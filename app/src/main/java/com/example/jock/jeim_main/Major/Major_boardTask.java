package com.example.jock.jeim_main.Major;

import android.os.AsyncTask;

import com.example.jock.jeim_main.Another.Url;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Major_boardTask extends AsyncTask<String,Void,String> {
    String sendMSG,result;
    @Override
    protected String doInBackground(String... params) {

        String pageTotal = params[0];
        try {
            URL url = new URL(Url.Main+Url.Departmentlist);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.connect();
            OutputStream outputStream = con.getOutputStream();
            if(pageTotal != null){
                sendMSG = "page="+pageTotal;
            }


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
        }catch (Exception e){

        }
        return result;
    }
}
