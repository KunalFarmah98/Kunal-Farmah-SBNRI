package com.apps.kunalfarmah.kunalfarmahsbnri;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apps.kunalfarmah.kunalfarmahsbnri.Models.License;
import com.apps.kunalfarmah.kunalfarmahsbnri.Models.Permissions;
import com.apps.kunalfarmah.kunalfarmahsbnri.Models.Repo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    final String BASE_URL = "https://api.github.com/orgs/octokit";

    public  WebServiceRepository(Application application){
        this.application = application;
    }
    private static OkHttpClient providesOkHttpClientBuilder(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }


    List<Repo> webserviceResponseList = new ArrayList<>();

    public LiveData<List<Repo>> providesWebService() {

        final MutableLiveData<List<Repo>> data = new MutableLiveData<>();

        String response = "";
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build();

            //Defining retrofit api service
            APIService service = retrofit.create(APIService.class);
            //  response = service.makeRequest().execute().body();
            service.makeRequest().enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("Repository","Response::::"+response.body());
                    webserviceResponseList = parseJson(response.body());
                    RepoRepository postRoomDBRepository = new RepoRepository(application);
                    postRoomDBRepository.insertPosts(webserviceResponseList);
                    data.setValue(webserviceResponseList);

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("Repository","Failed:::");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        //  return retrofit.create(Repo.class);
        return  data;

    }


    private List<Repo> parseJson(String response) {

        List<Repo> apiResults = new ArrayList<>();

        JSONObject jsonObject;

        JSONArray jsonArray;

        try {
            jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                Repo repo = new Repo();
                String name,description;
                try {
                    name = object.getString("name");
                            description = object.getString("description");
                }
                catch (Exception e){
                    name = description = "Not Available";
                }


                repo.setName(name);
                repo.setDescription(description);

                int opencnt;
                try {
                    opencnt = object.getInt("open_issues");
                }
                catch (Exception e){
                    opencnt = 0;
                }

                repo.setOpenIssues(opencnt);
                License license = new License();
                JSONObject license_ = object.getJSONObject("licence");
                String key,lname,lnid,lspdx,url;
                try {
                    key = license_.getString("key");
                            lname = license_.getString("name");
                            lnid = license_.getString("node_id");
                            lspdx = license_.getString("MIT");
                            url = license_.getString("url");
                }
                catch (Exception e){
                    key = lname = lnid = lspdx = url = "Not Avaialble";
                }

                license.setKey(key);
                license.setName(lname);
                license.setNodeId(lnid);
                license.setSpdxId(lspdx);
                license.setUrl(url);
                repo.setLicense(license);

                JSONObject permissions_ = object.getJSONObject("permissions");
                Permissions permissions = new Permissions();
                String admin,push,pull;

                try {
                    admin = permissions_.getString("admin");
                    push = permissions_.getString("push");
                    pull = permissions_.getString("pull");

                }
                catch (Exception e){
                    admin=pull=push="Not Defined";
                }

                permissions.setAdmin(admin.equals("true"));
                permissions.setPush(push.equals("true"));
                permissions.setPull(pull.equals("true"));

                apiResults.add(repo);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(getClass().getSimpleName(), String.valueOf(apiResults.size()));
        return apiResults;
    }
}
