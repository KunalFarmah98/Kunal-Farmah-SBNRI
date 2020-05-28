package com.apps.kunalfarmah.kunalfarmahsbnri.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.apps.kunalfarmah.kunalfarmahsbnri.Room.RepoModel;
import com.apps.kunalfarmah.kunalfarmahsbnri.Room.RepoDao;
import com.apps.kunalfarmah.kunalfarmahsbnri.Room.RepoRoomDataBase;

import java.util.List;

public class RepoRepository {
    private RepoDao repoDao;
    private LiveData<List<RepoModel>> repos;

    public RepoRepository(Application application) {
        RepoRoomDataBase db = RepoRoomDataBase.getInstance(application);
        repoDao = db.RepoDao();
        repos = repoDao.getAllRepos();
    }

    public void insert(RepoModel repo) {
        new InsertRepoAsyncTask(repoDao).execute(repo);
    }

    public LiveData<List<RepoModel>> getRepos() {
        return repos;
    }

    public static class InsertRepoAsyncTask extends AsyncTask<RepoModel, Void, Void> {
        private RepoDao repoDao;

        private InsertRepoAsyncTask(RepoDao repoDao) {
            this.repoDao = repoDao;
        }

        @Override
        protected Void doInBackground(RepoModel... repos) {
            repoDao.insert(repos[0]);
            return null;
        }
    }

    public void insertPosts(List<RepoModel> repolist) {
        new insertAsyncTask(repoDao).execute(repolist);
    }

    private static class insertAsyncTask extends AsyncTask<List<RepoModel>, Void, Void> {

        private RepoDao mAsyncTaskDao;

        insertAsyncTask(RepoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<RepoModel>... params) {
            mAsyncTaskDao.insertRepos(params[0]);
            return null;
        }
    }

    public void deleteAll() {
        new deleteAllAsyncTask(repoDao).execute();

    }

    private static class deleteAllAsyncTask extends AsyncTask<List<RepoModel>, Void, Void> {

        private RepoDao mAsyncTaskDao;

        deleteAllAsyncTask(RepoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<RepoModel>... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
