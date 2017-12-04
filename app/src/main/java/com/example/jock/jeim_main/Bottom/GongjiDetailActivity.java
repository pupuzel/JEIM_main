package com.example.jock.jeim_main.Bottom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.jock.jeim_main.Another.Url;
import com.example.jock.jeim_main.R;



public class GongjiDetailActivity extends AppCompatActivity {
    WebView webView;
    Intent intent;
    String code;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_gongji_detail);

        intent = getIntent();
        code = intent.getStringExtra("코드");

        webView = (WebView) findViewById(R.id.bottom_gongji_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webView.setInitialScale(1);
        webView.loadUrl(Url.gongji+code);

    }
}
