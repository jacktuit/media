package testK;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import java.util.List;
import java.util.concurrent.ExecutionException;

import dao.KollusDownloadContentDao;
import entity.KollusDownloadContentEntity;

public class DownloadingContentRepository {

    private KollusDownloadContentDao mDownloadingContentDao;
    private LiveData<List<KollusDownloadContentEntity>> mAllDownloadingContents;
    private LiveData<KollusDownloadContentEntity> mDownloadingContentItem;

    public DownloadingContentRepository(Application application) {
        DownloadContentDatabase db = DownloadContentDatabase.getDatabase(application);
        mDownloadingContentDao = db.kollusDownloadingDao();
        mAllDownloadingContents = mDownloadingContentDao.getAllDownloadContents();
    }

    public LiveData<List<KollusDownloadContentEntity>> getAllDownloadingContents() {
        return mAllDownloadingContents;
    }

    public List<KollusDownloadContentEntity> getAllContents() throws ExecutionException, InterruptedException {
//        return new getAllContentsAsync(mDownloadingContentDao).execute().get();
        return new getAllContentsAsync(mDownloadingContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    public LiveData<KollusDownloadContentEntity> getLiveDownloadingContentItem(int id) {
        mDownloadingContentItem = mDownloadingContentDao.getDownloadContent(id);
        return mDownloadingContentItem;
    }

    public KollusDownloadContentEntity getDownloadingContent(int id) throws ExecutionException, InterruptedException {
//        return new getDownloadContentAsync(mDownloadingContentDao).execute(id).get();
        return new getDownloadContentAsync(mDownloadingContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,id).get();
    }

    public long insertDownloadContent(KollusDownloadContentEntity content) throws ExecutionException, InterruptedException {
//        new insertDownloadContentAsync(mDownloadingContentDao).execute(content);
       return new insertDownloadContentAsync(mDownloadingContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, content).get();
    }

    public void updateDownloadContent(KollusDownloadContentEntity content) {
//        new updateDownloadContentAsync(mDownloadingContentDao).execute(content);
        new updateDownloadContentAsync(mDownloadingContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, content);
    }

    public void deleteItemDownloadContent(KollusDownloadContentEntity content) {
//        new deleteItemDownloadAsync(mDownloadingContentDao).execute(content);
        new deleteItemDownloadAsync(mDownloadingContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,content);
    }

    public boolean deleteIdDownloadContent(int Id) throws ExecutionException, InterruptedException {
//        return new deleteIdDownloadContentAsync(mDownloadingContentDao, Id).execute().get();
        return new deleteIdDownloadContentAsync(mDownloadingContentDao, Id).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    public void deleteAllDownloadContents() {
//        new deleteAllDownloadingContentsAsync(mDownloadingContentDao).execute();
        new deleteAllDownloadingContentsAsync(mDownloadingContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public List<KollusDownloadContentEntity> loadAllByIds(Integer[] ids) throws ExecutionException, InterruptedException {
//        return new loadAllByIdsAsync(mDownloadingContentDao).execute(ids).get();
        return new loadAllByIdsAsync(mDownloadingContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,ids).get();
    }

    public List<KollusDownloadContentEntity> getDownloadContentByMediaContentKey(String media_content_key) throws ExecutionException, InterruptedException {
//        return new getDownloadContentByMediaContentKeyAsync(mDownloadingContentDao, media_content_key).execute().get();
        return new getDownloadContentByMediaContentKeyAsync(mDownloadingContentDao, media_content_key).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    public KollusDownloadContentEntity findDownloadingByMediaContentKey(String media_content_key, int disk_type) throws ExecutionException, InterruptedException {
//        return new findDownloadByMediaContentKeyAsync(mDownloadingContentDao, media_content_key, disk_type).execute().get();
        return new findDownloadByMediaContentKeyAsync(mDownloadingContentDao, media_content_key, disk_type).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    public KollusDownloadContentEntity findDownloadingByThumbPath(String thumbPath) throws ExecutionException, InterruptedException {
//        return new findDownloadByThumbPathAsync(mDownloadingContentDao).execute(thumbPath).get();
        return new findDownloadByThumbPathAsync(mDownloadingContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,thumbPath).get();
    }

    public List<KollusDownloadContentEntity> recentlyDownloadingContentList(long expireDate) throws ExecutionException, InterruptedException {
//        return new recentlyDownloadingContentListAsync(mDownloadingContentDao).execute(expireDate).get();
        return new recentlyDownloadingContentListAsync(mDownloadingContentDao).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,expireDate).get();
    }

    public boolean updateDownloadItem(long downloadReceivingSize, int download_percent, int download_status, int id) throws ExecutionException, InterruptedException {
//        return new updateDownloadItemAsync(mDownloadingContentDao, downloadReceivingSize, download_percent, download_status, id).execute().get();
        return new updateDownloadItemAsync(mDownloadingContentDao, downloadReceivingSize, download_percent, download_status, id).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    public boolean updateDownloadItemPercent(long downloadReceivingSize, int download_percent, int id) throws ExecutionException, InterruptedException {
//        return new updateDownloadItemPercentAsync(mDownloadingContentDao, downloadReceivingSize, download_percent, id).execute().get();
        return new updateDownloadItemPercentAsync(mDownloadingContentDao, downloadReceivingSize, download_percent, id).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    public boolean updateDownloadItemStatus(int download_status, int id) throws ExecutionException, InterruptedException {
//        return new updateDownloadItemStatusAsync(mDownloadingContentDao, download_status, id).execute().get();
        return new updateDownloadItemStatusAsync(mDownloadingContentDao, download_status, id).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    public boolean updateDownloadContent(long download_receiving_size, String filename, int id) throws ExecutionException, InterruptedException {
//        return new updateItemAsync(mDownloadingContentDao, download_receiving_size, filename, id).execute().get();
        return new updateItemAsync(mDownloadingContentDao, download_receiving_size, filename, id).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
    }

    //thread define
    private static class getDownloadContentAsync extends AsyncTask<Integer, Void, KollusDownloadContentEntity> {

        private KollusDownloadContentDao mDownloadContentsDaoAsync;

        getDownloadContentAsync(KollusDownloadContentDao downloadContentDao) {
            mDownloadContentsDaoAsync = downloadContentDao;
        }

        @Override
        protected KollusDownloadContentEntity doInBackground(Integer... ids) {
            return mDownloadContentsDaoAsync.getDownloadContentById(ids[0]);
        }
    }

    private static class insertDownloadContentAsync extends AsyncTask<KollusDownloadContentEntity, Void, Long> {

        private KollusDownloadContentDao mDownloadContentsDaoAsync;

        insertDownloadContentAsync(KollusDownloadContentDao downloadContentDao) {
            mDownloadContentsDaoAsync = downloadContentDao;
        }

        @Override
        protected Long doInBackground(KollusDownloadContentEntity... content) {
            long id = mDownloadContentsDaoAsync.insert(content[0]);
            return id;
        }
    }

    private static class updateDownloadContentAsync extends AsyncTask<KollusDownloadContentEntity, Void, Void> {

        private KollusDownloadContentDao mDownloadContentsDaoAsync;

        updateDownloadContentAsync(KollusDownloadContentDao downloadContentDao) {
            mDownloadContentsDaoAsync = downloadContentDao;
        }

        @Override
        protected Void doInBackground(KollusDownloadContentEntity... content) {
            mDownloadContentsDaoAsync.update(content[0]);
            return null;
        }
    }

    private static class deleteIdDownloadContentAsync extends AsyncTask<Void, Void, Boolean> {

        private KollusDownloadContentDao mDownloadContentsDaoAsync;
        private int Id;

        deleteIdDownloadContentAsync(KollusDownloadContentDao downloadContentDao, int id) {
            this.mDownloadContentsDaoAsync = downloadContentDao;
            this.Id = id;
        }

        @Override
        protected Boolean doInBackground(Void... param) {
            try {
                mDownloadContentsDaoAsync.deleteIdFile(Id);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    private static class deleteItemDownloadAsync extends AsyncTask<KollusDownloadContentEntity, Void, Void> {

        private KollusDownloadContentDao mDownloadContentsDaoAsync;

        deleteItemDownloadAsync(KollusDownloadContentDao downloadContentDao) {
            mDownloadContentsDaoAsync = downloadContentDao;
        }

        @Override
        protected Void doInBackground(KollusDownloadContentEntity... content) {
            mDownloadContentsDaoAsync.delete(content[0]);
            return null;
        }
    }

    private static class deleteAllDownloadingContentsAsync extends AsyncTask<KollusDownloadContentEntity, Void, Void> {

        private KollusDownloadContentDao mDownloadContentsDaoAsync;

        deleteAllDownloadingContentsAsync(KollusDownloadContentDao downloadContentDao) {
            mDownloadContentsDaoAsync = downloadContentDao;
        }

        @Override
        protected Void doInBackground(KollusDownloadContentEntity... content) {
            mDownloadContentsDaoAsync.deleteAll();
            return null;
        }
    }

    private static class loadAllByIdsAsync extends AsyncTask<Integer[], Void, List<KollusDownloadContentEntity>> {

        private KollusDownloadContentDao mDownloadContentsDaoAsync;

        loadAllByIdsAsync(KollusDownloadContentDao downloadContentDao) {
            mDownloadContentsDaoAsync = downloadContentDao;
        }

        @Override
        protected List<KollusDownloadContentEntity> doInBackground(Integer[]... params) {
            Integer[] ids = params[0];

            return mDownloadContentsDaoAsync.loadAllByIds(ids);
        }
    }

    private static class getDownloadContentByMediaContentKeyAsync extends AsyncTask<Void, Void, List<KollusDownloadContentEntity>> {

        private KollusDownloadContentDao mDownloadContentsDaoAsync;
        private String mMediaContentKey;

        getDownloadContentByMediaContentKeyAsync(KollusDownloadContentDao downloadContentDao, String media_content_key) {
            mDownloadContentsDaoAsync = downloadContentDao;
            this.mMediaContentKey = media_content_key;
        }

        @Override
        protected List<KollusDownloadContentEntity> doInBackground(Void... param) {
            return mDownloadContentsDaoAsync.getMediaContentKeyContent(mMediaContentKey);
        }
    }

    private static class findDownloadByMediaContentKeyAsync extends AsyncTask<Void, Void, KollusDownloadContentEntity> {

        private KollusDownloadContentDao mDownloadContentsDaoAsync;
        private String mMediaContentKey;
        private int DiskType;

        findDownloadByMediaContentKeyAsync(KollusDownloadContentDao downloadContentDao, String media_content_key, int disk_type) {
            mDownloadContentsDaoAsync = downloadContentDao;
            this.mMediaContentKey = media_content_key;
            this.DiskType = disk_type;
        }

        @Override
        protected KollusDownloadContentEntity doInBackground(Void... param) {
            return mDownloadContentsDaoAsync.findByMediaContentKey(mMediaContentKey, DiskType);
        }
    }

    private static class findDownloadByThumbPathAsync extends AsyncTask<String, Void, KollusDownloadContentEntity> {

        private KollusDownloadContentDao mDownloadContentsDaoAsync;

        findDownloadByThumbPathAsync(KollusDownloadContentDao downloadContentDao) {
            mDownloadContentsDaoAsync = downloadContentDao;
        }

        @Override
        protected KollusDownloadContentEntity doInBackground(String... thumbPath) {
            return mDownloadContentsDaoAsync.findByThumbPath(thumbPath[0]);
        }
    }

    private static class recentlyDownloadingContentListAsync extends AsyncTask<Long, Void, List<KollusDownloadContentEntity>> {

        private KollusDownloadContentDao mDownloadContentsDaoAsync;

        recentlyDownloadingContentListAsync(KollusDownloadContentDao downloadContentDao) {
            mDownloadContentsDaoAsync = downloadContentDao;
        }

        @Override
        protected List<KollusDownloadContentEntity> doInBackground(Long... createExpireDate) {
            return mDownloadContentsDaoAsync.recentlyKollusContentList(createExpireDate[0]);
        }
    }

    private static class updateItemAsync extends AsyncTask<Void, Void, Boolean> {

        private KollusDownloadContentDao mDownloadContentsDaoAsync;
        private long mDownloadReceivingSize;
        private String mFileName;
        private int mId;

        updateItemAsync(KollusDownloadContentDao downloadContentDao, long download_receiving_size, String filename, int id) {
            this.mDownloadContentsDaoAsync = downloadContentDao;
            this.mDownloadReceivingSize = download_receiving_size;
            this.mFileName = filename;
            this.mId = id;
        }

        @Override
        protected Boolean doInBackground(Void... param) {
            try {
                mDownloadContentsDaoAsync.updateItem(mDownloadReceivingSize, mFileName, mId);
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

    private static class updateDownloadItemPercentAsync extends AsyncTask<Void, Void, Boolean> {

        private KollusDownloadContentDao mDownloadContentsDaoAsync;
        private long mDownloadReceivingSize;
        private int mDownloadPercent;
        private int mId;

        updateDownloadItemPercentAsync(KollusDownloadContentDao downloadContentDao, long downloadReceivingSize, int download_percent, int id) {
            this.mDownloadContentsDaoAsync = downloadContentDao;
            this.mDownloadReceivingSize = downloadReceivingSize;
            this.mDownloadPercent = download_percent;
            this.mId = id;

        }

        @Override
        protected Boolean doInBackground(Void... param) {
            try {
                mDownloadContentsDaoAsync.updateDownloadPercentUpdateItem(mDownloadReceivingSize, mDownloadPercent, mId);
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

    private static class updateDownloadItemStatusAsync extends AsyncTask<Void, Void, Boolean> {

        private KollusDownloadContentDao mDownloadContentsDaoAsync;
        private int mDownloadStatus;
        private int mId;

        updateDownloadItemStatusAsync(KollusDownloadContentDao downloadContentDao, int download_status, int id) {
            this.mDownloadContentsDaoAsync = downloadContentDao;
            this.mDownloadStatus = download_status;
            this.mId = id;

        }

        @Override
        protected Boolean doInBackground(Void... param) {
            try {
                mDownloadContentsDaoAsync.updateDownloadStatusItem(mDownloadStatus, mId);
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

    private static class updateDownloadItemAsync extends AsyncTask<Void, Void, Boolean> {

        private KollusDownloadContentDao mDownloadContentsDaoAsync;
        private long mDownloadReceivingSize;
        private int mDownloadPercent;
        private int mDownloadStatus;
        private int mId;

        updateDownloadItemAsync(KollusDownloadContentDao downloadContentDao, long downloadReceivingSize, int download_percent, int download_status, int id) {
            this.mDownloadContentsDaoAsync = downloadContentDao;
            this.mDownloadReceivingSize = downloadReceivingSize;
            this.mDownloadPercent = download_percent;
            this.mDownloadStatus = download_status;
            this.mId = id;

        }

        @Override
        protected Boolean doInBackground(Void... param) {
            try {
                mDownloadContentsDaoAsync.updateDownloadItem(mDownloadReceivingSize, mDownloadPercent, mDownloadStatus, mId);
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

    private static class getAllContentsAsync extends AsyncTask<Integer[], Void, List<KollusDownloadContentEntity>> {

        private KollusDownloadContentDao mDownloadContentsDaoAsync;

        getAllContentsAsync(KollusDownloadContentDao downloadContentDao) {
            mDownloadContentsDaoAsync = downloadContentDao;
        }

        @Override
        protected List<KollusDownloadContentEntity> doInBackground(Integer[]... params) {
            return mDownloadContentsDaoAsync.getAllContents();
        }
    }
}