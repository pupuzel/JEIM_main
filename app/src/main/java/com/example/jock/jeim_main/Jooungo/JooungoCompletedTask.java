package com.example.jock.jeim_main.Jooungo;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jock.jeim_main.Another.Url;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class JooungoCompletedTask extends AsyncTask<String,Void,String>{

    private String result,sendMsg;

    @Override
    protected String doInBackground(String... params) {

        try{
            URL url = new URL(Url.Main+Url.JooungoCompleted);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.connect();
            OutputStream osw = con.getOutputStream();
            sendMsg = "boardcode="+params[0];

            osw.write(sendMsg.getBytes("UTF-8"));//OutputStreamWriter에 담아 전송
            osw.flush();

            //jsp와 통신이 정상적으로 되었을 때 할 코드들입니다.
            if(con.getResponseCode() == con.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(con.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();

                String str;
                //jsp에 리턴값
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                result = buffer.toString();

            } else {
                Log.i("통신 결과", con.getResponseCode()+"에러");
                // 통신이 실패했을 때 실패한 이유를 알기 위한 로그
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
