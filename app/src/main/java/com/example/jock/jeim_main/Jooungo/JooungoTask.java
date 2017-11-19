package com.example.jock.jeim_main.Jooungo;

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


public class JooungoTask extends AsyncTask<JooungoUpdateNotice,Void,String> {
    private JooungoUpdateNotice info = new JooungoUpdateNotice();
    private String lineEnd = "\r\n";
    private String twoHyphens = "--";
    private String boundary = "*****";

    @Override
    protected String doInBackground(JooungoUpdateNotice...parmas) {

        StringBuilder html = new StringBuilder();
        String mResult,result = null;
        String userid,price,title,content,group;

        JooungoUpdateNotice info = parmas[0];
        userid = info.getUserid();
        price = info.getPrice();
        title = info.getTitle();
        content = info.getContent();
        group = info.getGroup();
        try {

            URL url = new URL(Url.Main+Url.JooungoBoard);
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

                String filepath = "jooungo"+String.valueOf(System.currentTimeMillis()) + ".jpg"; // 서버에 저장하기 위해 현재 시간을 ms초로 바꾼값+jpg 라고 명시적 이름을 지어줌
                ByteArrayInputStream mByteInputStream = new ByteArrayInputStream(info.getimg(i)); // 이미지 바이트값을 바이트스트림으로 변환

                int bytesAvailable = mByteInputStream.available(); // 이미지 바이트 크기 가져오기
                int maxBufferSize = 1024;  // 최대 바이트설정 값
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);  // 비교하여 최소값을 가져오기

                byte[] buffer = new byte[bufferSize];
                int bytesRead = mByteInputStream.read(buffer, 0, bufferSize);


                outParam.writeBytes(twoHyphens + boundary + lineEnd);
                outParam.writeBytes("Content-Disposition: form-data; name=\"file"+i+"\";filename=\"" + filepath+"\"" + lineEnd);
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
            //파일올리기 끝

            // text 추가
            outParam.writeBytes(twoHyphens + boundary + lineEnd);
            outParam.writeBytes("Content-Disposition: form-data; name=\"userid\""+ lineEnd);
            outParam.writeBytes(lineEnd);
            outParam.writeBytes(URLEncoder.encode(userid, "UTF-8"));
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
                Log.i("리턴",result);
                br.close();
            }

        }catch (Exception ex) {ex.printStackTrace();}
        return result;
    }

}
