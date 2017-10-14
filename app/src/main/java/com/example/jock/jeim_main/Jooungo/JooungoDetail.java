package com.example.jock.jeim_main.Jooungo;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.R;
import com.example.jock.jeim_main.Task.ImgTask;
import com.example.jock.jeim_main.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.concurrent.ExecutionException;

public class JooungoDetail extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences pref;
     private ProgressBar progressBar;
     private ProgressDialog mProgress;
     private Bitmap bitmapimg1,bitmapimg2,bitmapimg3;
     private Intent intent;
     private String boardCode;
     private ImgTask imgTask;
     private NumberFormat nf;
     private JSONObject imglistJson = new JSONObject();
     private JooungoDelete deleteTask;
     private TextView txt_title,txt_username,txt_date,txt_price,txt_content,txt_group,txt_loding;
     private ImageView imageView1,imageView2,imageView3;
     private Button btn_delete,btn_update,btn_backlist;

    private int price,groupvalue;
    private String usernum,prefUsernum;
    private String img1,img2,img3;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_jooungo_detailboard);

        nf  = NumberFormat.getInstance();
        nf.setMaximumIntegerDigits(10);

        txt_title = (TextView) findViewById(R.id.txt_jooungo_detail_title);
        txt_username = (TextView) findViewById(R.id.txt_jooungo_detail_username);
        txt_date = (TextView) findViewById(R.id.txt_jooungo_detail_date);
        txt_price = (TextView) findViewById(R.id.txt_jooungo_detail_price);
        txt_content = (TextView) findViewById(R.id.txt_jooungo_detail_content);
        txt_group = (TextView) findViewById(R.id.txt_jooungo_detail_group);
        txt_loding = (TextView) findViewById(R.id.txt_jooungo_detail_lodingtxt);

        btn_delete = (Button) findViewById(R.id.btn_jooungo_delete);
        btn_update = (Button) findViewById(R.id.btn_jooungo_update);
        btn_backlist = (Button) findViewById(R.id.btn_jooungo_backlist);
        imageView1 = (ImageView) findViewById(R.id.txt_jooungo_detail_img1);
        imageView2 = (ImageView) findViewById(R.id.txt_jooungo_detail_img2);
        imageView3 = (ImageView) findViewById(R.id.txt_jooungo_detail_img3);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_jooungo_detail);

        try{
            pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);
            prefUsernum = pref.getString("회원아이디",null);

            intent = getIntent();
            boardCode = intent.getStringExtra("게시판코드");
            new selectdetail().execute(boardCode);

        }catch (Exception e){   e.printStackTrace();  }

        btn_delete.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_backlist.setOnClickListener(this);
    } // onCreate finish


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_jooungo_delete :
                deletecheck();
                break;

            case R.id.btn_jooungo_update :
                updatecheck();
                break;

            case R.id.btn_jooungo_backlist :
                Intent intent = new Intent(getApplicationContext(),JooungoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
        }

    }

    private void deletecheck(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("정말 게시글을 삭제하시겠습니까?");
        builder.setPositiveButton("확인",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    imglistJson.put("게시판코드",boardCode);
                    deleteTask = new JooungoDelete();
                   String result = deleteTask.execute(imglistJson).get();
                    if(result.equals("success")){
                        Intent intent = new Intent(getApplicationContext(),JooungoActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),boardCode,Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("취소",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    private void updatecheck(){
        Intent intent = new Intent(getApplicationContext(),JooungoUpdate.class);
        intent.putExtra("게시판코드",boardCode);
        intent.putExtra("제목",txt_title.getText().toString());
        intent.putExtra("내용",txt_content.getText().toString());
        intent.putExtra("가격",price);
        intent.putExtra("분류",groupvalue);
        intent.putExtra("이미지1",img1);
        intent.putExtra("이미지2",img2);
        intent.putExtra("이미지3",img3);
        startActivity(intent);
    }

    // 게시판 코드에 맞는 데이터를 가져오는 쓰레드
    class selectdetail extends AsyncTask<String,Void,String> {
        String result,sendMsg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(JooungoDetail.this);
            mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgress.setCancelable(false);
            mProgress.setMessage("잠시만 기다려주세요 로딩중입니다.");
            mProgress.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL(Url.Main+Url.JooungoDetail);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //해당 url에 접속할수 있도록
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
                OutputStreamWriter osw = new OutputStreamWriter(httpURLConnection.getOutputStream());
                sendMsg = "used_bdnum="+strings[0];

                osw.write(sendMsg);//OutputStreamWriter에 담아 전송
                osw.flush();

                //jsp와 통신이 정상적으로 되었을 때 할 코드들입니다.
                if(httpURLConnection.getResponseCode() == httpURLConnection.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();

                    String str;
                    //jsp에 리턴값
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    result = buffer.toString();

                } else {
                    Log.i("통신 결과", httpURLConnection.getResponseCode()+"에러");
                    // 통신이 실패했을 때 실패한 이유를 알기 위한 로그
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            return result;
        } // doInBackground finish

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject json = jsonArray.getJSONObject(0);

                usernum = json.getString("작성자학번");
                txt_title.setText(json.getString("제목"));
                txt_content.setText(json.getString("내용"));
                txt_username.setText(json.getString("작성자"));
                txt_date.setText(json.getString("날짜").substring(0,16));
                price = Integer.parseInt(json.getString("가격"));
                String pricefmt = nf.format(price);
                txt_price.setText(pricefmt+"원");


                if(json.getString("그룹").equals("1")){
                    groupvalue = 1;
                    txt_group.setText("[팝니다]");
                }else{
                    groupvalue = 2;
                    txt_group.setText("[삽니다]");
                }

                img1 = json.getString("이미지1");
                img2= json.getString("이미지2");
                img3 = json.getString("이미지3");
                new imgThread(img1,img2,img3).start();    //이미지 가져오는 쓰레드는 별도의 쓰레드가 작업

                if(prefUsernum.equals(usernum)){

                    btn_delete.setVisibility(View.VISIBLE);
                    btn_update.setVisibility(View.VISIBLE);
                }

        } catch (Exception e) {  e.printStackTrace();   }
            mProgress.dismiss();
        } // onPostExecute() finish


    }   //class selectlist finish

    // 이미지를 가져오는 쓰레드
    class imgThread extends Thread{
        String Simg1,Simg2,Simg3 = null;

        imgThread(String img1,String img2,String img3){
            this.Simg1 = img1;
            this.Simg2 = img2;
            this.Simg3 = img3;
        }

        @Override
        public void run() {
            try {
                if (Simg1 != "null") {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        txt_loding.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        }
                    });

                    imgTask = new ImgTask();
                    bitmapimg1 = imgTask.execute(img1).get();
                    imglistJson.put("이미지1",Simg1);
                    if(Simg2 != "null"){
                        imgTask = new ImgTask();
                        bitmapimg2 = imgTask.execute(Simg2).get();
                        imglistJson.put("이미지2",Simg2);
                        if (Simg3 != "null"){
                            imgTask = new ImgTask();
                            bitmapimg3 = imgTask.execute(Simg3).get();
                            imglistJson.put("이미지3",Simg3);
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (img1 != "null") {
                        imageView1.setImageBitmap(bitmapimg1);
                        imageView1.setVisibility(View.VISIBLE);
                        if(img2 != "null"){
                            imageView2.setImageBitmap(bitmapimg2);
                            imageView2.setVisibility(View.VISIBLE);
                            if (img3 != "null"){
                                imageView3.setImageBitmap(bitmapimg3);
                                imageView3.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    txt_loding.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }
            });


        }
    }
}
