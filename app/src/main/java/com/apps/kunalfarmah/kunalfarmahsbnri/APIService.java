package com.apps.kunalfarmah.kunalfarmahsbnri;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("/repos?page=1&per_page=10")
    Call<String> makeRequest();
}
