package com.apps.kunalfarmah.kunalfarmahsbnri.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {RepoModel.class}, version = 1)
public abstract class RepoRoomDataBase extends RoomDatabase {

    private static RepoRoomDataBase INSTANCE;

    public abstract RepoDao RepoDao();

    public static synchronized RepoRoomDataBase getInstance(Context mContext) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(mContext.getApplicationContext(),
                    RepoRoomDataBase.class, "RepoRoomDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
