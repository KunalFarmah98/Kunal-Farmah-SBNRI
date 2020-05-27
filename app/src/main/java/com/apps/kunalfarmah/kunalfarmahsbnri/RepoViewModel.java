package com.apps.kunalfarmah.kunalfarmahsbnri;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.apps.kunalfarmah.kunalfarmahsbnri.Models.Repo;
import com.apps.kunalfarmah.kunalfarmahsbnri.Models.RepoModel;

import java.util.List;

public class RepoViewModel extends AndroidViewModel {

    private RepoRepository postRoomDBRepository;
    private LiveData<List<RepoModel>> mAllRepos;
    WebServiceRepository webServiceRepository ;
    private final LiveData<List<RepoModel>>  retroObservable;

    public RepoViewModel(Application application){
        super(application);
        postRoomDBRepository = new RepoRepository(application);
        webServiceRepository = new WebServiceRepository(application);
        retroObservable = webServiceRepository.providesWebService();
        //postRoomDBRepository.insertPosts(retroObservable.getValue());
        mAllRepos = postRoomDBRepository.getRepos();
    }

    public LiveData<List<RepoModel>> getRepos() {
        return mAllRepos;
    }
}
