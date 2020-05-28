package com.apps.kunalfarmah.kunalfarmahsbnri.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apps.kunalfarmah.kunalfarmahsbnri.Utils.APIService;
import com.apps.kunalfarmah.kunalfarmahsbnri.Models.License;
import com.apps.kunalfarmah.kunalfarmahsbnri.Models.Permissions;
import com.apps.kunalfarmah.kunalfarmahsbnri.Models.Repo;
import com.apps.kunalfarmah.kunalfarmahsbnri.Room.RepoModel;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WebServiceRepository {
    Application application;
    final String BASE_URL = "https://api.github.com/orgs/octokit/";

    public WebServiceRepository(Application application) {
        this.application = application;
    }

    private static OkHttpClient providesOkHttpClientBuilder() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<RepoModel> webserviceResponseList = new ArrayList<>();

    public LiveData<List<RepoModel>> providesWebService(final int page_number) {

        final MutableLiveData<List<RepoModel>> data = new MutableLiveData<>();

        String response = "";
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();


            //Defining retrofit api service
            APIService service = retrofit.create(APIService.class);
            service.makeRequest(page_number, 10).enqueue(new Callback<List<Repo>>() {
                @Override
                public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
//                    Log.d("url", String.valueOf(call.request().url()));
//                    Log.d("Repository", "Response::::" + response.body());
                    webserviceResponseList = parseJson(response.body());
//                    Log.d("webServiceResponseList", String.valueOf(webserviceResponseList.size()));
                    RepoRepository postRoomDBRepository = new RepoRepository(application);
                    postRoomDBRepository.insertPosts(webserviceResponseList);
                    data.setValue(webserviceResponseList);

                }

                @Override
                public void onFailure(Call<List<Repo>> call, Throwable t) {
//                    Log.d("url", String.valueOf(call.request().url()));
//                    Log.d("Repository", "Failed:::");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;

    }

    // getting data ready for saving in database
    private List<RepoModel> parseJson(List<Repo> response) {

        List<RepoModel> apiResults = new ArrayList<>();


        for (int i = 0; i < response.size(); i++) {
            Repo object = response.get(i);
            RepoModel repo = new RepoModel();
            repo.setId(object.getNodeId());
            String name, description;
            try {
                name = object.getName();
                description = object.getDescription();
            } catch (Exception e) {
                name = description = "Not Available";
            }


            repo.setName(name);
            repo.setDescription(description);

            int opencnt;
            try {
                opencnt = object.getOpenIssues();
            } catch (Exception e) {
                opencnt = 0;
            }

            repo.setOpen_cont(opencnt);
            License license_ = object.getLicense();
            String lkey, lname, lnid, lspdx, lurl;
            try {
                lkey = license_.getKey();
                lname = license_.getName();
                lnid = license_.getNodeId();
                lspdx = license_.getSpdxId();
                lurl = license_.getUrl();
            } catch (Exception e) {
                lkey = lname = lnid = lspdx = lurl = "Not Avaialble";
            }

            repo.setLkey(lkey);
            repo.setLname(lname);
            repo.setLnodeId(lnid);
            repo.setLspdxId(lspdx);
            repo.setLurl(lurl);

            Permissions permissions_ = object.getPermissions();
            boolean admin, push, pull;

            try {
                admin = permissions_.isAdmin();
                push = permissions_.isPush();
                pull = permissions_.isPull();

            } catch (Exception e) {
                admin = pull = push = false;
            }

            repo.setAdmin(admin);
            repo.setPush(push);
            repo.setPull(pull);

            apiResults.add(repo);
//            Log.d("Entry" + String.valueOf(i), apiResults.get(i).getName() + apiResults.get(i).getPush()
//                    + apiResults.get(i).getPull() + apiResults.get(i).getAdmin());
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;
    }
}
