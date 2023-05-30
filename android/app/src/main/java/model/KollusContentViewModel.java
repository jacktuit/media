package model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;



import java.util.List;
import java.util.concurrent.ExecutionException;

import entity.KollusContentEntity;

public class KollusContentViewModel extends AndroidViewModel {

    private KollusContentRepository mRepository;
    private LiveData<List<KollusContentEntity>> mKollusContents;
    private LiveData<KollusContentEntity> mKollusContentItem;
    private LiveData<List<KollusContentEntity>> mFolderSearchKollusContentList;

    public KollusContentViewModel(@NonNull Application application) {
        super(application);

        mRepository = new KollusContentRepository(application);
    }

    public LiveData<List<KollusContentEntity>> getKollusContents() {
        if (mKollusContents == null) {
            mKollusContents = mRepository.getAllKollusContents();
        }

        return mKollusContents;
    }

    //Kollus Content Query function add. 20200525 etlim.
    public LiveData<KollusContentEntity> getKollusContentItem(int id) {
//        if (mKollusContentItem == null) {
            mKollusContentItem = mRepository.getKollusContentItem(id);
//        }

        return mKollusContentItem;
    }

    public KollusContentEntity getKollusContent(int id) throws ExecutionException, InterruptedException {
        return mRepository.getKollusContent(id);
    }

    public void insertKollusContent(KollusContentEntity content) {
        mRepository.insertKollusContent(content);
    }

    public void updateKollusContent(KollusContentEntity content) {
        mRepository.updateKollusContent(content);
    }

    public void deleteKollusContent(KollusContentEntity content) {
        mRepository.deleteItemKollusContent(content);
    }

    public void deleteIdKollusContent(int id) {
        mRepository.deleteIdKollusContent(id);
    }

    public void deleteAllKollusContents() {
        mRepository.deleteAllKollusContents();
    }


    /******************************** db view model start *************************************/
    //custom db action add. etlim
    public List<KollusContentEntity> findFolderList(String search_folder_path) throws ExecutionException, InterruptedException {
       return mRepository.findFolderList(search_folder_path);
    }

    public List<KollusContentEntity> loadAllByIds(Integer[] ids) throws ExecutionException, InterruptedException {
        return mRepository.loadAllByIds(ids);
    }

    public KollusContentEntity findByMediaContentKey(String media_content_key, int disk_type) throws ExecutionException, InterruptedException {
        return mRepository.findByMediaContentKey(media_content_key, disk_type);
    }

    public KollusContentEntity findByThumbPath(String thumbPath) throws ExecutionException, InterruptedException {
        return mRepository.findByThumbPath(thumbPath);
    }

    public List<KollusContentEntity> recentlyKollusContentList(long createExpireDate) throws ExecutionException, InterruptedException {
        return mRepository.recentlyKollusContentList(createExpireDate);
    }

    public List<KollusContentEntity> recentlyFileList(long createExpireDate, String fileType) throws ExecutionException, InterruptedException {
        return mRepository.recentlyFileList(createExpireDate, fileType);
    }

    public List<KollusContentEntity> searchFolderAllList(String folder_path, int disk_index) throws ExecutionException, InterruptedException {
        return mRepository.searchFolderAllList(folder_path, disk_index);
    }

    public List<KollusContentEntity> searchFolderPathFileList(String folder_path) throws ExecutionException, InterruptedException {
        return mRepository.searchFolderPathFileList(folder_path);
    }

    public List<KollusContentEntity> searchTypeFolderPathFileList(String folder_path, String fileType) throws ExecutionException, InterruptedException {
        return mRepository.searchTypeFolderPathFileList(folder_path, fileType);
    }

    public boolean deleteFolder(String folder_path, int disk_index) throws ExecutionException, InterruptedException {
        return mRepository.deleteFolder(folder_path, disk_index);
    }

    public boolean updateItem(String filepath, String filename, int id, long modifyTimeMillis) throws ExecutionException, InterruptedException {
        return mRepository.updateItem(filepath, filename, id , modifyTimeMillis);
    }

    public boolean updateFileModify(String filepath, String folderPath, int id, long modifyTimeMillis) throws ExecutionException, InterruptedException {
        return mRepository.updateFileModify(filepath, folderPath, id , modifyTimeMillis);
    }

    public boolean updateContent(int current_time, long recently_play_time, int expiration_date, int expiration_count, int total_expiration_count, int count_play_count, int lms_percent, String thumbnail_path, String screenshot_path, int id) throws ExecutionException, InterruptedException {
        return mRepository.updateContent(current_time, recently_play_time, expiration_date, expiration_count, total_expiration_count, count_play_count, lms_percent, thumbnail_path, screenshot_path, id);
    }

    public LiveData<List<KollusContentEntity>> getLiveKollusContentList(String search_folder_path){
        mFolderSearchKollusContentList = mRepository.getLiveKollusContentList(search_folder_path);
        return mFolderSearchKollusContentList;
    }

    public boolean deleteAllFolder(String search_folder_path) throws ExecutionException, InterruptedException {
        return mRepository.deleteAllFolder(search_folder_path);
    }

    /*********************************** db view model end **********************************/
}
