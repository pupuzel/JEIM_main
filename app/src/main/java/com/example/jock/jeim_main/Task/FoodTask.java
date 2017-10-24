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
import java.net.URLEncoder;


public class FoodTask extends AsyncTask<String,Void,String> {
    private String result,sendMSG;
    private Context context;
    private String type,delivery,ordervalue;
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

        try{
            type = URLEncoder.encode(params[0], "UTF-8");
            delivery = URLEncoder.encode(params[1], "UTF-8");
            ordervalue = URLEncoder.encode(params[2], "UTF-8");

            if(params[0].equals("All")){
                type = URLEncoder.encode("%25", "UTF-8");
            }if(params[1].equals("All")){
                delivery = URLEncoder.encode("%25", "UTF-8");
            }if(params[2].equals("All")){
                ordervalue = URLEncoder.encode("%25", "UTF-8");
            }

            URL url = new URL(Url.Main+Url.Food);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.connect();
            OutputStream outputStream = con.getOutputStream();

            sendMSG = "type="+type+"&delivery="+delivery+"&ordervalue="+ordervalue;

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
