package entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "download")
public class KollusDownloadContentEntity {
    @PrimaryKey(autoGenerate = true)
    int id;

    //Kollus download Content data
    @ColumnInfo(name = "filename")
    String filename;

    @ColumnInfo(name = "thumbnail_path")
    String thumbnail_path;

    @ColumnInfo(name = "download_start", defaultValue = "0")
    long download_start;

    @ColumnInfo(name = "download_file_size", defaultValue = "0")
    long download_file_size;

    @ColumnInfo(name = "download_receiving_size", defaultValue = "0")
    long download_receiving_size;

    @ColumnInfo(name = "download_status", defaultValue = "0")
    int download_status;

    @ColumnInfo(name = "disk_index")
    int disk_index;

    @ColumnInfo(name = "disk_type")
    int disk_type;

    @ColumnInfo(name = "download_percent", defaultValue = "0")
    int download_percent;

    @ColumnInfo(name = "media_content_key")
    String media_content_key;

    @ColumnInfo(name = "folder")
    String folder;

    public KollusDownloadContentEntity(
            @NonNull String filename,
            @Nullable String thumbnail_path,
            @NonNull long download_start,
            @NonNull long download_file_size,
            @NonNull long download_receiving_size,
            @NonNull int download_status,
            @NonNull int disk_index,
            @NonNull int disk_type,
            @NonNull int download_percent,
            @NonNull String media_content_key,
            @Nullable String folder) {

        //kollus download data set
        this.filename = filename;
        this.thumbnail_path = thumbnail_path;
        this.download_start = download_start;
        this.download_file_size = download_file_size;
        this.download_receiving_size = download_receiving_size;
        this.download_status = download_status;
        this.disk_index = disk_index;
        this.disk_type = disk_type;
        this.download_percent = download_percent;
        this.media_content_key = media_content_key;
        this.folder = folder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getDownload_file_size() {
        return download_file_size;
    }

    public void setDownload_file_size(long download_file_size) {
        this.download_file_size = download_file_size;
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

    public int getDownload_percent() {
        return download_percent;
    }

    public void setDownload_percent(int download_percent) {
        this.download_percent = download_percent;
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

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public long getDownload_receiving_size() {
        return download_receiving_size;
    }

    public void setDownload_receiving_size(long download_receiving_size) {
        this.download_receiving_size = download_receiving_size;
    }

    public int getDownload_status() {
        return download_status;
    }

    public void setDownload_status(int download_status) {
        this.download_status = download_status;
    }

    public long getDownload_start() {
        return download_start;
    }

    public void setDownload_start(long download_start) {
        this.download_start = download_start;
    }
}