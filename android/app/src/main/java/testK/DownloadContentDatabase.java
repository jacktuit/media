package testK;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


import dao.KollusDownloadContentDao;
import entity.KollusDownloadContentEntity;

@Database(entities = {KollusDownloadContentEntity.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class DownloadContentDatabase extends RoomDatabase {

    public abstract KollusDownloadContentDao kollusDownloadingDao();

    private static DownloadContentDatabase INSTANCE;

    static DownloadContentDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DownloadContentDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DownloadContentDatabase.class, "downloading_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}