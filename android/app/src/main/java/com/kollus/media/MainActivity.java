package com.kollus.media;

import static dao.BaseValue.BROADCAST_DOWNLOAD_COMPLETE;
import static dao.BaseValue.CHECK_FAIL;
import static dao.BaseValue.DIR_ROOT_PATH;
import static dao.BaseValue.DISK_TYPE_EXTERNAL;
import static dao.BaseValue.DISK_TYPE_LOCAL;
import static dao.BaseValue.DOWNLOAD_PROGRESS_STATUS_READY;
import static dao.BaseValue.DOWNLOAD_PROGRESS_STATUS_STOP;
import static dao.BaseValue.MB_1;
import static dao.BaseValue.MB_2;
import static dao.BaseValue.NO_DATA;
import static dao.BaseValue.NO_FIELD_DATA;
import static dao.BaseValue.TYPE_FILE;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Message;

import androidx.annotation.NonNull;

import com.kollus.sdk.media.KollusStorage;
import com.kollus.sdk.media.content.KollusContent;
import com.kollus.sdk.media.util.Log;
import com.kollus.sdk.media.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import entity.KollusContentEntity;
import entity.KollusDownloadContentEntity;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;
import model.KollusContentViewModel;
import model.KollusDownloadingModel;

public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "com.kollus.media/meth";
    protected static MultiKollusStorage mMultiStorage;
    private DownloadInfo mInfo;
    private KollusStorage storage;
    private KollusContent mKollusContent;
    private MultiKollusStorage multi;
    private List<DownloadInfo> mDownloadInfoList;
    private Context mContext;

    private Intent mIntent;
    private String  mediaUrl;
    private native String native_getMediaInfoJson(long var1, String var3);
    private KollusContentViewModel mKollusContentViewModel;
    private KollusDownloadingModel mKollusDownloadingModel;
    private ArrayList<MultiKollusContent> mDownloadList;


    private   void handleMessage(Message msg) {
        MultiKollusContent contentOne = (MultiKollusContent) msg.obj;
        mMultiStorage.getKollusContent(mMultiStorage.getStorageIndex(contentOne.getKollusStorage()),
                contentOne.getKollusContent(), contentOne.getKollusContent().getMediaContentKey());
    }
    public static final String TAG = MainActivity.class.getName();

    private void updateKollusContentDownloadServiceLoaded(DownloadInfo info, MultiKollusContent content) {
        Log.d(TAG, "DOWNLOAD_LOADED updateKollusContentDownloadServiceLoaded!!!! ");
        Log.d(TAG, "DOWNLOAD_LOADED name == " + content.getKollusContent().getSubCourse() + " , mck == " + content.getKollusContent().getMediaContentKey() + " , isloaded == " + content.getKollusContent().isLoaded());

        String folder = info.getFolder();
        boolean isDownloadCompleted = content.getKollusContent().isCompleted();
        Log.d(TAG, "updateKollusContentDownloadServiceLoaded isDownloadCompleted === " + isDownloadCompleted);
        if (isDownloadCompleted) {
            Log.d(TAG, "DOWNLOAD_LOADED isDownloadCompleted ");

            //content db update check
            KollusContentEntity findContent = null;
            try {
                int disk_index = mMultiStorage.getStorageIndex(content.getKollusStorage());
                int disk_type = DISK_TYPE_LOCAL;
                if (disk_index > DISK_TYPE_LOCAL) {
                    disk_type = DISK_TYPE_EXTERNAL;
                }
                findContent = mKollusContentViewModel.findByMediaContentKey(content.getKollusContent().getMediaContentKey(), disk_type);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (findContent != null) {
                Log.d(TAG, "Download Loaded isDownloadCompleted already download complete");

                //DB Update Refresh.
                int id = findContent.getId();
                int current_time = findContent.getCurrent_time();
                long recently_play_time = findContent.getRecently_play_time();
                int expiration_date = content.getKollusContent().getExpirationDate();
//                int expiration_play_time = content.getKollusContent().getExpirationPlaytime();
//                int expiration_total_play_time = content.getKollusContent().getTotalExpirationPlaytime();
                int expiration_count = content.getKollusContent().getExpirationCount();
                int total_expiration_count = content.getKollusContent().getTotalExpirationCount();
                String thumbnail_path = content.getKollusContent().getThumbnailPath();
                int count_play_count = findContent.getCount_play_count();
                int lms_percent = findContent.getLms_percent();
                String screenshot_path = content.getKollusContent().getScreenShotPath();
                System.out.println("ssssssssssss");
                System.out.println(thumbnail_path);

                try {
                    mKollusContentViewModel.updateContent(current_time, recently_play_time,
                            expiration_date, expiration_count, total_expiration_count,
                            count_play_count, lms_percent, thumbnail_path, screenshot_path, id);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    //기존 다운로딩 리스트에 있으면 삭제한다.
                    List<KollusDownloadContentEntity> downloadContentList = null;
                    downloadContentList = mKollusDownloadingModel.getAllContents();
                    synchronized (downloadContentList) {
                        int exist_check_index = CHECK_FAIL;
                        for (int i = 0; i < downloadContentList.size(); i++) {
                            int diskIndex = downloadContentList.get(i).getDisk_index();
                            String mck = downloadContentList.get(i).getMedia_content_key();
                            if (diskIndex == mMultiStorage.getStorageIndex(content.getKollusStorage()) &&
                                    mck.equals(content.getKollusContent().getMediaContentKey())) {
                                exist_check_index = i;
                                break;
                            }
                        }

                        if (exist_check_index != CHECK_FAIL) {
                            synchronized (downloadContentList) {
                                int downloadContenId = downloadContentList.get(exist_check_index).getId();

                                // Downloading DataBase 에서 삭제
                                try {
                                    mKollusDownloadingModel.deleteIdDownloadingContent(downloadContenId);
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    //다운로드 리스트에 있으면 mDownloadContentList 삭제할 때 동기화 시켜준다.
                                    int delete_disk_index = downloadContentList.get(exist_check_index).getDisk_index();
                                    String delete_media_content_key = downloadContentList.get(exist_check_index).getMedia_content_key();
                                    int check_index = CHECK_FAIL;
                                    for (int i = 0; i < mDownloadList.size(); i++) {
                                        int diskIndex = mMultiStorage.getStorageIndex(mDownloadList.get(i).getKollusStorage());
                                        String mck = mDownloadList.get(i).getKollusContent().getMediaContentKey();
                                        if ((diskIndex == delete_disk_index) && (mck.equals(delete_media_content_key))) {
                                            check_index = i;
                                            break;
                                        }
                                    }
                                    if (check_index != CHECK_FAIL) {
                                        Log.i(TAG, "updateKollusContentDownloadServiceLoaded  mDownloadList.remove name == " + mDownloadList.get(check_index).getKollusContent().getSubCourse());
                                        mDownloadList.remove(check_index);
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //4. ui refresh.
                try {
                    Intent download_load_refresh = new Intent();
                    download_load_refresh.setAction(BROADCAST_DOWNLOAD_COMPLETE);
//                    mContext.sendBroadcast(download_load_refresh);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //5. next download
//                nextDownload();

            }

            else {
                Log.d(TAG, "Download Loaded isDownloadCompleted content db update!!! plz....");
                //update Content DB
                String folderFullName = folder;

                //1. folder 생성 및 업데이트.
                String filePath = "";
                String folderName = "";
                if (folderFullName != null && !folderFullName.isEmpty()) {
                    String[] items = folderFullName.split(DIR_ROOT_PATH);
                    for (int i = 0; i < items.length; i++) {
                        Log.d(TAG, "folderName = " + items[i]);
                        folderName = items[i];
                        filePath = filePath + DIR_ROOT_PATH + items[i];
                        try {
                            Log.d(TAG, "filePath = " + filePath);
                            // /를 기준으로 문자열을 추출할 것이다.
                            int idx = filePath.lastIndexOf(DIR_ROOT_PATH);
                            // "/" 앞부분을 추출
                            String parentDirectoryPath = filePath.substring(0, idx);
                            String folderPath = "";

                            if ((parentDirectoryPath != null && parentDirectoryPath.equals("")) || (parentDirectoryPath != null && parentDirectoryPath.isEmpty())) {
                                folderPath = DIR_ROOT_PATH;
                            } else {
                                folderPath = parentDirectoryPath;
                            }
                            Log.d(TAG, "folderPath = " + folderPath);

                            List<KollusContentEntity> findFolderList = mKollusContentViewModel.findFolderList(folderPath);

                            if (folderPath.equals(DIR_ROOT_PATH)) {
//                                boolean isDuplicateFolder = Utils.isDuplicateFolder(findFolderList, folderName);
//                                if (!isDuplicateFolder) {
//                                    Log.d(TAG, "update folder !!!!!");
//                                    //폴더 추가.
//                                    DatabaseFolderAdd(mContext, folderName, folderPath, filePath);
//                                } else {
//                                    Log.d(TAG, "already update folder !!!!!");
//                                }
                            } else {
//                                if (findFolderList.size() > 0) {
//                                    Log.d(TAG, "folderPath is exists = " + folderPath);
//                                    boolean isDuplicateFolder = Utils.isDuplicateFolder(findFolderList, folderName);
//                                    if (!isDuplicateFolder) {
//                                        Log.d(TAG, "update folderName is exists = " + folderName);
//                                        DatabaseFolderAdd(mContext, folderName, folderPath, filePath);
//                                    } else {
//                                        Log.d(TAG, "already update folderName is exists = " + folderName);
//                                    }
//                                } else {
//                                    Log.d(TAG, "folderPath is no exists update plz... = " + folderPath);
//                                    DatabaseFolderAdd(mContext, folderName, folderPath, filePath);
//                                }
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                //2. 파일 DB 업데이트 및 변경.
                String newFileName = content.getKollusContent().getSubCourse();
                String newFilePath = "";
                String folderPath = "";
                if ((folderFullName == null)
                        || (folderFullName != null && folderFullName.equals(""))
                        || (folderFullName != null && folderFullName.isEmpty())) {
                    newFilePath = DIR_ROOT_PATH + newFileName;
                } else if (folderFullName != null && folderFullName.equals(DIR_ROOT_PATH)) {
                    newFilePath = DIR_ROOT_PATH + newFileName;
                } else {
                    newFilePath = DIR_ROOT_PATH + folderFullName + DIR_ROOT_PATH + newFileName;
                }

                int idx = newFilePath.lastIndexOf(DIR_ROOT_PATH);
                String parentDirectoryPath = newFilePath.substring(0, idx);
                if ((parentDirectoryPath != null && parentDirectoryPath.equals("")) || (parentDirectoryPath != null && parentDirectoryPath.isEmpty())) {
                    folderPath = DIR_ROOT_PATH;
                } else {
                    folderPath = parentDirectoryPath;
                }

                //해당 파일이 있는지 체크
                int disk_index = DiskUtil.getDiskIndex(mContext);
                int disk_type = DISK_TYPE_LOCAL;
//                if (disk_index > DISK_TYPE_LOCAL) {
//                    disk_type = DISK_TYPE_EXTERNAL;
//                }

                long file_create = System.currentTimeMillis();
                long file_edit = file_create;

                String media_content_key = content.getKollusContent().getMediaContentKey();
//                KollusContentEntity findContent = null;
//                try {
//                    findContent = mKollusContentViewModel.findByMediaContentKey(media_content_key, disk_type);
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                if(findContent != null){
//                    file_create = findContent.getFile_create();
//                    file_edit = findContent.getFile_edit();
//                }

                String filepath = newFilePath;
                String filename = newFileName;
                String file_type = TYPE_FILE;
                String folder_path = folderPath;
                long file_size = content.getKollusContent().getFileSize();
                int duration = content.getKollusContent().getDuration();
                int current_time = NO_DATA;
                long recently_play_time = NO_DATA;
                int expiration_date = content.getKollusContent().getExpirationDate();
//                int expiration_play_time = content.getKollusContent().getExpirationPlaytime();
//                int expiration_total_play_time = content.getKollusContent().getTotalExpirationPlaytime();
                int expiration_count = content.getKollusContent().getExpirationCount();
                int total_expiration_count = content.getKollusContent().getTotalExpirationCount();
                int count_play_count = NO_DATA;
                int lms_percent = NO_DATA;
                String thumbnail_path = content.getKollusContent().getThumbnailPath();
                String screenshot_path = content.getKollusContent().getScreenShotPath();
                Bitmap thumbnail_bitmap = null;
                try {
                    //DB Save column size Max 2MB
                    File file = new File(thumbnail_path);
                    long length = file.length();
                    if (length > 0) {
                        if (length <= MB_1) {
                            thumbnail_bitmap = BitmapFactory.decodeFile(thumbnail_path);
                        } else if (length <= MB_2) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 2;
                            thumbnail_bitmap = BitmapFactory.decodeFile(thumbnail_path, options);
                        } else {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 4;
                            thumbnail_bitmap = BitmapFactory.decodeFile(thumbnail_path, options);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                KollusContentEntity complete_download_content = new KollusContentEntity(NO_FIELD_DATA, filePath, filename,
                        file_type, file_create, file_edit,
                        folder_path, file_size, duration,
                        current_time, recently_play_time, expiration_date,
                        expiration_count, total_expiration_count, count_play_count,
                        disk_index, disk_type, lms_percent,
                        thumbnail_path, media_content_key, screenshot_path, thumbnail_bitmap);
                mKollusContentViewModel.insertKollusContent(complete_download_content);
//                mContentsList.add(complete_download_content);

                //3. Downloading DataBase 에서 삭제
                //기존 다운로딩 리스트에 있으면 삭제한다.
                try {
                    synchronized (this) {
                        int exist_check_index = CHECK_FAIL;
                        List<KollusDownloadContentEntity> downloadContentList = null;
                        downloadContentList = mKollusDownloadingModel.getAllContents();

                        for (int i = 0; i < downloadContentList.size(); i++) {
                            int diskIndex = downloadContentList.get(i).getDisk_index();
                            String mck = downloadContentList.get(i).getMedia_content_key();
                            if (diskIndex == mMultiStorage.getStorageIndex(content.getKollusStorage()) &&
                                    mck.equals(content.getKollusContent().getMediaContentKey())) {
                                exist_check_index = i;
                                break;
                            }
                        }

                        if (exist_check_index != CHECK_FAIL) {
                            synchronized (downloadContentList) {
                                int id = downloadContentList.get(exist_check_index).getId();

                                // Downloading DataBase 에서 삭제
                                try {
                                    mKollusDownloadingModel.deleteIdDownloadingContent(id);
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    //다운로드 리스트에 있으면 mDownloadContentList 삭제할 때 동기화 시켜준다.
                                    int delete_disk_index = downloadContentList.get(exist_check_index).getDisk_index();
                                    String delete_media_content_key = downloadContentList.get(exist_check_index).getMedia_content_key();
                                    int check_index = CHECK_FAIL;
                                    for (int i = 0; i < mDownloadList.size(); i++) {
                                        int diskIndex = mMultiStorage.getStorageIndex(mDownloadList.get(i).getKollusStorage());
                                        String mck = mDownloadList.get(i).getKollusContent().getMediaContentKey();
                                        if ((diskIndex == delete_disk_index) && (mck.equals(delete_media_content_key))) {
                                            check_index = i;
                                            break;
                                        }
                                    }

                                    if (check_index != CHECK_FAIL) {
                                        Log.i(TAG, "11 updateKollusContentDownloadServiceLoaded  mDownloadList.remove name == " + mDownloadList.get(check_index).getKollusContent().getSubCourse());
                                        mDownloadList.remove(check_index);
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //4. ui refresh.
                try {
                    Intent download_load_refresh = new Intent();
                    download_load_refresh.setAction(BROADCAST_DOWNLOAD_COMPLETE);
//                    mContext.sendBroadcast(download_load_refresh);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //5. next download
//                nextDownload();
            }
        } else {
            content.getKollusContent().setDownloading(true);

            try {
                synchronized (this) {
                    List<KollusDownloadContentEntity> downloadContentList = null;
                    downloadContentList = mKollusDownloadingModel.getAllContents();

                    boolean exist = false;
                    int exist_content_list_index = CHECK_FAIL;
                    for (int i = 0; i < downloadContentList.size(); i++) {
                        int disk_index = downloadContentList.get(i).getDisk_index();
                        String mck = downloadContentList.get(i).getMedia_content_key();
                        if (disk_index == mMultiStorage.getStorageIndex(content.getKollusStorage()) &&
                                mck.equals(content.getKollusContent().getMediaContentKey())) {
                            exist_content_list_index = i;
                            break;
                        }
                    }

                    Log.d(TAG, "Download Loaded  exist_content_list_index == " + exist_content_list_index);
                    if (exist_content_list_index != CHECK_FAIL) {
                        int status = downloadContentList.get(exist_content_list_index).getDownload_status();
                        if (status == DOWNLOAD_PROGRESS_STATUS_STOP) {
                            long download_received_size = content.getKollusContent().getReceivedSize();
                            int download_status = DOWNLOAD_PROGRESS_STATUS_READY;
                            int download_percent = content.getKollusContent().getDownloadPercent();
                            int id = downloadContentList.get(exist_content_list_index).getId();
                            long download_start_time = System.currentTimeMillis();
                            String download_folder = folder;

                            for (int i = 0; i < downloadContentList.size(); i++) {
                                if (id == downloadContentList.get(i).getId()) {
                                    downloadContentList.get(i).setDownload_receiving_size(download_received_size);
                                    downloadContentList.get(i).setDownload_percent(download_percent);
                                    downloadContentList.get(i).setDownload_status(download_status);
                                    downloadContentList.get(i).setDownload_start(download_start_time);
                                    downloadContentList.get(i).setFolder(download_folder);
                                    mKollusDownloadingModel.updateDownloadingContent(downloadContentList.get(i));
                                    break;
                                }
                            }
                        } else {
                            Log.d(TAG, "Download Loaded 888 skip status == " + status);
                        }
                    } else {
//                        DownloadingContentDataBaseAdd(folder, content);
                    }

                    for (MultiKollusContent iter : mDownloadList) {
                        if (iter.getKollusStorage() == content.getKollusStorage() &&
                                iter.getKollusContent().getMediaContentKey().equals(content.getKollusContent().getMediaContentKey())) {
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) {
                        mDownloadList.add(content);
                    }

//                    nextDownload();
//                    if (!exist) {
//                        mTotalDownloadCount++;
//                        mDownloadingFileSize += content.getKollusContent().getFileSize() - content.getKollusContent().getReceivedSize();
//                    }
//
//                    if (mTotalDownloadCount == 1) {
//                        mDownloadCheckTime = System.currentTimeMillis();
//                        mHandler.postDelayed(mDownloadChecker, DOWNLOAD_CHECK_TIME);
//                    }
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler((call, result) -> {
                    if (call.method.equals("getData")) {
                        mMultiStorage = MultiKollusStorage.getInstance(getApplicationContext());

                        mMultiStorage.setCertification(
                                "dc2b1bbbb5cbd5d2d3a3078aef7e9eeb7938a748",
                                "2023/12/31",
                                Utils.getPlayerId(MainActivity.this),
                                Utils.getPlayerIdMd5(MainActivity.this),
                                false);
                        int nRet = mMultiStorage.getErrorCode();
                        mMultiStorage.setNetworkTimeout(10, 3);
                        mInfo = new DownloadInfo(mMultiStorage.getStorage(0), null, "https://v.kr.kollus.com/si?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHB0IjoxNjg1MTYyNDQwLCJjdWlkIjoiRmdnZyIsIm1jIjp7IjAiOnsibWNrZXkiOiJUaW9xaExLdSJ9fX0.MzTJovMO8PFg6pjAmfft5BiOxFcgYHFOagOeIUX1UGw&uservalue0=1111&custom_key=b58ff70f6bdc17470f604e2e19500c97faa3fd7c677d206f51db05858331221c");
                        System.out.println("____");
                        System.out.println(nRet);
                        System.out.println(mInfo.getKollusStorge().getVersion());
                        KollusContent content = mInfo.getMultiKollusContent().getKollusContent();
                        String extraDrmParam = null;
                        MultiKollusContent testOne = mInfo.getMultiKollusContent();

                         mInfo.getKollusStorge().load("https://v.kr.kollus.com/si?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHB0IjoxNjg1MTYyNDQwLCJjdWlkIjoiRmdnZyIsIm1jIjp7IjAiOnsibWNrZXkiOiJUaW9xaExLdSJ9fX0.MzTJovMO8PFg6pjAmfft5BiOxFcgYHFOagOeIUX1UGw&uservalue0=1111&custom_key=b58ff70f6bdc17470f604e2e19500c97faa3fd7c677d206f51db05858331221c", extraDrmParam, content);


//                        String mediaInfoJson = native_getMediaInfoJson(-1L, "https://v.kr.kollus.com/si?jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHB0IjoxNjg1MDc0Mjc5LCJjdWlkIjoiRmZmIiwibWMiOnsiMCI6eyJtY2tleSI6IlRpb3FoTEt1In19fQ.iy5cFZZv0NkbTmmmX6C689UqQG2ABngS1x_H5Ylb8z0&uservalue0=1111&custom_key=b58ff70f6bdc17470f604e2e19500c97faa3fd7c677d206f51db05858331221c");
//                        try {
//                            JSONObject json = new JSONObject(mediaInfoJson);
//                            JSONArray items = json.getJSONObject("result").getJSONArray("items");
//                            JSONObject item = items.getJSONObject(0);
//                            mediaUrl = item.getJSONObject("file").getString("media_url");
//                            String fileName = mediaUrl.substring(mediaUrl.lastIndexOf(47) + 1);
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }


                        System.out.println("PPPPP");




//                         uri= mInfo.getMultiKollusContent().getKollusContent().getMediaUrl();

                        System.out.println("uuuu");
                        String data = "Hello from Android";
                        result.success(data);
                    } else {
                        result.notImplemented();
                    }



                });
    }
}
