package com.example.gavin.uberapitest;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class OAuthActivity extends AppCompatActivity {

    public static String OAUTH_URL = "https://github.com/login/oauth/authorize";
    public static String OAUTH_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token/";

    public static String CLIENT_ID = "c318b3ca6bb70166e877";
    public static String CLIENT_SECRET = "f54ac3c76cc77e5a5cd7b1e6a1e9b6f7ade301b8";
    public static String CALLBACK_URL = "http://127.0.0.1/";
    public static String CODE = "";
    public static final String URL = OAUTH_URL + "?client_id=" + CLIENT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth);

        WebView webView = (WebView)findViewById(R.id.wv);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(URL);
                return true;
            }
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                String accessTokenFragment = "access_token=";
                String accessCodeFragment = "code=";

                // We hijack the GET request to extract the OAuth parameters

                if (url.contains(accessTokenFragment)) {
                    // the GET request contains directly the token
                    String accessToken = url.substring(url.indexOf(accessTokenFragment));
                    AuthPrefs.ACCESS_TOKEN = accessToken;
                    System.out.println(accessToken);


                } else if(url.contains(accessCodeFragment)) {
                    // the GET request contains an authorization code
                    String accessCode = url.substring(url.indexOf(accessCodeFragment));
                    AuthPrefs.CODE = accessCode.substring(5);
                    CODE = AuthPrefs.CODE;
                    System.out.println(accessCode);
                    new getCodeTask().execute();

/*
                    String query = "client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&code=" + accessCode;
                    view.postUrl(OAUTH_ACCESS_TOKEN_URL, query.getBytes());
*/
                }

            }




        });
        webView.loadUrl(URL);
    }

    class getCodeTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://github.com/")
                    .build();
            PostCode postCodeService = retrofit.create(PostCode.class);
           // String request = "{ \"code\":"+AuthPrefs.CODE+"\", \"client_id\":\""+CLIENT_ID+"\", \"client_secret\":\""+CLIENT_SECRET+"\" }";
            //RequestBody rb = RequestBody.create(MediaType.parse("application/json"), request);
            Map<String, String> data = new HashMap<>();
            data.put("code",CODE);
            data.put("client_secret",CLIENT_SECRET);
            data.put("client_id",CLIENT_ID);

            System.out.println(data.toString());
            Call<ResponseBody> postCodeCall = postCodeService.getToken(data);
            String response = "";
            try {
                ResponseBody a = null;
                Response s = postCodeCall.execute();
                a = (ResponseBody)s.body();
                response = a.string();
                System.out.println(response);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }
    }
    public interface PostCode {
        @POST("login/oauth/access_token")
        Call<ResponseBody> getToken(@QueryMap(encoded=true) Map<String,String> params);

    }

}