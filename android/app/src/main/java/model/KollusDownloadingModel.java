package model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;



import java.util.List;
import java.util.concurrent.ExecutionException;

import entity.KollusDownloadContentEntity;
import testK.DownloadingContentRepository;

public class KollusDownloadingModel extends AndroidViewModel {

    private DownloadingContentRepository mDownloadRepository;
    private LiveData<List<KollusDownloadContentEntity>> mDownloadContents;

    public KollusDownloadingModel(@NonNull Application application) {
        super(application);

        mDownloadRepository = new DownloadingContentRepository(application);
    }

    public LiveData<List<KollusDownloadContentEntity>> getDownloadingContents() {
        if (mDownloadContents == null) {
            mDownloadContents = mDownloadRepository.getAllDownloadingContents();
        }

        return mDownloadContents;
    }

    public List<KollusDownloadContentEntity> getAllContents() throws ExecutionException, InterruptedException {
        return mDownloadRepository.getAllContents();
    }

    public KollusDownloadContentEntity getDownloadingContentContent(int id) throws ExecutionException, InterruptedException {
        return mDownloadRepository.getDownloadingContent(id);
    }

    public long insertDownloadingContent(KollusDownloadContentEntity content) throws ExecutionException, InterruptedException {
        return mDownloadRepository.insertDownloadContent(content);
    }

    public void updateDownloadingContent(KollusDownloadContentEntity content) {
        mDownloadRepository.updateDownloadContent(content);
    }

    public void deleteDownloadingContent(KollusDownloadContentEntity content) {
        mDownloadRepository.deleteItemDownloadContent(content);
    }

    public boolean deleteIdDownloadingContent(int id) throws ExecutionException, InterruptedException{
       return mDownloadRepository.deleteIdDownloadContent(id);
    }

    public void deleteAllDownloadingContents() {
        mDownloadRepository.deleteAllDownloadContents();
    }

    //custom db find.
    public List<KollusDownloadContentEntity> loadAllByIds(Integer[] ids) throws ExecutionException, InterruptedException {
        return mDownloadRepository.loadAllByIds(ids);
    }

    public List<KollusDownloadContentEntity> getDownloadingContentByMediaContentKey(String media_content_key) throws ExecutionException, InterruptedException {
        return mDownloadRepository.getDownloadContentByMediaContentKey(media_content_key);
    }

    public KollusDownloadContentEntity findDownloadingContentsByMediaContentKey(String media_content_key, int disk_type) throws ExecutionException, InterruptedException {
        return mDownloadRepository.findDownloadingByMediaContentKey(media_content_key, disk_type);
    }

    public KollusDownloadContentEntity findDownloadingContentByThumbPath(String thumbPath) throws ExecutionException, InterruptedException {
        return mDownloadRepository.findDownloadingByThumbPath(thumbPath);
    }

    public List<KollusDownloadContentEntity> recentlyDownloadingContentList(long expireDateTimeMs) throws ExecutionException, InterruptedException {
        return mDownloadRepository.recentlyDownloadingContentList(expireDateTimeMs);
    }

    public boolean updateDownloadingContent(long download_receiving_size, String filename, int id) throws ExecutionException, InterruptedException {
        return mDownloadRepository.updateDownloadContent(download_receiving_size, filename, id);
    }

    public boolean updateDownloadingItem(long downloadReceivingSize, int download_percent, int download_status, int id) throws ExecutionException, InterruptedException {
        return mDownloadRepository.updateDownloadItem(downloadReceivingSize, download_percent, download_status, id);
    }

    public boolean updateDownloadItemPercent(long downloadReceivingSize, int download_percent, int id) throws ExecutionException, InterruptedException {
        return mDownloadRepository.updateDownloadItemPercent(downloadReceivingSize, download_percent, id);
    }

    public boolean updateDownloadItemStatus(int download_status, int id) throws ExecutionException, InterruptedException {
        return mDownloadRepository.updateDownloadItemStatus(download_status, id);
    }


}