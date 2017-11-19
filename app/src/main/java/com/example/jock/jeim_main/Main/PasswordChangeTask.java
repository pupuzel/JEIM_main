package com.example.jock.jeim_main.Main;

import android.os.AsyncTask;

import com.example.jock.jeim_main.Another.Url;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jock on 2017-10-20.
 */

public class PasswordChangeTask extends AsyncTask<String,Void,String> {
    String sendMSG;
    String result;

    @Override
    protected String doInBackground(String... params) {

        String userid = params[0];
        String pass = params[1];

        try {
            URL url = new URL(Url.Passwordchange);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.connect();
            OutputStream outputStream = con.getOutputStream();

            sendMSG ="num="+userid+"&password="+pass;
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
                reader.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
