package model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import java.util.List;
import java.util.concurrent.ExecutionException;

import dao.KollusContentDao;
import entity.KollusContentEntity;

public class KollusContentRepository {

    private KollusContentDao mKollusContentDao;
    private LiveData<List<KollusContentEntity>> mAllKollusContents;

    //LiveData Database item. etlim.
    private LiveData<KollusContentEntity> mKollusContentItem;
    private LiveData<List<KollusContentEntity>> mSearchFolderKollusContentList;

    public KollusContentRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mKollusContentDao = db.kollusDao();
        mAllKollusContents = mKollusContentDao.getAllKollusContents();
    }

    public LiveData<List<KollusContentEntity>> getAllKollusContents() {
        return mAllKollusContents;
    }

    public LiveData<KollusContentEntity> getKollusContentItem(int id) {
        mKollusContentItem = mKollusContentDao.getKollusContent(id);
        return mKollusContentItem;
    }

    public LiveData<List<KollusContentEntity>> getLiveKollusContentList(String search_folder_path) {
        mSearchFolderKollusContentList = mKollusContentDao.getLiveKollusContentList(search_folder_path);
        return mSearchFolderKollusContentList;
    }

    public KollusContentEntity getKollusContent(int contentId) throws ExecutionException, InterruptedException {
        return new getKollusContentAsync(mKollusContentDao).execute(contentId).get();
//        return new getKollusContentAsync(mKollusContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, contentId).get();
    }

    public void insertKollusContent(KollusContentEntity content) {
        new insertKollusContentAsync(mKollusContentDao).execute(content);
//        new insertKollusContentAsync(mKollusContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, content);
    }

    public void updateKollusContent(KollusContentEntity content) {
        new updateKollusContentAsync(mKollusContentDao).execute(content);
//        new updateKollusContentAsync(mKollusContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, content);
    }

    public void deleteItemKollusContent(KollusContentEntity content) {
        new deleteItemKollusContentAsync(mKollusContentDao).execute(content);
//        new deleteItemKollusContentAsync(mKollusContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, content);
    }

    public void deleteIdKollusContent(int Id) {
        new deleteIdKollusContentAsync(mKollusContentDao, Id).execute();
//        new deleteIdKollusContentAsync(mKollusContentDao, Id).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void deleteAllKollusContents() {
        new deleteAllKollusContentAsync(mKollusContentDao).execute();
//        new deleteAllKollusContentAsync(mKollusContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /*********************************************************************************************/
    //20200522 custom database event add. etlim. repository async event add.
    public List<KollusContentEntity> findFolderList(String search_folder_path) throws ExecutionException, InterruptedException {
        return new findFolderListAsync(mKollusContentDao).execute(search_folder_path).get();
//        return new findFolderListAsync(mKollusContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, search_folder_path).get();
    }

    public List<KollusContentEntity> loadAllByIds(Integer[] ids) throws ExecutionException, InterruptedException {
        return new loadAllByIdsAsync(mKollusContentDao).execute(ids).get();
//        return new loadAllByIdsAsync(mKollusContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ids).get();
    }

    public KollusContentEntity findByMediaContentKey(String media_content_key, int disk_type) throws ExecutionException, InterruptedException {
        return new findByMediaContentKeyAsync(mKollusContentDao, media_content_key, disk_type).execute().get();
//        return new findByMediaContentKeyAsync(mKollusContentDao, media_content_key, disk_type).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    public KollusContentEntity findByThumbPath(String thumbPath) throws ExecutionException, InterruptedException {
        return new findByThumbPathAsync(mKollusContentDao).execute(thumbPath).get();
//        return new findByThumbPathAsync(mKollusContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, thumbPath).get();
    }

    public List<KollusContentEntity> recentlyKollusContentList(long createExpireDate) throws ExecutionException, InterruptedException {
        return new recentlyKollusContentListAsync(mKollusContentDao).execute(createExpireDate).get();
//        return new recentlyKollusContentListAsync(mKollusContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, createExpireDate).get();
    }

    public List<KollusContentEntity> recentlyFileList(long createExpireDate, String fileType) throws ExecutionException, InterruptedException {
//        return new recentlyFileListAsync(mKollusContentDao, fileType).execute(createExpireDate).get();
        return new recentlyFileListAsync(mKollusContentDao, fileType).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, createExpireDate).get();
    }

    public List<KollusContentEntity> searchFolderAllList(String folder_path, int disk_index) throws ExecutionException, InterruptedException {
        return new searchFolderAllListAsync(mKollusContentDao, folder_path, disk_index).execute().get();
//        return new searchFolderAllListAsync(mKollusContentDao, folder_path, disk_index).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    public List<KollusContentEntity> searchFolderPathFileList(String folder_path) throws ExecutionException, InterruptedException {
        return new searchFolderPathFileListAsync(mKollusContentDao, folder_path).execute().get();
//        return new searchFolderPathFileListAsync(mKollusContentDao, folder_path).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    public List<KollusContentEntity> searchTypeFolderPathFileList(String folder_path, String fileType) throws ExecutionException, InterruptedException {
        return new searchTypeFolderPathFileListAsync(mKollusContentDao, folder_path, fileType).execute().get();
//        return new searchTypeFolderPathFileListAsync(mKollusContentDao, folder_path, fileType).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    public boolean deleteFolder(String folder_path, int disk_index) throws ExecutionException, InterruptedException {
        return new deleteFolderAsync(mKollusContentDao, folder_path, disk_index).execute().get();
//        return new deleteFolderAsync(mKollusContentDao, folder_path, disk_index).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    public boolean deleteAllFolder(String folder_path) throws ExecutionException, InterruptedException {
        return new deleteAllFolderAsync(mKollusContentDao, folder_path).execute().get();
//        return new deleteAllFolderAsync(mKollusContentDao, folder_path).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    public boolean updateItem(String filepath, String filename, int id, long modifyTimeMillis) throws ExecutionException, InterruptedException {
        return new updateItemAsync(mKollusContentDao, filepath, filename, id, modifyTimeMillis).execute().get();
//        return new updateItemAsync(mKollusContentDao, filepath, filename, id, modifyTimeMillis).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    public boolean updateFileModify(String filepath, String folderPath, int id, long modifyTimeMillis) throws ExecutionException, InterruptedException {
        return new updateFileModifyAsync(mKollusContentDao, filepath, folderPath, id, modifyTimeMillis).execute().get();
//        return new updateFileModifyAsync(mKollusContentDao, filepath, folderPath, id, modifyTimeMillis).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    public boolean updateContent(int current_time, long recently_play_time, int expiration_date, int expiration_count, int total_expiration_count, int count_play_count, int lms_percent, String thumbnail_path, String screenshot_path, int id) throws ExecutionException, InterruptedException {
        return new updateContentAsync(mKollusContentDao, current_time, recently_play_time, expiration_date, expiration_count, total_expiration_count, count_play_count, lms_percent, thumbnail_path, screenshot_path, id).execute().get();
//        return new updateContentAsync(mKollusContentDao, current_time, recently_play_time, expiration_date, expiration_count, total_expiration_count, count_play_count, lms_percent, thumbnail_path, screenshot_path, id).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }


/********************************************************************************************/
    /**
     * NOTE: all write operations should be done in background thread,
     * otherwise the following error will be thrown
     * `java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.`
     */

    private static class getKollusContentAsync extends AsyncTask<Integer, Void, KollusContentEntity> {

        private KollusContentDao mKollusContentDaoAsync;

        getKollusContentAsync(KollusContentDao kollusContentDao) {
            mKollusContentDaoAsync = kollusContentDao;
        }

        @Override
        protected KollusContentEntity doInBackground(Integer... ids) {
            return mKollusContentDaoAsync.getKollusContentById(ids[0]);
        }
    }

    private static class insertKollusContentAsync extends AsyncTask<KollusContentEntity, Void, Long> {

        private KollusContentDao mKollusContentDaoAsync;

        insertKollusContentAsync(KollusContentDao kollusContentDao) {
            mKollusContentDaoAsync = kollusContentDao;
        }

        @Override
        protected Long doInBackground(KollusContentEntity... content) {
            long id = mKollusContentDaoAsync.insert(content[0]);
            return id;
        }
    }

    private static class updateKollusContentAsync extends AsyncTask<KollusContentEntity, Void, Void> {

        private KollusContentDao mKollusContentDaoAsync;

        updateKollusContentAsync(KollusContentDao kollusContentDao) {
            mKollusContentDaoAsync = kollusContentDao;
        }

        @Override
        protected Void doInBackground(KollusContentEntity... content) {
            mKollusContentDaoAsync.update(content[0]);
            return null;
        }
    }

    private static class deleteIdKollusContentAsync extends AsyncTask<Void, Void, Void> {

        private KollusContentDao mKollusContentDaoAsync;
        private int Id;

        deleteIdKollusContentAsync(KollusContentDao kollusContentDao, int id) {
            this.mKollusContentDaoAsync = kollusContentDao;
            this.Id = id;
        }

        @Override
        protected Void doInBackground(Void... param) {
            try {
                mKollusContentDaoAsync.deleteIdFile(Id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class deleteItemKollusContentAsync extends AsyncTask<KollusContentEntity, Void, Void> {

        private KollusContentDao mKollusContentDaoAsync;

        deleteItemKollusContentAsync(KollusContentDao kollusContentDao) {
            mKollusContentDaoAsync = kollusContentDao;
        }

        @Override
        protected Void doInBackground(KollusContentEntity... content) {
            mKollusContentDaoAsync.delete(content[0]);
            return null;
        }
    }

    private static class deleteAllKollusContentAsync extends AsyncTask<KollusContentEntity, Void, Void> {

        private KollusContentDao mKollusContentDaoAsync;

        deleteAllKollusContentAsync(KollusContentDao kollusContentDao) {
            mKollusContentDaoAsync = kollusContentDao;
        }

        @Override
        protected Void doInBackground(KollusContentEntity... content) {
            mKollusContentDaoAsync.deleteAll();
            return null;
        }
    }

    //20200521 etlim kollus renewal player async task. db edit.

    /************************** async Dao access start ********************************/
    private static class findFolderListAsync extends AsyncTask<String, Void, List<KollusContentEntity>> {

        private KollusContentDao mKollusContentDaoAsync;

        findFolderListAsync(KollusContentDao kollusContentDao) {
            mKollusContentDaoAsync = kollusContentDao;
        }

        @Override
        protected List<KollusContentEntity> doInBackground(String... search) {
            return mKollusContentDaoAsync.findFolderList(search[0]);
        }
    }

    private static class loadAllByIdsAsync extends AsyncTask<Integer[], Void, List<KollusContentEntity>> {

        private KollusContentDao mKollusContentDaoAsync;

        loadAllByIdsAsync(KollusContentDao kollusContentDao) {
            mKollusContentDaoAsync = kollusContentDao;
        }

        @Override
        protected List<KollusContentEntity> doInBackground(Integer[]... params) {
            Integer[] ids = params[0];

            return mKollusContentDaoAsync.loadAllByIds(ids);
        }
    }

    private static class findByMediaContentKeyAsync extends AsyncTask<Void, Void, KollusContentEntity> {

        private KollusContentDao mKollusContentDaoAsync;
        private String mMediaContentKey;
        private int DiskType;

        findByMediaContentKeyAsync(KollusContentDao kollusContentDao, String media_content_key, int disk_type) {
            mKollusContentDaoAsync = kollusContentDao;
            this.mMediaContentKey = media_content_key;
            this.DiskType = disk_type;
        }

        @Override
        protected KollusContentEntity doInBackground(Void... param) {
            return mKollusContentDaoAsync.findByMediaContentKey(mMediaContentKey, DiskType);
        }
    }

    private static class findByThumbPathAsync extends AsyncTask<String, Void, KollusContentEntity> {

        private KollusContentDao mKollusContentDaoAsync;

        findByThumbPathAsync(KollusContentDao kollusContentDao) {
            mKollusContentDaoAsync = kollusContentDao;
        }

        @Override
        protected KollusContentEntity doInBackground(String... thumbPath) {
            return mKollusContentDaoAsync.findByThumbPath(thumbPath[0]);
        }
    }

    private static class recentlyKollusContentListAsync extends AsyncTask<Long, Void, List<KollusContentEntity>> {

        private KollusContentDao mKollusContentDaoAsync;

        recentlyKollusContentListAsync(KollusContentDao kollusContentDao) {
            mKollusContentDaoAsync = kollusContentDao;
        }

        @Override
        protected List<KollusContentEntity> doInBackground(Long... createExpireDate) {
            return mKollusContentDaoAsync.recentlyKollusContentList(createExpireDate[0]);
        }
    }

    private static class recentlyFileListAsync extends AsyncTask<Long, Void, List<KollusContentEntity>> {

        private KollusContentDao mKollusContentDaoAsync;
        private String mFileType;

        recentlyFileListAsync(KollusContentDao kollusContentDao, String fileType) {
            mKollusContentDaoAsync = kollusContentDao;
            this.mFileType = fileType;
        }

        @Override
        protected List<KollusContentEntity> doInBackground(Long... createExpireDate) {
            return mKollusContentDaoAsync.recentlyFileList(createExpireDate[0], mFileType);
        }
    }

    private static class searchFolderAllListAsync extends AsyncTask<Void, Void, List<KollusContentEntity>> {

        private KollusContentDao mKollusContentDaoAsync;
        private String mSearch;
        private int mDiskIndex;

        searchFolderAllListAsync(KollusContentDao kollusContentDao, String search, int disk_index) {
            mKollusContentDaoAsync = kollusContentDao;
            this.mSearch = search;
            this.mDiskIndex = disk_index;
        }

        @Override
        protected List<KollusContentEntity> doInBackground(Void... param) {
            return mKollusContentDaoAsync.searchFolderAllList(mSearch, mDiskIndex);
        }
    }

    private static class searchFolderPathFileListAsync extends AsyncTask<Void, Void, List<KollusContentEntity>> {

        private KollusContentDao mKollusContentDaoAsync;
        private String mSearch;

        searchFolderPathFileListAsync(KollusContentDao kollusContentDao, String search) {
            mKollusContentDaoAsync = kollusContentDao;
            this.mSearch = search;
        }

        @Override
        protected List<KollusContentEntity> doInBackground(Void... param) {
            return mKollusContentDaoAsync.searchFolderPathFileList(mSearch);
        }
    }

    private static class searchTypeFolderPathFileListAsync extends AsyncTask<Void, Void, List<KollusContentEntity>> {

        private KollusContentDao mKollusContentDaoAsync;
        private String mSearch;
        private String mFileType;

        searchTypeFolderPathFileListAsync(KollusContentDao kollusContentDao, String search, String fileType) {
            mKollusContentDaoAsync = kollusContentDao;
            this.mSearch = search;
            this.mFileType = fileType;
        }

        @Override
        protected List<KollusContentEntity> doInBackground(Void... param) {
            return mKollusContentDaoAsync.searchTypeFolderPathFileList(mSearch, mFileType);
        }
    }

    private static class deleteAllFolderAsync extends AsyncTask<Void, Void, Boolean> {

        private KollusContentDao mKollusContentDaoAsync;
        private String mSearch;

        deleteAllFolderAsync(KollusContentDao kollusContentDao, String search) {
            mKollusContentDaoAsync = kollusContentDao;
            this.mSearch = search;
        }

        @Override
        protected Boolean doInBackground(Void... param) {
            try {
                mKollusContentDaoAsync.deleteAllFolder(mSearch);
            } catch (NullPointerException e) {
                e.printStackTrace();
                return false;
            } catch (RuntimeException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    private static class deleteFolderAsync extends AsyncTask<Void, Void, Boolean> {

        private KollusContentDao mKollusContentDaoAsync;
        private String mSearch;
        private int mDiskIndex;

        deleteFolderAsync(KollusContentDao kollusContentDao, String search, int disk_index) {
            mKollusContentDaoAsync = kollusContentDao;
            this.mSearch = search;
            this.mDiskIndex = disk_index;
        }

        @Override
        protected Boolean doInBackground(Void... param) {
            try {
                mKollusContentDaoAsync.deleteFolder(mSearch, mDiskIndex);
            } catch (NullPointerException e) {
                e.printStackTrace();
                return false;
            } catch (RuntimeException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    private static class updateItemAsync extends AsyncTask<Void, Void, Boolean> {

        private KollusContentDao mKollusContentDaoAsync;
        private String mFilePath;
        private String mFileName;
        private int mId;
        private long mModifyTimeMillis;

        updateItemAsync(KollusContentDao kollusContentDao, String filepath, String filename, int id, long modifyTimeMillis) {
            this.mKollusContentDaoAsync = kollusContentDao;
            this.mFilePath = filepath;
            this.mFileName = filename;
            this.mId = id;
            this.mModifyTimeMillis = modifyTimeMillis;
        }

        @Override
        protected Boolean doInBackground(Void... param) {
            try {
                mKollusContentDaoAsync.updateItem(mFilePath, mFileName, mId, mModifyTimeMillis);
            } catch (NullPointerException e) {
                e.printStackTrace();
                return false;
            } catch (RuntimeException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    private static class updateFileModifyAsync extends AsyncTask<Void, Void, Boolean> {

        private KollusContentDao mKollusContentDaoAsync;
        private String mFilePath;
        private String mFolderPath;
        private int mId;
        private long mModifyTimeMillis;

        updateFileModifyAsync(KollusContentDao kollusContentDao, String filepath, String folderPath, int id, long modifyTimeMillis) {
            this.mKollusContentDaoAsync = kollusContentDao;
            this.mFilePath = filepath;
            this.mFolderPath = folderPath;
            this.mId = id;
            this.mModifyTimeMillis = modifyTimeMillis;
        }

        @Override
        protected Boolean doInBackground(Void... param) {
            try {
                mKollusContentDaoAsync.updateFileModify(mFilePath, mFolderPath, mId, mModifyTimeMillis);
            } catch (NullPointerException e) {
                e.printStackTrace();
                return false;
            } catch (RuntimeException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    private static class updateContentAsync extends AsyncTask<Void, Void, Boolean> {

        private KollusContentDao mKollusContentDaoAsync;
        private int mCurrentTime;
        private long mRecentlyPlayTime;
        private int mExpirationDate;
        private int mExpirationCount;
        private int mTotalExpirationCount;
        private int mCountPlayCount;
        private int mLmsPercent;
        private String mThumbnailPath;
        private String mScreenshotPath;
        private int mId;

        updateContentAsync(KollusContentDao kollusContentDao, int current_time, long recently_play_time, int expiration_date, int expiration_count, int total_expiration_count, int count_play_count, int lms_percent, String thumbnail_path, String screenshot_path, int id) {
            this.mKollusContentDaoAsync = kollusContentDao;
            this.mCurrentTime = current_time;
            this.mRecentlyPlayTime = recently_play_time;
            this.mExpirationDate = expiration_date;
            this.mExpirationCount = expiration_count;
            this.mTotalExpirationCount = total_expiration_count;
            this.mCountPlayCount = count_play_count;
            this.mLmsPercent = lms_percent;
            this.mThumbnailPath = thumbnail_path;
            this.mScreenshotPath = screenshot_path;
            this.mId = id;

        }

        @Override
        protected Boolean doInBackground(Void... param) {
            try {
                mKollusContentDaoAsync.updateContent(mCurrentTime, mRecentlyPlayTime, mExpirationDate, mExpirationCount, mTotalExpirationCount, mCountPlayCount, mLmsPercent, mThumbnailPath, mScreenshotPath, mId);
            } catch (NullPointerException e) {
                e.printStackTrace();
                return false;
            } catch (RuntimeException e) {
                e.printStackTrace();
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    /************************** async Dao access end ********************************/

}
