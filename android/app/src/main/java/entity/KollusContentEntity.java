package entity;


import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "kollus")
public class KollusContentEntity {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "note", defaultValue = "NULL")
    String note;

    @ColumnInfo(name = "timestamp")
    Date timestamp;

    //Kollus Content data
    @ColumnInfo(name = "filepath")
    String filepath;

    @ColumnInfo(name = "filename")
    String filename;

    @ColumnInfo(name = "file_type")
    String file_type;

    @ColumnInfo(name = "file_create", defaultValue = "0")
    long file_create;

    @ColumnInfo(name = "file_edit", defaultValue = "0")
    long file_edit;

    @ColumnInfo(name = "folder_path")
    String folder_path;

    @ColumnInfo(name = "file_size", defaultValue = "0")
    long file_size;

    @ColumnInfo(name = "duration", defaultValue = "0")
    int duration;

    @ColumnInfo(name = "current_time", defaultValue = "0")
    int current_time;

    @ColumnInfo(name = "recently_play_time", defaultValue = "0")
    long recently_play_time;

    @ColumnInfo(name = "expiration_date", defaultValue = "0")
    int expiration_date;

    @ColumnInfo(name = "expiration_count", defaultValue = "0")
    int expiration_count;

    @ColumnInfo(name = "total_expiration_count", defaultValue = "0")
    int total_expiration_count;

    @ColumnInfo(name = "count_play_count", defaultValue = "0")
    int count_play_count;

    @ColumnInfo(name = "disk_index")
    int disk_index;

    @ColumnInfo(name = "disk_type")
    int disk_type;

    @ColumnInfo(name = "lms_percent", defaultValue = "0")
    int lms_percent;

    @ColumnInfo(name = "thumbnail_path")
    String thumbnail_path;

    @ColumnInfo(name = "media_content_key")
    String media_content_key;

    @ColumnInfo(name = "screenshot_path")
    String screenshot_path;

    @ColumnInfo(name = "thumbnail_bitmap")
    Bitmap thumbnail_bitmap;

    //만들지 않고 싶은 필드를 추가할 경우.
    @Ignore
    Bitmap picture;

    public KollusContentEntity(@Nullable String note, @NonNull String filepath, @NonNull String filename,
                               @NonNull String file_type, @NonNull long file_create, @NonNull long file_edit,
                               @NonNull String folder_path, @NonNull long file_size, @NonNull int duration,
                               @NonNull int current_time, @NonNull long recently_play_time, @NonNull int expiration_date,
                               @NonNull int expiration_count, @NonNull int total_expiration_count, @NonNull int count_play_count,
                               @NonNull int disk_index, @NonNull int disk_type, @NonNull int lms_percent,
                               @NonNull String thumbnail_path, @NonNull String media_content_key, @Nullable String screenshot_path, Bitmap thumbnail_bitmap) {
        this.note = note;
        // TODO - not sure about this
        this.timestamp = new Date();

        //kollus data set
        this.filepath = filepath;
        this.filename = filename;
        this.file_type = file_type;
        this.file_create = file_create;
        this.file_edit = file_edit;
        this.folder_path = folder_path;
        this.file_size = file_size;
        this.duration = duration;
        this.current_time = current_time;
        this.recently_play_time = recently_play_time;
        this.expiration_date = expiration_date;
        this.expiration_count = expiration_count;
        this.total_expiration_count = total_expiration_count;
        this.count_play_count = count_play_count;
        this.disk_index = disk_index;
        this.disk_type = disk_type;
        this.lms_percent = lms_percent;
        this.thumbnail_path = thumbnail_path;
        this.media_content_key = media_content_key;
        this.screenshot_path = screenshot_path;
        this.thumbnail_bitmap = thumbnail_bitmap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    //Kollus Content DB Function

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public long getFile_create() {
        return file_create;
    }

    public void setFile_create(long file_create) {
        this.file_create = file_create;
    }

    public long getFile_edit() {
        return file_edit;
    }

    public void setFile_edit(long file_edit) {
        this.file_edit = file_edit;
    }

    public String getFolder_path() {
        return folder_path;
    }

    public void setFolder_path(String folder_path) {
        this.folder_path = folder_path;
    }

    public long getFile_size() {
        return file_size;
    }

    public void setFile_size(long file_size) {
        this.file_size = file_size;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(int current_time) {
        this.current_time = current_time;
    }

    public long getRecently_play_time() {
        return recently_play_time;
    }

    public void setRecently_play_time(long recently_play_time) {
        this.recently_play_time = recently_play_time;
    }

    public int getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(int expiration_date) {
        this.expiration_date = expiration_date;
    }

    public int getExpiration_count() {
        return expiration_count;
    }

    public void setExpiration_count(int expiration_count) {
        this.expiration_count = expiration_count;
    }

    public int getTotal_expiration_count() {
        return total_expiration_count;
    }

    public void setTotal_expiration_count(int total_expiration_count) {
        this.total_expiration_count = total_expiration_count;
    }

    public int getCount_play_count() {
        return count_play_count;
    }

    public void setCount_play_count(int count_play_count) {
        this.count_play_count = count_play_count;
    }

    public int getDisk_index() {
        return disk_index;
    }

    public void setDisk_index(int disk_index) {
        this.disk_index = disk_index;
    }

    public int getDisk_type() {
        return disk_type;
    }

    public void setDisk_type(int disk_type) {
        this.disk_type = disk_type;
    }

    public int getLms_percent() {
        return lms_percent;
    }

    public void setLms_percent(int lms_percent) {
        this.lms_percent = lms_percent;
    }

    public String getThumbnail_path() {
        return thumbnail_path;
    }

    public void setThumbnail_path(String thumbnail_path) {
        this.thumbnail_path = thumbnail_path;
    }

    public String getMedia_content_key() {
        return media_content_key;
    }

    public void setMedia_content_key(String media_content_key) {
        this.media_content_key = media_content_key;
    }

    public String getScreenshot_path() {
        return screenshot_path;
    }

    public void setScreenshot_path(String screenshot_path) {
        this.screenshot_path = screenshot_path;
    }

    public Bitmap getThumbnail_bitmap() {
        return thumbnail_bitmap;
    }

    public void setThumbnail_bitmap(Bitmap thumbnail_bitmap) {
        this.thumbnail_bitmap = thumbnail_bitmap;
    }
}
