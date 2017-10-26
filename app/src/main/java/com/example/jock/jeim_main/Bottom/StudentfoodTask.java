package com.example.jock.jeim_main.Bottom;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.jock.jeim_main.Another.Url;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class StudentfoodTask extends AsyncTask<String,Void,String> {
    private String result,sendMSG;
    private String firstdate,lastdate;
    private ProgressDialog mProgress;

    public StudentfoodTask(Context context) {
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

        try{
            firstdate = params[0];
            lastdate  = params[1];

            URL url = new URL(Url.Main+Url.StudentFood);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.connect();
            OutputStream outputStream = con.getOutputStream();

            sendMSG = "firstdate="+firstdate+"&lastdate="+lastdate;

            outputStream.write(sendMSG.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            if(con.getResponseCode() == con.HTTP_OK){

                InputStream inputStream = con.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder = new StringBuilder();
                while ((result = bufferedReader.readLine()) != null){
                    stringBuilder.append(result);
                }
                result = null;
                result = stringBuilder.toString();
                bufferedReader.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mProgress.dismiss();
    }
}
