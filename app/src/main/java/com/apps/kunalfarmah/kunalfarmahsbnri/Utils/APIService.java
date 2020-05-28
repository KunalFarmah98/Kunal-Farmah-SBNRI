package com.apps.kunalfarmah.kunalfarmahsbnri.Utils;

import com.apps.kunalfarmah.kunalfarmahsbnri.Models.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("repos")
    Call<List<Repo> >makeRequest(@Query("page") int page, @Query("per_page") int per_page);
}
