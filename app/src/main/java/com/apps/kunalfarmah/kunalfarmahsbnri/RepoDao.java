package com.apps.kunalfarmah.kunalfarmahsbnri;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.apps.kunalfarmah.kunalfarmahsbnri.Models.Repo;
import com.apps.kunalfarmah.kunalfarmahsbnri.Models.RepoModel;

import java.util.List;

@Dao
public interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RepoModel repo);

    @Query("SELECT * from Repo")
    LiveData<List<RepoModel>> getAllRepos();

    @Query("DELETE FROM Repo")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepos(List<RepoModel> repo);

}
