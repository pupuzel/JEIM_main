package com.example.jock.jeim_main;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.Jooungo.JooungoActivity;
import com.example.jock.jeim_main.Jooungo.JooungoNewboard;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginActivity extends AppCompatActivity {

    Button btnjoin,btnlogin;
    TextView loginhome;
    EditText idtext,pwtext;
    private AlertDialog.Builder dialog;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnjoin = (Button)findViewById(R.id.btnjoin);
        btnlogin = (Button)findViewById(R.id.btnlogin);
        loginhome = (TextView)findViewById(R.id.loginhome);
        idtext = (EditText)findViewById(R.id.idtext);
        pwtext = (EditText)findViewById(R.id.passwdtext);


        // 회원가입으로 이동 이벤트
        btnjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent joinintent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(joinintent);
            }
        });
        // 홈으로 이동 이벤트
        loginhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeintent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeintent);
            }
        });

        //로그인 버튼 클릭 이벤트
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String results;
                    String id = idtext.getText().toString();
                    String pw = pwtext.getText().toString();
                    try {
                        loginAT login = new loginAT();
                        results = login.execute(id, pw).get();

                        JSONArray jsonArray = new JSONArray(results);
                        JSONObject json = jsonArray.getJSONObject(0);
                        String juserid = json.getString("회원아이디");
                        String jusername = json.getString("회원이름");

                        //정보를 프리퍼런스에 저장하기
                        pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("회원이름",jusername);
                        editor.putString("회원아이디",juserid);
                        editor.commit();

                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        dialog = builder.setMessage("로그인을 환영합니다").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent backhome = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(backhome);
                            }
                        });
                        dialog.show();

                    }catch (Exception e){
                        Toast.makeText(getApplication(),"아이디 혹은 비밀번호가 맞지않습니다",Toast.LENGTH_SHORT).show();
                    }

            }
        });



    }




    // 로그인 이용을 위한 AsyncTask  클래스 사용
    class loginAT extends AsyncTask<String,Void,String> {
        String result,sendMsg;

        @Override
        protected String doInBackground(String... strings) {
            try{
                URL url = new URL(Url.Main+Url.Login);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //해당 url에 접속할수 있도록
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
                OutputStreamWriter osw = new OutputStreamWriter(httpURLConnection.getOutputStream());
                sendMsg = "userid="+strings[0]+"&userpw="+strings[1];

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

    }   //class selectlist finish
}
