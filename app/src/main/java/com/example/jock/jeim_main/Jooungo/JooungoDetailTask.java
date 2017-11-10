package com.example.jock.jeim_main.Jooungo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jock.jeim_main.Another.Url;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class JooungoDetailTask extends AsyncTask<String,Void,String> {
    private String result,sendMsg;
    private ProgressDialog mProgress;

    public JooungoDetailTask(ProgressDialog mProgress){
        this.mProgress = mProgress;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgress.setCancelable(false);
        mProgress.setMessage("잠시만 기다려주세요 로딩중입니다.");
        mProgress.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            URL url = new URL(Url.Main+Url.JooungoDetail);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //해당 url에 접속할수 있도록
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
            OutputStreamWriter osw = new OutputStreamWriter(httpURLConnection.getOutputStream());
            sendMsg = "used_bdnum="+strings[0];

            osw.write(sendMsg);//OutputStreamWriter에 담아 전송
            osw.flush();

            //jsp와 통신이 정상적으로 되었을 때 할 코드들입니다.
            if(httpURLConnection.getResponseCode() == httpURLConnection.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                String str;
                //jsp에 리턴값
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                result = buffer.toString();

            } else {
                Log.i("통신 결과", httpURLConnection.getResponseCode()+"에러");
                // 통신이 실패했을 때 실패한 이유를 알기 위한 로그
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    } // doInBackground finish
}
