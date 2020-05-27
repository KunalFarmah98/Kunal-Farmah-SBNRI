package com.apps.kunalfarmah.kunalfarmahsbnri;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.apps.kunalfarmah.kunalfarmahsbnri.Models.Repo;

import java.util.List;

@Dao
public interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Repo repo);

    @Query("SELECT name,description,license,permissions,openIssues from Repo ORDER BY name ASC")
    LiveData<List<Repo>> getAllRepos();

    @Query("DELETE FROM Repo")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepos(List<Repo> repo);

}
