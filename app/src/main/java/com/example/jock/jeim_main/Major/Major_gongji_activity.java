package com.example.jock.jeim_main.Major;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.jock.jeim_main.Another.Url;
import com.example.jock.jeim_main.R;


public class Major_gongji_activity extends AppCompatActivity {

    WebView webView;
    String gongji = "https://dep.jeiu.ac.kr/CI/community/list.asp?BoardID=00088";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.major_gongji_main);

        webView = (WebView) findViewById(R.id.major_gongji_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webView.setInitialScale(1);
        webView.loadUrl(gongji);
    }
}
