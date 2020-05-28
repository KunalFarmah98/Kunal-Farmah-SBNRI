package com.apps.kunalfarmah.kunalfarmahsbnri;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RepoViewModelFactory implements ViewModelProvider.Factory {
    Application mApplication;

    public RepoViewModelFactory(Application mApplication) {
        this.mApplication = mApplication;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RepoViewModel(mApplication);
    }
}
