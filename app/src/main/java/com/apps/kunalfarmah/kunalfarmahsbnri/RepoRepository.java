package com.apps.kunalfarmah.kunalfarmahsbnri;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.ImageButton;

import androidx.lifecycle.LiveData;

import com.apps.kunalfarmah.kunalfarmahsbnri.Models.Repo;
import com.apps.kunalfarmah.kunalfarmahsbnri.R;
import com.apps.kunalfarmah.kunalfarmahsbnri.RepoDao;
import com.apps.kunalfarmah.kunalfarmahsbnri.RepoRoomDataBase;

import java.util.List;

public class RepoRepository {
    private RepoDao repoDao;
    private LiveData<List<Repo>> repos;

    public RepoRepository(Application application){
        RepoRoomDataBase db = RepoRoomDataBase.getInstance(application);
        repoDao = db.RepoDao();
        repos = repoDao.getAllRepos();
    }

    public void insert(Repo repo){
        new InsertRepoAsyncTask(repoDao).execute(repo);
    }

    public LiveData<List<Repo>> getRepos(){
        return repos;
    }

    public static class InsertRepoAsyncTask extends AsyncTask<Repo,Void,Void>{
        private RepoDao repoDao;

        private InsertRepoAsyncTask(RepoDao repoDao){
            this.repoDao=repoDao;
        }
        @Override
        protected Void doInBackground(Repo... repos) {
            repoDao.insert(repos[0]);
            return null;
        }
    }

    public void insertPosts (List<Repo> repolist) {
        new insertAsyncTask(repoDao).execute(repolist);
    }

    private static class insertAsyncTask extends AsyncTask<List<Repo>, Void, Void> {

        private RepoDao mAsyncTaskDao;

        insertAsyncTask(RepoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Repo>... params) {
            mAsyncTaskDao.insertRepos(params[0]);
            return null;
        }
    }
}
