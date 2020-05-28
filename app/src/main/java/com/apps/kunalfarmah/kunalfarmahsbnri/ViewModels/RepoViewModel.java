package com.apps.kunalfarmah.kunalfarmahsbnri.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.apps.kunalfarmah.kunalfarmahsbnri.Repositories.RepoRepository;
import com.apps.kunalfarmah.kunalfarmahsbnri.Repositories.WebServiceRepository;
import com.apps.kunalfarmah.kunalfarmahsbnri.Room.RepoModel;

import java.util.List;

public class RepoViewModel extends AndroidViewModel {

    private RepoRepository postRoomDBRepository;
    private LiveData<List<RepoModel>> mAllRepos;
    WebServiceRepository webServiceRepository;
    private LiveData<List<RepoModel>> retroObservable;

    public RepoViewModel(Application application) {
        super(application);
        postRoomDBRepository = new RepoRepository(application);
        // clearing database on first time creation
        postRoomDBRepository.deleteAll();
        webServiceRepository = new WebServiceRepository(application);
        retroObservable = webServiceRepository.providesWebService(1);
        mAllRepos = postRoomDBRepository.getRepos();
    }

    public LiveData<List<RepoModel>> getRepos() {
        return mAllRepos;
    }

    public void fetchNext(int page) {
        retroObservable = webServiceRepository.providesWebService(page);
        mAllRepos = postRoomDBRepository.getRepos();
    }
}
