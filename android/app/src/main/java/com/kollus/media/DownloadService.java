package com.kollus.media;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.RemoteException;

import androidx.core.app.NotificationCompat;

import com.kollus.media.listener.MultiKollusPlayerDRMListener;
import com.kollus.media.listener.MultiKollusStorageListener;

import com.kollus.sdk.media.KollusPlayerDRMListener;
import com.kollus.sdk.media.KollusStorage;
import com.kollus.sdk.media.content.KollusContent;
import com.kollus.sdk.media.util.ErrorCodes;
import com.kollus.sdk.media.util.Log;
import com.kollus.sdk.media.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadService extends Service {
    private static final String TAG = DownloadService.class.getSimpleName();

    private final int MIN_FREE_SIZE = 150 * 1024 * 1024;
    public static final int ADD_HANDLER = 0;
    public static final int DISK_UNMOUNT 		= 1;
    public static final int DOWNLOAD_START = 10;
    public static final int DOWNLOAD_LOADED = 11;
    public static final int DOWNLOAD_ALREDY_LOADED = 12;
    public static final int DOWNLOAD_STARTED = 13;
    public static final int DOWNLOAD_LOAD_ERROR = 14;
    public static final int DOWNLOAD_CANCEL = 20;
    public static final int DOWNLOAD_CANCELED = 21;
    public static final int DOWNLOAD_ERROR = 30;
    public static final int DOWNLOAD_PROCESS = 40;
    public static final int DOWNLOAD_COMPLETE = 50;
    public static final int DOWNLOAD_DRM = 60;
    public static final int DOWNLOAD_DRM_INFO = 61;

    public static final int APP_FORGROUND = 70;
    public static final int APP_BACKGROUND = 71;

    private MultiKollusStorage mMultiStorage;
    private String mStoragePath;
    //20210513 download load task issue fixed.
    private LocalHandler mLocalHandler = new LocalHandler();
    private Messenger mMessenger = new Messenger(mLocalHandler);
//    private Messenger mMessenger = new Messenger(new LocalHandler());
    private Messenger mClientMessenger;
    private List<Handler> mHandlers;
    private ExecutorService mExecutor;
    private List<DownloadInfo> mDownloadInfoList;
    private DownloadDBHelper mDownloadDBHelper;

    private static boolean mDownloading = false;
    private PowerManager.WakeLock mWakeLock;
    private WifiManager.WifiLock mWifiLock;

    private final String mChannelId = "com.kollus.media.download";
    private final String mChannelName = "Kollus Download Channel";
    private NotificationCompat.Builder mNotificationBuilder;
    private boolean mRunningBackground;
    private boolean mDownloadServiceDestroyCheck = false;

    @Override
    public void onCreate() {
        super.onCreate();

        mDownloadServiceDestroyCheck = false;
        mHandlers = new ArrayList<Handler>();
        mDownloadInfoList = new ArrayList<DownloadInfo>();
        mDownloadDBHelper = DownloadDBHelper.getInstance(this);
        Vector<DownloadInfo> list = mDownloadDBHelper.list();
        for(DownloadInfo iter : list) {
            if(iter.getMultiKollusContent().getKollusContent().isLoaded())
                mDownloadInfoList.add(iter);
            else
                mDownloadDBHelper.delete(iter);
        }

        mMultiStorage = MultiKollusStorage.getInstance(getApplicationContext());
        mMultiStorage.registerKollusStorageListener(mKollusStorageListener);
        mMultiStorage.registerKollusPlayerDRMListener(mKollusPlayerDRMListener);
        mMultiStorage.registerSDStateChangeListener(mSDCardStateChangeListener);
        mStoragePath = Utils.getStoragePath(this);
        mExecutor = Executors.newFixedThreadPool(1);

        PowerManager powerManager = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        mWakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, ":DownloadWakeLock");

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mWifiLock = wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL_HIGH_PERF, "DownloadWifiLock");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
            }else {
//                pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            }

            NotificationChannel channel = new NotificationChannel(
                    mChannelId, mChannelName,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
//
//            mNotificationBuilder = new NotificationCompat.Builder(this, mChannelId)
//                    .setContentIntent(pendingIntent)
//                    .setSmallIcon(R.drawable.folder_24)
//                    .setContentTitle(getResources().getString(R.string.app_name))
//                    .setProgress(100, 0, false)
//                    .setOnlyAlertOnce(true);
        }
    }

    @Override
    public void onDestroy() {
        mMultiStorage.unregisterKollusStorageListener(mKollusStorageListener);
        mMultiStorage.unregisterKollusPlayerDRMListener(mKollusPlayerDRMListener);
        mMultiStorage.unregisterSDStateChangeListener(mSDCardStateChangeListener);
        mDownloadServiceDestroyCheck = true;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (!mWakeLock.isHeld())
            mWakeLock.acquire();
        if (!mWifiLock.isHeld())
            mWifiLock.acquire();

        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub
//		synchronized (mDownloadList){
//			for(DownloadInfo info : mDownloadList) {
//				KollusStorage storage = info.getMultiKollusContent().getKollusStorage();
//				String mediaContentKey = info.getMultiKollusContent().getKollusContent().getMediaContentKey();
//				storage.unload(mediaContentKey);
//			}
//			mDownloadList.clear();
//		}
        if (mWakeLock.isHeld())
            mWakeLock.release();
        if (mWifiLock.isHeld())
            mWifiLock.release();
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
//		return super.onStartCommand(intent, flags, startId);
        return START_REDELIVER_INTENT;
    }

    private class LocalHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADD_HANDLER: {
                    mClientMessenger = new Messenger((Handler) msg.obj);
                    try {
                        mClientMessenger.send(Message.obtain(null, ADD_HANDLER, "Registed messanger"));
                        for (DownloadInfo iter : mDownloadInfoList) {
                            mClientMessenger.send(Message.obtain(null, DOWNLOAD_LOADED, 0, 0, iter));
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;

                case DOWNLOAD_START: {
                    DownloadInfo info = (DownloadInfo) msg.obj;
                    LoadTask task = new LoadTask(info);
                    mExecutor.execute(task);
                }
                break;

                case DOWNLOAD_CANCEL: {
                    MultiKollusContent content = (MultiKollusContent) msg.obj;
                    Log.d(TAG, String.format("DOWNLOAD_CANCEL: %d Disk's %s",
                            mMultiStorage.getStorageIndex(content.getKollusStorage()), content.getKollusContent().getMediaContentKey()));
                    try {
                        KollusStorage storage = content.getKollusStorage();
                        String mediaContentKey = content.getKollusContent().getMediaContentKey();
                        storage.unload(mediaContentKey);

                        synchronized (mDownloadInfoList) {
                            mDownloading = !mDownloadInfoList.isEmpty();

                            if (mDownloadInfoList.size() == 0)
                                return;

                            if (mDownloadInfoList.get(0).getMultiKollusContent().getKollusContent().getMediaContentKey().equals(mediaContentKey)) {
                                Log.d(TAG, "DownloadList.remove " + mediaContentKey + " in first");
                                nextDownload();
                            } else {
                                for (DownloadInfo info : mDownloadInfoList) {
                                    if (info.getMultiKollusContent().getKollusContent().getMediaContentKey().equals(mediaContentKey)) {
                                        Log.d(TAG, "DownloadList.remove " + mediaContentKey + " in not first");
                                        mDownloadDBHelper.delete(info);
                                        mDownloadInfoList.remove(info);
                                        break;
                                    }
                                }
                            }
                        }

                        if (mDownloading && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            stopForeground(true);
                        }

                        mClientMessenger.send(Message.obtain(null, DOWNLOAD_CANCELED, 0, 0, mediaContentKey));
                    } catch (RemoteException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;

                case APP_FORGROUND:
                    mRunningBackground = false;
                    stopForeground(true);
                    break;
                case APP_BACKGROUND:
                    mRunningBackground = true;
                    if (!mDownloadInfoList.isEmpty()) {
                        if (mNotificationBuilder != null) {
                            startForeground(KollusConstants.SERVICE_DOWNLOAD, mNotificationBuilder.build());
                        } else {
                            startForeground(KollusConstants.SERVICE_DOWNLOAD, new Notification());
                        }
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public static boolean isDownloading() {
        return mDownloading;
    }

    private class LoadTask implements Runnable {
        private DownloadInfo mInfo;

        LoadTask(DownloadInfo info) {
            mInfo = info;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                synchronized (mDownloadInfoList) {
                    for (DownloadInfo info : mDownloadInfoList) {
                        if (info.getUrl().equals(mInfo.getUrl())) {
                            Log.w(TAG, "Already exists in DownloadList");
                            mClientMessenger.send(Message.obtain(null, DOWNLOAD_ALREDY_LOADED));
                            return;
                        }
                    }
                }

                KollusContent content = mInfo.getMultiKollusContent().getKollusContent();
                long freeSize = Utils.getAvailableMemorySize(mStoragePath) - content.getFileSize();
                if (freeSize < MIN_FREE_SIZE) {
                    mClientMessenger.send(Message.obtain(null, DOWNLOAD_LOAD_ERROR, 0, ErrorCodes.ERROR_STORAGE_FULL));
                    return;
                }

                String extraDrmParam = null;
                int nErrorCode = mInfo.getKollusStorge().load(mInfo.getUrl(), extraDrmParam, content);
                if(mDownloadServiceDestroyCheck){
                    mDownloading = false;
                    return;
                }
                if (nErrorCode != ErrorCodes.ERROR_OK) {
                    mClientMessenger.send(Message.obtain(null, DOWNLOAD_LOAD_ERROR, 0, nErrorCode));
                } else {
                    synchronized (mDownloadInfoList) {
                        mDownloading = true;
                        for(DownloadInfo info : mDownloadInfoList) {
                            if(info.getMultiKollusContent().getKollusStorage() == mInfo.getMultiKollusContent().getKollusStorage() &&
                                    info.getMultiKollusContent().getKollusContent().getMediaContentKey().equalsIgnoreCase(
                                            mInfo.getMultiKollusContent().getKollusContent().getMediaContentKey())) {
                                mClientMessenger.send(Message.obtain(null, DOWNLOAD_ALREDY_LOADED));
                                return;
                            }
                        }

                        boolean bFirstDownload = mDownloadInfoList.isEmpty();
                        mDownloadInfoList.add(mInfo);
                        mDownloadDBHelper.add(mInfo);
                        mClientMessenger.send(Message.obtain(null, DOWNLOAD_LOADED, 0, 0, mInfo));
                        if (bFirstDownload) {
                            long fileSize = content.getFileSize();
                            freeSize = Utils.getAvailableMemorySize(mStoragePath);
                            if (fileSize > freeSize || (freeSize - fileSize) < MIN_FREE_SIZE) {
                                mClientMessenger.send(Message.obtain(null, DOWNLOAD_ERROR, 0, ErrorCodes.ERROR_STORAGE_FULL, mInfo.getMultiKollusContent()));
                                return;
                            }

                            int nRet = mInfo.getKollusStorge().download(content.getMediaContentKey());
                            Log.d(TAG, String.format("Download Start MCK %s index %d nRet %d", content.getMediaContentKey(), content.getUriIndex(), nRet));
                            if (nRet >= 0) {
                                mClientMessenger.send(Message.obtain(null, DOWNLOAD_STARTED, 0, 0, mInfo.getMultiKollusContent()));

                                if (mNotificationBuilder != null) {
                                    mNotificationBuilder.setLargeIcon(BitmapFactory.decodeFile(content.getThumbnailPath()));
                                    mNotificationBuilder.setContentText(content.getSubCourse());
                                }
                            } else {
                                mClientMessenger.send(Message.obtain(null, DOWNLOAD_ERROR, 0, nRet, mInfo.getMultiKollusContent()));
                                nextDownload();
                            }
                        }
                    }
                }

                //서비스 종료시 다운로드 상태를 변경하도록 방어코드 추가.
                if(mDownloadServiceDestroyCheck){
                    mDownloading = false;
                }
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    MultiKollusStorageListener mKollusStorageListener = new MultiKollusStorageListener() {
        @Override
        public void onComplete(KollusStorage storage, KollusContent content) {
            // TODO Auto-generated method stub
            Log.d(TAG, "Download Complete " + content.getMediaContentKey());
            if (mClientMessenger != null) {
                MultiKollusContent multiKollusContent = null;
                try {
                    synchronized (mDownloadInfoList) {
                        for (DownloadInfo iter : mDownloadInfoList) {
                            if (iter.getKollusStorge() == storage &&
                                    iter.getMultiKollusContent().getKollusContent().getMediaContentKey().equalsIgnoreCase(content.getMediaContentKey())) {
                                multiKollusContent = iter.getMultiKollusContent();
                                multiKollusContent.getKollusContent().setDownloadCompleted(true);
                                mDownloadDBHelper.delete(iter);
                                break;
                            }
                        }
                        nextDownload();
                    }

                    if (multiKollusContent == null) {
                        Log.e(TAG, "Download Complete. But Not Found in Download List." + content.getMediaContentKey());
                        return;
                    }

                    mClientMessenger.send(Message.obtain(null, DOWNLOAD_COMPLETE, 0, 0, multiKollusContent));
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onProgress(KollusStorage storage, KollusContent content) {

//            Log.d(TAG, "onProgress getMediaContentKey == " + content.getMediaContentKey() + " , name == " + content.getSubCourse() + " , getReceivedSize ==  " + content.getReceivedSize());
//            Log.d(TAG, "onProgress  getFileSize == " + content.getFileSize() + " , getDownloadPercent == " + content.getDownloadPercent() + " , disk_index == " + mMultiStorage.getStorageIndex(storage));

            // TODO Auto-generated method stub
            if (mClientMessenger != null) {
                MultiKollusContent multiKollusContent = null;
                try {
                    synchronized (mDownloadInfoList) {
                        for (DownloadInfo iter : mDownloadInfoList) {
                            if (iter.getKollusStorge() == storage &&
                                    iter.getMultiKollusContent().getKollusContent().getMediaContentKey().equalsIgnoreCase(content.getMediaContentKey())) {
                                multiKollusContent = iter.getMultiKollusContent();
                                PropertyUtil.copyKollusContent(multiKollusContent.getKollusContent(), content);
                                break;
                            }
                        }

                        if (multiKollusContent != null)
                            mClientMessenger.send(Message.obtain(null, DOWNLOAD_PROCESS, 0, 0, multiKollusContent));
                        content.setReceivedSize(content.getReceivingSize());
                    }

                    if (mNotificationBuilder != null) {
                        mNotificationBuilder.setProgress(100, content.getDownloadPercent(), false);
                        if (mRunningBackground) {
                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.notify(KollusConstants.SERVICE_DOWNLOAD, mNotificationBuilder.build());
                        }
                    }
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onError(KollusStorage storage, KollusContent content, int errorCode) {
            // TODO Auto-generated method stub
            Log.d(TAG, "onError " + content.getMediaContentKey() + " , errorCode == " + errorCode);
            if (mClientMessenger != null) {

                MultiKollusContent multiKollusContent = null;
                try {
                    Log.d(TAG, "onError " + content.getMediaContentKey());
                    synchronized (mDownloadInfoList) {
                        for (DownloadInfo iter : mDownloadInfoList) {
                            if (iter.getKollusStorge() == storage &&
                                    iter.getMultiKollusContent().getKollusContent().getMediaContentKey().equalsIgnoreCase(content.getMediaContentKey())) {
                                multiKollusContent = iter.getMultiKollusContent();
                                break;
                            }
                        }
                        nextDownload();
                    }
                    mClientMessenger.send(Message.obtain(null, DOWNLOAD_ERROR, mMultiStorage.getStorageIndex(storage), errorCode, multiKollusContent));
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    };

    private void nextDownload() throws RemoteException {

        while (!mDownloadInfoList.isEmpty()) {
            mDownloadDBHelper.delete(mDownloadInfoList.get(0));
            mDownloadInfoList.remove(0);
            mDownloading = !mDownloadInfoList.isEmpty();

            if (mDownloadInfoList.isEmpty()) {
                Log.d(TAG, "nextDownload is null");
                break;
            } else {
                MultiKollusContent multiContent = mDownloadInfoList.get(0).getMultiKollusContent();
                KollusContent content = multiContent.getKollusContent();
                long fileSize = content.getFileSize();
                long freeSize = Utils.getAvailableMemorySize(mStoragePath);
                if (fileSize > freeSize || (freeSize - fileSize) < MIN_FREE_SIZE) {
                    mClientMessenger.send(Message.obtain(null, DOWNLOAD_ERROR, 0, ErrorCodes.ERROR_STORAGE_FULL, multiContent));
                } else {
                    int nRet = multiContent.getKollusStorage().download(content.getMediaContentKey());
                    if (nRet >= 0) {
                        Log.d(TAG, "nextDownload started " + content);
                        mClientMessenger.send(Message.obtain(null, DOWNLOAD_STARTED, 0, 0, multiContent));
                        if (mNotificationBuilder != null) {
                            mNotificationBuilder.setContentText(content.getSubCourse());
                        }
                        break;
                    } else {
                        Log.d(TAG, "nextDownload error " + content);
                        mClientMessenger.send(Message.obtain(null, DOWNLOAD_ERROR, 0, nRet, multiContent));
                    }
                }
            }
        }

        if (mDownloadInfoList.isEmpty()) {
            //Notification 종료 및 다운로드 핸들러 큐의 이벤트 제거.
            try {
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancelAll();
                stopForeground(true);
                if (mLocalHandler != null) {
                    mLocalHandler.removeCallbacksAndMessages(null);
                }
                Log.d(TAG, "nextDownload stopForeground");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    MultiKollusPlayerDRMListener mKollusPlayerDRMListener = new MultiKollusPlayerDRMListener() {
        @Override
        public void onDRM(KollusStorage storage, String request, String response) {
            // TODO Auto-generated method stub
            if (mClientMessenger != null) {
                try {
                    mClientMessenger.send(Message.obtain(null, DOWNLOAD_DRM, new DownloadDRM(storage, request, response)));
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onDRMInfo(KollusStorage storage, KollusContent content, int nInfoCode) {
            Log.i(TAG, String.format("onDRMInfo index %d nInfoCode %d message %s", content.getUriIndex(), nInfoCode, content.getServiceProviderMessage()));

            if (mClientMessenger != null) {
                try {
                    if (nInfoCode == KollusPlayerDRMListener.DCB_INFO_DELETE) {
                        content.setDownloadError(true);
                        synchronized (mDownloadInfoList) {
                            nextDownload();
                        }
                    }
                    mClientMessenger.send(Message.obtain(null, DOWNLOAD_DRM_INFO, mMultiStorage.getStorageIndex(storage), nInfoCode, content));
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    };

    private MultiKollusStorage.SDCardStateChangeListener mSDCardStateChangeListener = new MultiKollusStorage.SDCardStateChangeListener() {
        @Override
        public void onStateChaged(String path, boolean mounted) {
            if(!mounted) {
                synchronized (mDownloadInfoList) {
                    for(int i=0; i<mDownloadInfoList.size(); i++) {
                        if(mDownloadInfoList.get(i).getKollusStorge().getRootPath().contains(path)) {
                            mDownloadInfoList.remove(i);
                            i=0;
                        }
                    }
                    mDownloading = !mDownloadInfoList.isEmpty();
                    try {
                        mClientMessenger.send(Message.obtain(null, DISK_UNMOUNT));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

}

