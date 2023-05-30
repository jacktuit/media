package dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

import entity.KollusContentEntity;


/**
 * Created by etlim. 20200713.
 */

@Dao
public interface KollusContentDao {

    @Query("SELECT * FROM kollus ORDER BY id DESC")
    LiveData<List<KollusContentEntity>> getAllKollusContents();

    @Query("SELECT * FROM kollus WHERE id=:id")
    KollusContentEntity getKollusContentById(int id);

    @Query("SELECT * FROM kollus WHERE id=:id")
    LiveData<KollusContentEntity> getKollusContent(int id);

    //etlim, db query add. 20200520
    @Query("SELECT * FROM kollus WHERE folder_path LIKE :search_folder_path")
    LiveData<List<KollusContentEntity>> getLiveKollusContentList(String search_folder_path);

    @Query("SELECT * FROM kollus WHERE folder_path LIKE :search_folder_path")
    List<KollusContentEntity> findFolderList(String search_folder_path);

    @Query("SELECT * FROM kollus WHERE id IN (:ids)")
    List<KollusContentEntity> loadAllByIds(Integer[] ids);

    @Query("SELECT * FROM kollus WHERE media_content_key LIKE :media_content_key AND " + "disk_type=:disk_type LIMIT 1")
    KollusContentEntity findByMediaContentKey(String media_content_key, int disk_type);

    @Query("SELECT * FROM kollus WHERE thumbnail_path LIKE :thumbPath LIMIT 1")
    KollusContentEntity findByThumbPath(String thumbPath);

    @Query("SELECT * FROM kollus WHERE file_create > :createExpireDate")
    List<KollusContentEntity> recentlyKollusContentList(long createExpireDate);

    @Query("SELECT * FROM kollus WHERE file_create > :createExpireDate AND " + "file_type=:fileType ORDER BY file_create DESC")
    List<KollusContentEntity> recentlyFileList(long createExpireDate, String fileType);

    @Query("SELECT * FROM kollus WHERE folder_path LIKE :search_folder_path || '%' AND " + "disk_index=:disk_index")
    List<KollusContentEntity> searchFolderAllList(String search_folder_path, int disk_index);

    @Query("SELECT * FROM kollus WHERE folder_path LIKE :search_folder_path || '%'")
    List<KollusContentEntity> searchFolderPathFileList(String search_folder_path);

    @Query("SELECT * FROM kollus WHERE folder_path LIKE :search_folder_path || '%' AND " + "file_type=:fileType")
    List<KollusContentEntity> searchTypeFolderPathFileList(String search_folder_path, String fileType);

    @Query("DELETE FROM kollus WHERE folder_path LIKE :search_folder_path || '%' AND " + "disk_index=:disk_index")
    void deleteFolder(String search_folder_path, int disk_index);

    @Query("DELETE FROM kollus WHERE folder_path LIKE :search_folder_path || '%'")
    void deleteAllFolder(String search_folder_path);

    @Query("DELETE FROM kollus WHERE id=:id")
    void deleteIdFile(int id);

    /**
     * Updating only title and description
     * By order id
     */
    @Query("UPDATE kollus SET filepath = :filepath, filename= :filename, file_edit= :modifyTimeMillis WHERE id =:id")
    void updateItem(String filepath, String filename, int id, long modifyTimeMillis);

    /**
     * Updating only file path and folder path
     * By order id
     */
    @Query("UPDATE kollus SET filepath = :filepath, folder_path= :folderPath, file_edit= :modifyTimeMillis WHERE id =:id")
    void updateFileModify(String filepath, String folderPath, int id, long modifyTimeMillis);

    /**
     * Updating only file path and folder path
     * By order id
     */
    @Query("UPDATE kollus SET `current_time` = :current_time, recently_play_time= :recently_play_time, expiration_date= :expiration_date, expiration_count= :expiration_count, total_expiration_count= :total_expiration_count, count_play_count= :count_play_count, lms_percent= :lms_percent, thumbnail_path= :thumbnail_path, screenshot_path= :screenshot_path WHERE id =:id")
    void updateContent(int current_time, long recently_play_time, int expiration_date, int expiration_count, int total_expiration_count, int count_play_count, int lms_percent, String thumbnail_path, String screenshot_path, int id);

    //default query action
    @Insert
    long insert(KollusContentEntity content);

    @Update
    void update(KollusContentEntity content);

    @Delete
    void delete(KollusContentEntity content);

    @Query("DELETE FROM kollus")
    void deleteAll();

//    @Insert
//    void insertAll(KollusContentEntity... users);

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
