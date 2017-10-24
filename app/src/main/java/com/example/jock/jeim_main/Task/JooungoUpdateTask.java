package com.example.jock.jeim_main.Task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jock.jeim_main.Jooungo.Jooungoboardinfo;
import com.example.jock.jeim_main.Another.Url;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class JooungoUpdateTask extends AsyncTask<Jooungoboardinfo,Void,String> {
    private Jooungoboardinfo info = new Jooungoboardinfo();
    private String lineEnd = "\r\n";
    private String twoHyphens = "--";
    private String boundary = "*****";

    @Override
    protected String doInBackground(Jooungoboardinfo...parmas) {

        StringBuilder html = new StringBuilder();
        String mResult,result = null;
        String price,title,content,group,boardcode;

        Jooungoboardinfo info = parmas[0];
        price = info.getPrice();
        title = info.getTitle();
        content = info.getContent();
        group = info.getGroup();
        boardcode = info.getBoardcode();

        try {

            URL url = new URL(Url.Main+Url.JooungoUpdate);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if (conn != null) {
                conn.setUseCaches(false);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            }else{
                Log.i("연결안됨 - "," URL을 다시 확인해주세요.");
            }

            DataOutputStream outParam = new DataOutputStream(conn.getOutputStream());

            // 파일올리기
            for (int i = 0; i < info.size();i++ ){

                if(info.getimg(i) != null) {
                    String filepath = String.valueOf(System.currentTimeMillis()) + ".jpg";
                    ByteArrayInputStream mByteInputStream = new ByteArrayInputStream(info.getimg(i));

                    int bytesAvailable = mByteInputStream.available();
                    int maxBufferSize = 1024;
                    int bufferSize = Math.min(bytesAvailable, maxBufferSize);

                    byte[] buffer = new byte[bufferSize];
                    int bytesRead = mByteInputStream.read(buffer, 0, bufferSize);


                    outParam.writeBytes(twoHyphens + boundary + lineEnd);
                    outParam.writeBytes("Content-Disposition: form-data; name=\"file" + i + "\";filename=\"" + filepath + "\"" + lineEnd);
                    outParam.writeBytes(lineEnd);

                    // read image
                    while (bytesRead > 0) {
                        outParam.write(buffer, 0, bufferSize);
                        bytesAvailable = mByteInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = mByteInputStream.read(buffer, 0, bufferSize);
                    }

                    outParam.writeBytes(lineEnd);
                    outParam.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                    mByteInputStream.close();

                }
            }
            //파일올리기 끝

            // text 추가
            outParam.writeBytes(twoHyphens + boundary + lineEnd);
            outParam.writeBytes("Content-Disposition: form-data; name=\"boardcode\""+ lineEnd);
            outParam.writeBytes(lineEnd);
            outParam.writeBytes(URLEncoder.encode(boardcode, "UTF-8"));
            outParam.writeBytes(lineEnd);
            outParam.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            outParam.writeBytes(twoHyphens + boundary + lineEnd);
            outParam.writeBytes("Content-Disposition: form-data; name=\"title\""+ lineEnd);
            outParam.writeBytes(lineEnd);
            outParam.writeBytes(URLEncoder.encode(title, "UTF-8"));
            outParam.writeBytes(lineEnd);
            outParam.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            outParam.writeBytes(twoHyphens + boundary + lineEnd);
            outParam.writeBytes("Content-Disposition: form-data; name=\"content\""+ lineEnd);
            outParam.writeBytes(lineEnd);
            outParam.writeBytes(URLEncoder.encode(content, "UTF-8"));
            outParam.writeBytes(lineEnd);
            outParam.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            outParam.writeBytes(twoHyphens + boundary + lineEnd);
            outParam.writeBytes("Content-Disposition: form-data; name=\"price\""+ lineEnd);
            outParam.writeBytes(lineEnd);
            outParam.writeBytes(URLEncoder.encode(price, "UTF-8"));
            outParam.writeBytes(lineEnd);
            outParam.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            outParam.writeBytes(twoHyphens + boundary + lineEnd);
            outParam.writeBytes("Content-Disposition: form-data; name=\"group\""+ lineEnd);
            outParam.writeBytes(lineEnd);
            outParam.writeBytes(URLEncoder.encode(group, "UTF-8"));
            outParam.writeBytes(lineEnd);
            outParam.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            // text 끝

            outParam.flush();
            outParam.close();

            //response 받기
            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(tmp);

                while ((mResult = br.readLine()) != null) {
                    html.append(mResult);
                }
                result = html.toString();
                Log.i("중고업데이트 리턴",result);
                br.close();
            }

        }catch (Exception ex) {ex.printStackTrace();}
        return result;
    }

}
