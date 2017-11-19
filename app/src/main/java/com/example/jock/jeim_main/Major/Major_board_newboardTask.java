package com.example.jock.jeim_main.Major;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jock.jeim_main.Another.Url;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Jock on 2017-11-17.
 */

public class Major_board_newboardTask extends AsyncTask<Major_board_newboardNotice,Void,String> {

    private Major_board_newboardNotice info = new Major_board_newboardNotice();
    private String lineEnd = "\r\n";
    private String twoHyphens = "--";
    private String boundary = "*****";
    private ProgressDialog mProgress;

    public Major_board_newboardTask(){
    }

    public Major_board_newboardTask(ProgressDialog mProgress){
        this.mProgress = mProgress;
    }

    @Override
    protected String doInBackground(Major_board_newboardNotice... params) {

        StringBuilder html = new StringBuilder();
        String mResult,result = null;
        String userid,content;
        List<byte[]> imglist;

        try {
            info = params[0];
            userid = info.getUserid();
            content = info.getContent();
            imglist = info.getImgbytelist();

            URL url = new URL(Url.Main+Url.Departmentnewboard);
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
            for (int i = 0; i < imglist.size();i++ ){
                if(imglist.get(i) != null) {
                    String filepath = "major"+String.valueOf(System.currentTimeMillis()) + ".jpg";
                    ByteArrayInputStream mByteInputStream = new ByteArrayInputStream(imglist.get(i));

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
            outParam.writeBytes("Content-Disposition: form-data; name=\"userid\""+ lineEnd);
            outParam.writeBytes(lineEnd);
            outParam.writeBytes(URLEncoder.encode(userid, "UTF-8"));
            outParam.writeBytes(lineEnd);
            outParam.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            outParam.writeBytes(twoHyphens + boundary + lineEnd);
            outParam.writeBytes("Content-Disposition: form-data; name=\"content\""+ lineEnd);
            outParam.writeBytes(lineEnd);
            outParam.writeBytes(URLEncoder.encode(content, "UTF-8"));
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
                br.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}
