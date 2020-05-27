package com.apps.kunalfarmah.kunalfarmahsbnri;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.apps.kunalfarmah.kunalfarmahsbnri.Models.Repo;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LOG" ;
    RecyclerView rv;
    RepoViewModel repoViewModel;
    ProgressDialog progressDialog;
    RepoAdapter mAdapter;
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;
    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 3;
    private int currentPage = PAGE_START;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.mainRecycler);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(mAdapter);
        rv.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
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

        loadFirstPage();



        repoViewModel = ViewModelProviders.of(this).get(RepoViewModel.class);
        progressDialog= ProgressDialog.show(this, "Loading...", "Please wait...", true);
        repoViewModel.getRepos().observe(this, new Observer<List<Repo>>() {
            @Override
            public void onChanged(@Nullable List<Repo> resultModels) {
                if(progressDialog!=null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                mAdapter = new RepoAdapter(getApplicationContext(),resultModels);
            }
        });
    }

    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");
        List<Repo> repos = Repo.createRepos(mAdapter.getItemCount());
        progressBar.setVisibility(View.GONE);
        mAdapter.addAll(repos);

        if (currentPage <= TOTAL_PAGES) mAdapter.addLoadingFooter();
        else isLastPage = true;

    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);
        List<Repo> repos = Repo.createRepos(mAdapter.getItemCount());

        mAdapter.removeLoadingFooter();
        isLoading = false;

        mAdapter.addAll(repos);

        if (currentPage != TOTAL_PAGES) mAdapter.addLoadingFooter();
        else isLastPage = true;
    }
}
