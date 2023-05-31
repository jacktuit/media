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
