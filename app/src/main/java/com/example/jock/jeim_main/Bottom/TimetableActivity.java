package com.example.jock.jeim_main.Bottom;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.jock.jeim_main.R;
import com.example.jock.jeim_main.Another.Url;


public class TimetableActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_timetable);

        webView = (WebView) findViewById(R.id.bottom_timetable_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webView.setInitialScale(1);
        webView.loadUrl(Url.timetable);
    }
}
