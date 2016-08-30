package com.example.gavin.uberapitest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new asdfAsyncTask().execute();

    }
    class asdfAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .build();
            UberService service = retrofit.create(UberService.class);

            Call<ResponseBody> getProductsCall = service.getProducts();
            try {

                System.out.println("should print response next");
                System.out.println();
                Response asdf = getProductsCall.execute();
                ResponseBody responseBody = (ResponseBody)asdf.body();
                System.out.println(responseBody.string());

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
    public interface UberService {
        @GET("/orgs/octokit/repos")
        Call<ResponseBody> getProducts();
    }


}
