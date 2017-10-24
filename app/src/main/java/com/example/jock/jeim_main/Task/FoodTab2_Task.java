package com.example.jock.jeim_main.Task;

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


public class FoodTab2_Task extends AsyncTask<String,Void,String> {

    private String result,sendMSG;
    private Context context;
    private String code,delivery,ordervalue;
    private ProgressDialog mProgress;

    public FoodTab2_Task(Context context) {
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
            code = params[0];

            URL url = new URL(Url.Main+Url.FoodTab2);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.connect();
            OutputStream outputStream = con.getOutputStream();

            sendMSG = "code="+code;

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
        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mProgress.dismiss();
    }


}
