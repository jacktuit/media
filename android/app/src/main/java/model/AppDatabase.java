package model;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import dao.KollusContentDao;
import entity.KollusContentEntity;
import testK.DateConverter;

/**
 * Created by etlim on 07/13/20.
 */

@Database(entities = {KollusContentEntity.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract KollusContentDao kollusDao();

    private static AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "kollus_database")
                            .build();

//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            AppDatabase.class, "kollus_database")
//                            .fallbackToDestructiveMigration()
//                            .fallbackToDestructiveMigrationOnDowngrade()
//                            .allowMainThreadQueries()
//                            .build();
                }
            }
        }
        return INSTANCE;
    }
}