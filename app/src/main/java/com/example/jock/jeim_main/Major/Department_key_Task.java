package com.example.jock.jeim_main.Major;

import android.os.AsyncTask;

import com.example.jock.jeim_main.Another.Url;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Department_key_Task extends AsyncTask<String,Void,String> {

    private String key_have,key_apply,key_num,sendMSG,result;

    @Override
    protected String doInBackground(String... params) {

        try{
            key_have = params[0];
            key_apply = params[1];
            key_num = params[2];
            URL url = new URL(Url.Main+Url.DepartmentKey);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.connect();
            OutputStream outputStream = con.getOutputStream();

            sendMSG = "key_have="+key_have+"&key_apply="+key_apply+"&key_num="+key_num;

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
}
