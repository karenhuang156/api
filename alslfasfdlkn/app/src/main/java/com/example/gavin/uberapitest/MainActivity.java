package com.example.gavin.uberapitest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RelativeLayout searchLayout;
    private LinearLayoutManager linearLayoutManager;
    private CoordinatorLayout coordinatorLayout;
    private RepoAdapter repoAdapter;
    private EditText input;
    private Button button;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchLayout = (RelativeLayout)findViewById(R.id.rl_search);
        recyclerView = (RecyclerView)findViewById(R.id.rv_repolist);
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.cl_results);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        input = (EditText)findViewById(R.id.et_user);
        button = (Button)findViewById(R.id.b_go);
        spinner = (ProgressBar)findViewById(R.id.pb_spin);

        spinner.setVisibility(View.GONE);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OAuthActivity.class);
                startActivity(intent);
                logButton.setVisibility(View.GONE);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setAdapter(null);
                spinner.setVisibility(View.VISIBLE);
                String in = input.getText().toString();
                new asdfAsyncTask().execute(in);
            }
        });
        coordinatorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("clicked recycle");
                searchLayout.animate()
                        .translationY(-searchLayout.getHeight());
            }
        });
        input.setOnEditorActionListener(new EditText.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEND) {
                    button.performClick();
                    System.out.println("u sumbitted");
                    return true;
                }
                System.out.println("action");
                return false;
            }

        });
    }

    class asdfAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String user = strings[0];
            System.out.println(user);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .build();
            GitHubService service = retrofit.create(GitHubService.class);
            PostRepo postRepo = retrofit.create(PostRepo.class);
            //GetAuth getAuth = retrofit.create(GetAuth.class);

<<<<<<< HEAD
            String json = "{ \"name\": \"testoldurl\", \"auto_init\": true, \"private\": false, \"gitignore_template\": \"nanoc\"}";
            //String au = "{ \"scopes\": [\"public_repo\"], \"note\": \"admin-script\" }";
=======
            String json = "{ \"name\": \"NEWREPO\", \"auto_init\": true, \"private\": false, \"gitignore_template\": \"nanoc\"}";
            String au = "{ \"scopes\": [\"public_repo\"], \"note\": \"admin-script\" }";
>>>>>>> githubworks-130916

            RequestBody rb = RequestBody.create(MediaType.parse("application/json"), json);
            System.out.println(AuthPrefs.ACCESS_TOKEN);
            /*RequestBody rb2 = RequestBody.create(MediaType.parse("application/json"), au);
            Call<ResponseBody> getAuthCall = getAuth.getAuth(rb2, "token 3807d30b297c20dd78eaa56e065f6b2752d0c7cb");
            try {
                Response rrr = getAuthCall.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            */
            Call<ResponseBody> createRepoCall = postRepo.createRepo(rb, "token "+AuthPrefs.ACCESS_TOKEN);

            Call<ResponseBody> getProductsCall = service.getRepos(user, "token "+AuthPrefs.ACCESS_TOKEN);
            ResponseBody responseBody = null;
            String response = "";

            try {

                Request req = createRepoCall.request();
                RequestBody reqb = req.body();
                System.out.println(req.headers().toString());
                System.out.println("created repo using token "+AuthPrefs.ACCESS_TOKEN);
                Response aaa = createRepoCall.execute();

                //System.out.println(aaa.errorBody().string());
                //ResponseBody rb = (ResponseBody)aaa.body();

                //System.out.println(rb.string());

                System.out.println("should print response next");
                System.out.println();
                Response asdf = getProductsCall.execute();
                responseBody = (ResponseBody) asdf.body();
                if (responseBody == null){
                    System.out.println("responsebody is null");
                    //recyclerView.setAdapter(null);
                }
                else {
                    response = responseBody.string();

                    // System.out.println(response);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("second print " + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.isEmpty()) {
                recyclerView.setAdapter(null);
                Context context = getApplicationContext();
                String text = "User does not exist";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                System.out.println("json obj null found");
            }
            else{
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
                    if(repos.length == 0){
                        Context context = getApplicationContext();
                        String text = "User has no repositories";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        System.out.println("user has no repos");
                    }
                    for (Repo r : repos) {
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
            spinner.setVisibility(View.GONE);

        }
    }

    public interface GitHubService {
        @GET("users/{user}/repos")
        Call<ResponseBody> getRepos(@Path(value="user", encoded=true) String user,
                                    @Header("Authorization") String auth);
    }

    public interface PostRepo {
        //@Headers("Content-Type: application/json")
        @POST("user/repos")
        Call<ResponseBody> createRepo(@Body RequestBody body,
                                      @Header("Authorization") String auth);
    }

   /* public interface GetAuth {
        @POST("authorizations")
        Call<ResponseBody> getAuth(@Body RequestBody body,
                                   @Header("Authorization") String auth);
    }*/


}
