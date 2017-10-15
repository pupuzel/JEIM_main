package com.example.jock.jeim_main.Task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.jock.jeim_main.Food.FoodActivity;
import com.example.jock.jeim_main.Url;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class FoodTask extends AsyncTask<String,Void,String> {
    private String result,sendMSG;
    private Context context;
    private ProgressDialog mProgress;
    public FoodTask(Context context) {
        mProgress = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setMessage("로딩중입니다.");
        mProgress.setCancelable(false);
        mProgress.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            for (int i = 0; i < 5; i++) {
                //asyncDialog.setProgress(i * 30);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mProgress.dismiss();
    }
}
