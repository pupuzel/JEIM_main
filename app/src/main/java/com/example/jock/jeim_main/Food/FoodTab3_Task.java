package com.example.jock.jeim_main.Food;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jock.jeim_main.Another.Url;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class FoodTab3_Task extends AsyncTask<String,Void,String> {
    private String result,sendMSG;
    private Context context;
    private ProgressDialog mProgress;

    public FoodTab3_Task(Context context) {
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
            String username = params[0];
            String content = params[1];
            String pageTotal = params[2];
            String code = params[3];

            URL url = new URL(Url.Main+Url.FoodTab3);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.connect();
            OutputStream outputStream = con.getOutputStream();

            sendMSG = "username="+username+"&content="+content+"&pageTotal="+pageTotal+"&code="+code;

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
                Log.i("리졸졸",result);
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
