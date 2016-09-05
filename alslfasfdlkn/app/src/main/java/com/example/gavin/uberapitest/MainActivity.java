package com.example.gavin.uberapitest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RepoAdapter repoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.rv_repolist);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        asdfAsyncTask asdf = new asdfAsyncTask();
        asdf.execute();
    }

    class asdfAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .build();
            UberService service = retrofit.create(UberService.class);

            Call<ResponseBody> getProductsCall = service.getProducts();
            ResponseBody responseBody = null;
            String response = "";

            try {

                System.out.println("should print response next");
                System.out.println();
                Response asdf = getProductsCall.execute();
                responseBody = (ResponseBody)asdf.body();
                response = responseBody.string();
                System.out.println(response);

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("second print " + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                System.out.println("next is response");
                System.out.println("here is resopnse: " + s);
                JSONArray jsonArray = new JSONArray(s);
                JSONObject jsonObject;
                String name;
                GsonBuilder gsonBuilder = new GsonBuilder();
                //gsonBuilder.registerTypeAdapter(Owner.class, new OwnerDeserializer());
                Gson gson = gsonBuilder.create();
                Repo[] repos = gson.fromJson(s, Repo[].class);
                ArrayList<Repo> repoArrayList = new ArrayList<>();

                for(Repo r : repos) {
                    repoArrayList.add(r);
                    System.out.println("name: " + r.getName());
                    System.out.println("a " + r.getOwner().toString());
                    System.out.println("owner: " + r.getOwner().getLogin());
                }

                repoAdapter = new RepoAdapter(repoArrayList);
                recyclerView.setAdapter(repoAdapter);


                /*for(int i=0; i<jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    Repo repo = gson.fromJson(jsonObject.toString(), Repo.class);
                }*/
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
    public interface UberService {
        @GET("/users/amisrs/repos")
        Call<ResponseBody> getProducts();
    }


}
