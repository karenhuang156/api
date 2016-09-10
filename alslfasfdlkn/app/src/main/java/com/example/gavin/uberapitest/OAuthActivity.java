package com.example.gavin.uberapitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OAuthActivity extends AppCompatActivity {

    public static String OAUTH_URL = "https://github.com/login/oauth/authorize";
    public static String OAUTH_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";

    public static String CLIENT_ID = "c318b3ca6bb70166e877";
    public static String CLIENT_SECRET = "0346b65fd23746ec557b1a02c81723664c67b13c";
    public static String CALLBACK_URL = "http://localhost";
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
        });
        //webView.loadUrl();
    }
}
