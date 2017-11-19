package com.example.jock.jeim_main.Main;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jock.jeim_main.Another.Url;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class JoinTask extends AsyncTask<String,Void,String> {

    String sendMSG,result;

    @Override
    protected String doInBackground(String... params) {

        String user_id = params[0];
        String user_pw = params[1];
        String user_gender = params[2];
        String user_name = params[3];
        String user_phone = params[4];
        String user_email1 = params[5];
        String user_email2 = params[6];
        String user_birth = params[7];
        String user_state = params[8];
        String user_token = params[9];

        try {
            URL url = new URL(Url.Join);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.connect();
            OutputStream outputStream = con.getOutputStream();

            sendMSG = "user_id="+user_id+"&user_pw="+user_pw+"&user_gender="+user_gender+"&user_name="+user_name+"&user_phone="+
                       user_phone+"&user_email1="+user_email1+"&user_email2="+user_email2+"&user_birth="+user_birth+"&state="+user_state+"&user_token="+user_token;
            Log.i("회원가입 샌드메세지",sendMSG);
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
