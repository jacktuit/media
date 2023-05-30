package dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

import entity.KollusDownloadContentEntity;

/**
 * Created by etlim. 20200713.
 */

@Dao
public interface KollusDownloadContentDao {

    @Query("SELECT * FROM download ORDER BY id ASC")
    LiveData<List<KollusDownloadContentEntity>> getAllDownloadContents();

    @Query("SELECT * FROM download ORDER BY id ASC")
    List<KollusDownloadContentEntity> getAllContents();

    @Query("SELECT * FROM download WHERE id=:id")
    KollusDownloadContentEntity getDownloadContentById(int id);

    @Query("SELECT * FROM download WHERE id=:id")
    LiveData<KollusDownloadContentEntity> getDownloadContent(int id);

    @Query("SELECT * FROM download WHERE media_content_key LIKE :media_content_key")
    List<KollusDownloadContentEntity> getMediaContentKeyContent(String media_content_key);

    @Query("SELECT * FROM download WHERE id IN (:ids)")
    List<KollusDownloadContentEntity> loadAllByIds(Integer[] ids);

    @Query("SELECT * FROM download WHERE media_content_key LIKE :media_content_key AND " + "disk_type=:disk_type LIMIT 1")
    KollusDownloadContentEntity findByMediaContentKey(String media_content_key, int disk_type);

    @Query("SELECT * FROM download WHERE thumbnail_path LIKE :thumbPath LIMIT 1")
    KollusDownloadContentEntity findByThumbPath(String thumbPath);

    @Query("SELECT * FROM download WHERE download_start > :createExpireDate")
    List<KollusDownloadContentEntity> recentlyKollusContentList(long createExpireDate);

//    @Query("DELETE FROM download WHERE folder_path LIKE :search_folder_path || '%'")
//    void deleteAllFolder(String search_folder_path);

    @Query("DELETE FROM download WHERE id=:id")
    void deleteIdFile(int id);

    @Query("UPDATE download SET download_receiving_size = :downloadReceivingSize, filename= :filename WHERE id =:id")
    void updateItem(long downloadReceivingSize, String filename, int id);

    @Query("UPDATE download SET download_receiving_size = :downloadReceivingSize, download_percent= :download_percent, download_status= :download_status WHERE id =:id")
    void updateDownloadItem(long downloadReceivingSize, int download_percent, int download_status, int id);

    @Query("UPDATE download SET download_receiving_size = :downloadReceivingSize, download_percent= :download_percent WHERE id =:id")
    void updateDownloadPercentUpdateItem(long downloadReceivingSize, int download_percent, int id);

    @Query("UPDATE download SET download_status= :download_status WHERE id =:id")
    void updateDownloadStatusItem(int download_status, int id);

    //default query action
    @Insert
    long insert(KollusDownloadContentEntity content);

    @Update
    void update(KollusDownloadContentEntity content);

    @Delete
    void delete(KollusDownloadContentEntity content);

    @Query("DELETE FROM download")
    void deleteAll();

//    @Insert
//    void insertAll(KollusDownloadContentEntity... users);

    /********************** sql 문법 사용법 start *********************/

//    //List data load
//    @Dao
//    public interface MyDao {
//        @Query("SELECT first_name, last_name FROM user WHERE region IN (:regions)")
//        public List<NameTuple> loadUsersFromRegions(List<String> regions);
//    }
//
//    //LiveData load
//    @Dao
//    public interface MyDao {
//        @Query("SELECT first_name, last_name FROM user WHERE region IN (:regions)")
//        public LiveData<List<User>> loadUsersFromRegionsSync(List<String> regions);
//    }

    //    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
//            + "last_name LIKE :last LIMIT 1")
//    User findByName(String first, String last);

//    @Query("SELECT * FROM kollus WHERE folder_path LIKE :search " + "OR note LIKE :search")
//    List<KollusContentEntity> findSameFolderList(String search);

//    @Query("SELECT * FROM kollus WHERE file_create > :createExpireDate")
//    KollusContentEntity[] loadAllUsersOlderThan(int createExpireDate);

//    @Query("SELECT * FROM kollus WHERE folder_path LIKE '%search%' ")
//    List<KollusContentEntity> findFolderL1t(String search);
//
//    @Query("SELECT * FROM kollus " +
//            "WHERE folder_path LIKE '% :search %' " +
//            "GROUP BY folder_path " +
//            "ORDER BY folder_path " +
//            "LIMIT :limit")
//    List<KollusContentEntity> findFolderL2t(String search, int limit);
//    String param = "%"+mMyString+"%";
//    @Query("SELECT * FROM track_table WHERE title LIKE :param" + " OR artist LIKE :param" + " OR album like :param")
//    List< Track > search(String param);
//   //특정 문자가 포함된 리스트 처리
//@Query("SELECT * FROM hamster WHERE name LIKE '%' || :search || '%'")
//    SELECT [컬럼명] FROM [테이블명] WHERE [컬럼명] LIKE '특정문자열%'
//    '김' 으로 시작하는 사람을 모두 조회
//    ex) SELECT id,name FROM member WHERE name LIKE '김%'
//    result) 김건모
//    '진'으로 끝나는 사람을 모두 조회
//    ex) SELECT id,name FROM member WHERE name LIKE '%진'
//    result) 김진
//
//    //특정 문자열이 있는 리스트 조회
//    SELECT [컬럼명] FROM [테이블명] WHERE [컬럼명] LIKE '%특정문자열%'
//    ex) SELECT id,p_name FROM member WHERE p_name LIKE '%프린터%'
//    result) 삼성프린터F
//
//    //특정 문자로 끝나는 사람 + 외자인 사람 조회
//    SELECT [컬럼명] FROM [테이블명] WHERE [컬럼명] LIKE '특정문자열?'
//    '김' + 외자인 사람을 모두 조회
//    ex) SELECT id,name FROM member WHERE name LIKE '김?'
//    result) 김진

    //범위를 정하여 리스트 가져오기
//    @Query("SELECT * FROM kollus WHERE id BETWEEN :minAge AND :maxAge")
//    KollusContentEntity[] loadAllUsersBetweenAges(int minAge, int maxAge);

//    오름차순 정렬 (ASC 생략)
//    SELECT * FROM 테이블 ORDER BY 컬럼1 ASC;
//
//    내림차순 정렬
//    SELECT * FROM 테이블 ORDER BY 컬럼1 DESC;
//
//    age 오름차순 정렬
//    SELECT name, age FROM table_name ORDER BY age ASC;
//
//    age 오름차순 정렬 (ASC 생략)
//    SELECT name, age FROM table_name ORDER BY age;
//
//    age 내림차순 정렬
//    SELECT name, age FROM table_name ORDER BY age DESC;

    /********************* sql 문법 사용법 end  ************************/
}