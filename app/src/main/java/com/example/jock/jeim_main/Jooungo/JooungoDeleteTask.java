package com.example.jock.jeim_main.Jooungo;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jock.jeim_main.Another.Url;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jock on 2017-08-22.
 */

public class JooungoDeleteTask extends AsyncTask<JSONObject,Void,String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(JSONObject... params) {
        String sendMsg,result = null;
        String resulttext = null;
        String boardCode;
        String img1,img2,img3;
        int size = params[0].length();
        try {
            boardCode = params[0].getString("게시판코드");
            sendMsg = "boardcode="+boardCode;
            if(size > 1){
                img1 = params[0].getString("이미지1");
                sendMsg = sendMsg+"&img1="+img1;
                if(size > 2){
                    img2 = params[0].getString("이미지2");
                    sendMsg = sendMsg+"&img2="+img2;
                    if(size > 3){
                        img3 = params[0].getString("이미지3");
                        sendMsg = sendMsg+"&img3="+img3;
                    }
                }
            }

            URL url = new URL(Url.Main+Url.JooungoDelete);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if (conn != null) {
                conn.setUseCaches(false);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
            }else{
                Log.i("연결안됨 - "," URL을 다시 확인해주세요.");
            }

            OutputStream stream = conn.getOutputStream();
            stream.write(sendMsg.getBytes("UTF-8"));
            stream.flush();
            stream.close();

            if(conn.getResponseCode() == conn.HTTP_OK) {  // 리턴값 받기

                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((resulttext= reader.readLine()) != null) {
                    buffer.append(resulttext);
                }
                result = buffer.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("오류남","오류");
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }


}
