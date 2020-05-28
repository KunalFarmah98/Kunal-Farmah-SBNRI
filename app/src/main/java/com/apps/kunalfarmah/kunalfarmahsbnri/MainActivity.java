package com.apps.kunalfarmah.kunalfarmahsbnri;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.apps.kunalfarmah.kunalfarmahsbnri.Pagination.PaginationScrollListener;
import com.apps.kunalfarmah.kunalfarmahsbnri.Pagination.RepoAdapter;
import com.apps.kunalfarmah.kunalfarmahsbnri.Room.RepoModel;
import com.apps.kunalfarmah.kunalfarmahsbnri.ViewModels.RepoViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LOG";
    RecyclerView rv;
    RepoViewModel repoViewModel;
    ProgressDialog progressDialog;
    RepoAdapter mAdapter;
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;
    List<RepoModel> repos;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 5;
    public static int currentPage = PAGE_START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.mainRecycler);
        progressBar = findViewById(R.id.loading);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        repos = new ArrayList<>();
        mAdapter = new RepoAdapter(this);
        rv.setAdapter(mAdapter);
        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }


        });

        repoViewModel = ViewModelProviders.of(this).get(RepoViewModel.class);
        progressDialog = ProgressDialog.show(this, "Loading...", "Please wait...", true);
        repoViewModel.getRepos().observe(this, new Observer<List<RepoModel>>() {
            @Override
            public void onChanged(@Nullable List<RepoModel> resultModels) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
//                Log.d("onDataChanged","entered");
                repos = resultModels;
                mAdapter.addAll(resultModels);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFirstPage();
            }
        }, 1000);
    }


    private void loadFirstPage() {
//        Log.d(TAG, "loadFirstPage: ");
        progressBar.setVisibility(View.GONE);
        if (currentPage <= TOTAL_PAGES) mAdapter.addLoadingFooter();
        else isLastPage = true;

    }

    private void loadNextPage() {
//        Log.d(TAG, "loadNextPage: " + currentPage);
        mAdapter.removeLoadingFooter();
        isLoading = false;
        repoViewModel.fetchNext(currentPage);
        if (currentPage != TOTAL_PAGES) mAdapter.addLoadingFooter();
        else isLastPage = true;
    }
}
