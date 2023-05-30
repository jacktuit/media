package com.kollus.media;

import android.content.Context;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Environment;
import android.os.storage.StorageManager;

import androidx.core.content.ContextCompat;

import com.kollus.sdk.media.util.Log;
import com.kollus.sdk.media.util.Utils;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
 * Created by Song on 2017-09-07.
 */

public class DiskUtil {
    private static final String TAG = DiskUtil.class.getSimpleName();
    private static final double ONE_KILOBYTE = 1024;
    private static final double ONE_MEGABYTE = ONE_KILOBYTE * 1024;
    private static final double ONE_GIGABYTE = ONE_MEGABYTE * 1024;
    private static StorageManager mStorageManager = null;

    /*
     * 로컬 스토리지가 시스템에서 강제로 바뀌는 경우 해당 패스로 넘어오는 경우 로컬 패스로 인식한다.
     */
    private static final String EX_LOCAL_STORAGE_PATH = "/sdcard/Android/data/com.kollus.media";


    /**
     * 주어진 사이즈 String으로 가져오는 함수
     * 예 : 1.00GB, 1.00MB, 1.00KB, 1B
     *
     * @param size
     * @return
     */
    public static String getStringSize(long size) {
        if (size >= ONE_GIGABYTE)
            return String.format("%1.2fGB", size / ONE_GIGABYTE);
        else if (size > ONE_MEGABYTE)
            return String.format("%1.2fMB", size / ONE_MEGABYTE);
        else if (size > ONE_KILOBYTE)
            return String.format("%1.2fKB", size / ONE_KILOBYTE);
        else if (size > 0)
            return String.format("%dB", size);
        else
            return "0B";
    }

    private static boolean isMounted(Context context, String path) {
        if (mStorageManager == null) {
            mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        }

        if (mStorageManager == null) {
            return false;
        }

        Method method;
        Object obj;

        try {
            method = mStorageManager.getClass().getMethod("getVolumeState", new Class[] { String.class });

            obj = method.invoke(mStorageManager, new Object[] { path });
            if (Environment.MEDIA_MOUNTED.equals(obj)) {
                File file = new File(path);
                if(file.canWrite()) {
                    return true;
                }
                else {
                    Log.e(TAG, String.format("isMounted Check >>> %s is Read-Only.", path));
                    return false;
                }
            }
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String internal = Environment.getExternalStorageDirectory().toString();
        if(path.contains(internal)) {
            Log.w(TAG, "Inner SDCard must be mounted.");
            File file = new File(internal);
            if(file.canWrite()) {
                return true;
            }
            else {
                Log.e(TAG, String.format("isMounted Check >>> %s is Read-Only.", internal));
                return false;
            }
        }

        return false;
    }

    private static String getExternalStoragePath(Context context) {
        String storagePath = null;
        int storageCount = 0;
        try {
            if (mStorageManager == null) {
                mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            }

            if (mStorageManager == null) {
                return null;
            }

            Method method;
            Object obj;

            method = mStorageManager.getClass().getMethod("getVolumePaths", (Class[]) null);
            obj = method.invoke(mStorageManager, (Object[]) null);
            String[] paths = (String[]) obj;
            if (paths == null) {
                return null;
            }

            for (String path : paths) {
                if (isMounted(context, path)) {
                    storageCount++;
                    if (2 == storageCount) {
                        storagePath = path;
                        break;
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                storageCount = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return storagePath;
    }

    /**
     * 외부 SD 카드 경로를 배열로 가져오는 함수
     *
     * @param context
     * @return SD카드 경로
     */
    public static Vector<String> getExternalMounts(Context context) {

        final Vector<String> out = new Vector<String>();
        File[] dirs = ContextCompat.getExternalFilesDirs(context, null);
        for (File iter : dirs) {
            if (iter != null) {
                if (isMounted(context, iter.getParent())) {
                    out.add(iter.getParent());
                    Log.d(TAG, "SDCard Path : " + iter.getParent() + " Mounted.");
                }else
                    Log.e(TAG, "SDCard Path : " + iter.getParent() + " Unmounted.");
            }
        }

        String old = getExternalStoragePath(context);
        if(old != null) {
            if(isMounted(context, old)) {
                Log.d(TAG, "External SDCard Path : " + old + " Mounted.");
                old += "/Android/data/" + context.getPackageName();
                Log.d(TAG, "External SDCard Path : "+old);
                if (!out.contains(old)) {
                    Log.i(TAG, "External SDCard Old type Path : "+old);
                    out.add(old);
                }
            }
            else {
                Log.e(TAG, "External SDCard Path : " + old + " Unmounted.");
            }
        }

        String usbMountPathString = "";
        try {
            usbMountPathString = DiskUtil.getUSB();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!usbMountPathString.equals("")) {
            boolean isUsbMountDetectCheck = false;
            try {
                for (int i = 0; i < out.size(); i++) {
                    String extStoragePath = out.elementAt(i);
                    if (extStoragePath != null && extStoragePath.startsWith(usbMountPathString)) {
                        isUsbMountDetectCheck = true;
                        break;
                    }
                }
            }catch(ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (isUsbMountDetectCheck == false) {
                usbMountPathString += "/Android/data/" + context.getPackageName();
                Log.d(TAG, "External USB SDCard Path : " + usbMountPathString);
                if (!out.contains(usbMountPathString)) {
                    Log.i(TAG, "External USB SDCard type Path : " + usbMountPathString);

                    boolean existCheck = false;

                    try {
                        File dir = new File(usbMountPathString);
                        if (!dir.exists()) {
                            boolean ret = dir.mkdirs();
                            existCheck = ret;
                        } else {
                            existCheck = true;
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, "External USB SDCard type existCheck : " + existCheck);

                    if(existCheck == true) {
                        out.add(usbMountPathString);
                    }
                }
            }
        }

        try {
            for (String iter : out) {
                File dir = new File(iter);
                if (!dir.exists()) {
                    boolean isMade = dir.mkdirs();
                    Log.d(TAG, dir.getAbsolutePath()+" mkdirs ==> "+isMade);
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out;
    }

    public static int getDiskIndex(Context context) {
        int storagIndex = 0;
        String storageLocation = Utils.getStoragePath(context);
        Vector<String> storageList = getExternalMounts(context);
        String defaultLocalPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/" + context.getPackageName();
        Log.d(TAG, "getDiskIndex defaultLocalPath : " + defaultLocalPath);
        Log.d(TAG, "getDiskIndex storageLocation : " + storageLocation);

        if(storageLocation.startsWith(EX_LOCAL_STORAGE_PATH)){
            Utils.setStoragePath(context, defaultLocalPath);
            storageLocation = defaultLocalPath;
        }

//        if (storageList.size() > 1) { //외장 하드 있는지 체크
//            for (String path : storageList) {
//                Log.d(TAG, "getDiskIndex SDCard Path : " + path);
//                if (storageLocation.startsWith(path)) {
//                    break;
//                }
//                storagIndex++;
//            }
//        }

        Log.d(TAG, String.format("getDiskIndex pathList '%s'", storageList.toString()));
        for(String path : storageList) {
            if(storageLocation.startsWith(path))
                break;
            storagIndex++;
        }

        if(storageList.size() <= storagIndex)
            storagIndex = 0;

        Log.d(TAG, String.format("getDiskIndex '%s' --> %d", storageLocation, storagIndex));

        return storagIndex;
    }

    //USB 인식 체크
    //USB 체크 방식 첫번째
    public static String getUSBProblematic(Context context) {
        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        if (usbManager == null) {
            Log.e(TAG, "Unable to get USB_SERVICE");
            return "";
        }
        UsbAccessory[] accessoryList = usbManager.getAccessoryList();
        if (accessoryList != null) {
            for (UsbAccessory usbAccessory : accessoryList) {
                // here we check the vendor
                Log.d(TAG, "getUSBProblematic: " + usbAccessory.toString());
            }
        }

        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        if (deviceList != null) {
            List<UsbDevice> usbDeviceList = new ArrayList<>(deviceList.values());

            for (Iterator<UsbDevice> iterator = usbDeviceList.iterator(); iterator.hasNext(); ) {
                UsbDevice next = iterator.next();

                boolean isMassStorage = false;
                for (int i = 0; i < next.getInterfaceCount(); i++) {
                    // Check USB interface type is mass storage
                    UsbInterface usbInterface = next.getInterface(i);
                    if (usbInterface.getInterfaceClass() == UsbConstants.USB_CLASS_MASS_STORAGE && usbInterface.getEndpointCount() == 2) {

                        // Check endpoints support bulk transfer
                        for (int j = 0; j < usbInterface.getEndpointCount(); j++) {
                            UsbEndpoint endpoint = usbInterface.getEndpoint(j);
                            if (endpoint != null) {
                                if (endpoint.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {

                                    // Valid mass storage
                                    isMassStorage = true;
                                }
                            }
                        }
                    }
                }

                if (!isMassStorage) {
                    iterator.remove();
                }
            }

            for (UsbDevice usbDevice : usbDeviceList) {
                Log.d(TAG, "getUSBProblematic: Device Name" + usbDevice.getDeviceName());
                Log.d(TAG, "getUSBProblematic: Device Desc" + usbDevice.toString());
            }
        }
        return "";
    }

    //USB 체크 방식 두번째
    public static String getUSB() {
        File storageDirectory = new File("/storage");
        if (!storageDirectory.exists()) {
            Log.e(TAG, "getUSB: '/storage' does not exist on this device");
            return "";
        }

        File[] files = storageDirectory.listFiles();
        if (files == null) {
            Log.e(TAG, "getUSB: Null when requesting directories inside '/storage'");
            return "";
        }

        List<String> possibleUSBStorageMounts = new ArrayList<>();
        for (File file : files) {
            String path = file.getPath();
            if (path.contains("emulated") ||
                    path.contains("sdcard") ||
                    path.contains("self")) {
                Log.d(TAG, "getUSB: Found '" + path + "' - not USB");
            } else {
                possibleUSBStorageMounts.add(path);
            }
        }

        if (possibleUSBStorageMounts.size() == 0) {
            Log.e(TAG, "getUSB: Did not find any possible USB mounts");
            return "";
        }
        if (possibleUSBStorageMounts.size() > 1) {
            Log.d(TAG, "getUSB: Found multiple possible USB mount points, choosing the first one");
        }

        return possibleUSBStorageMounts.get(0);
    }

    public static Vector<String> getExternalMountsLegacy() {
        final Vector<String> out = new Vector<String>();
        String reg = "(?i).*vold.*(vfat|ntfs|exfat|fat32|ext3|ext4).*rw.*";
        String s = "";
        try {
            final Process process = new ProcessBuilder().command("mount")
                    .redirectErrorStream(true).start();
            process.waitFor();
            final InputStream is = process.getInputStream();
            final byte[] buffer = new byte[1024];
            while (is.read(buffer) != -1) {
                s = s + new String(buffer);
            }
            is.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        // parse output
        final String[] lines = s.split("\n");
        for (String line : lines) {
            if (!line.toLowerCase(Locale.US).contains("asec")) {
                if (line.matches(reg)) {
                    String[] parts = line.split(" ");
                    for (String part : parts) {
                        if (part.startsWith("/"))
                            if (!part.toLowerCase(Locale.US).contains("vold"))
                                out.add(part);
                    }
                }
            }
        }
        return out;
    }

}
