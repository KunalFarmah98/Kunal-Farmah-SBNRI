package com.apps.kunalfarmah.kunalfarmahsbnri;

import android.content.Context;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.apps.kunalfarmah.kunalfarmahsbnri.Models.Repo;

@Database(entities = {Repo.class}, version = 1)
public abstract class RepoRoomDataBase extends RoomDatabase {

    private static RepoRoomDataBase INSTANCE;
    public abstract RepoDao RepoDao();

    public static synchronized RepoRoomDataBase getInstance(Context mContext){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(mContext.getApplicationContext(),
                    RepoRoomDataBase.class,  "RepoRoomDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
