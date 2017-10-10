package com.example.jock.jeim_main.Task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jock.jeim_main.Url;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class FictionTask extends AsyncTask<String,Void,String> {
    String sendMSG,result;

    @Override
    protected String doInBackground(String... params) {

        String username = params[0];
        String content = params[1];

        try {
            URL url = new URL(Url.Main+Url.Fictionadd);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.connect();
            OutputStream outputStream = con.getOutputStream();

            sendMSG = "username="+username+"&content="+content;

            outputStream.write(sendMSG.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            if(con.getResponseCode() == con.HTTP_OK){

                InputStreamReader tmp = new InputStreamReader(con.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder stringBuilder = new StringBuilder();

                while ( (result = reader.readLine()) != null){
                    stringBuilder.append(result);
                }

                result = null;
                result = stringBuilder.toString();
                Log.i("리턴값",result);
                reader.close();
            }
        }catch (Exception e){

        }
        return result;
    }
}
