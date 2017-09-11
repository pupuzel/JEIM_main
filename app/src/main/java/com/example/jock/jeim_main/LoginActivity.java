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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnjoin,btnlogin;
    TextView loginhome;
    EditText idtext,pwtext;
    private AlertDialog.Builder dialog;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnjoin = (Button)findViewById(R.id.btnjoin);   //회원가입 액티비티로 이동하는 버튼
        btnlogin = (Button)findViewById(R.id.btnlogin);  // 로그인 버튼
        loginhome = (TextView)findViewById(R.id.loginhome); // 뒤로가기 버튼
        idtext = (EditText)findViewById(R.id.idtext);    // 아이디 입력 텍스트창
        pwtext = (EditText)findViewById(R.id.passwdtext);  // 암호 입력 텍스트창

        // 각각 버튼에 대해 클릭이벤트가 발생하면 OnClick() 메소드가 실행이 되게 리스너 연결
        btnjoin.setOnClickListener(this);
        loginhome.setOnClickListener(this);
        btnlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnjoin:  //클릭한 버튼이 회원가입 버튼이면 밑에 구문실행
                startActivity(new Intent(getApplicationContext(), JoinActivity.class));
                break;

            case R.id.loginhome:  //클릭한 버튼이 뒤로가기 버튼이면 밑에 구문실행
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;

            case R.id.btnlogin:  // 클릭한 버튼이 로그인 버튼이면 밑에 구문을 실행
                Logincheck();  //로그인을 하기위한 메소드 실행
                break;
        }
    }

    private void Logincheck(){

        String results;
        String id = idtext.getText().toString();  // 아이디 텍스트창 값을 스트링 변수에 저장
        String pw = pwtext.getText().toString();  // 암호 텍스트창 값을 스트링 변수에 저장
        try {
            loginAT login = new loginAT();   // 실제 JSP에 통신하기 위해 만든 클래스를 생성
            results = login.execute(id, pw).get();  // 아이디,암호 값을 클래스에 매개값(id,pw)으로 전송 이후 get()하여 리턴값을 스트링 변수(results)에 저장

            JSONArray jsonArray = new JSONArray(results); // 받은 제이손 배열형식에 리턴값을 제이손Array 생성자 메소드 매개값으로 던져줌
            JSONObject json = jsonArray.getJSONObject(0); // 제이손배열 값을 제이손오브젝 형식으로 변환
            String juserid = json.getString("회원아이디"); // 제이손 값을 파싱하여 스트링 배열에 저장
            String jusername = json.getString("회원이름"); // 제이손 값을 파싱하여 스트링 배열에 저장

            //정보를 프리퍼런스에 저장하기  (자바에 Session 변수와 비슷한 기능을 가진 클래스)
            pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);  // Login 이라는 프리퍼런스를 MODE_PRIVATE형식으로 쓰겠다 선언
            SharedPreferences.Editor editor = pref.edit();   // pref 프리퍼런스를 수정하겠다라고 선언
            editor.putString("회원이름",jusername);   // put(키,값) 형식으로 데이터를 저장
            editor.putString("회원아이디",juserid);  // put(키,값) 형식으로 데이터를 저장
            editor.commit();  // 수정 완료

            // 로그인이 성공하면 "로그인 환영" 알람창을 띄어준다
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            dialog = builder.setMessage("로그인을 환영합니다").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                // 확인 버튼을 클릭하면 Onclick() 이벤트가 실행이된다.
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent backhome = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(backhome);
                    finish();
                    // 인탠트를 통해 메인화면으로 다시 돌아간다
                }
            });
            dialog.show();

        }catch (Exception e){
            // 로그인이 실패하여 Exception 예외처리가 발생하면 사용자에게 토스트메세지를 띄어준다.
            Toast.makeText(getApplication(),"아이디 혹은 비밀번호가 맞지않습니다",Toast.LENGTH_SHORT).show();
        }

    }


    // 로그인 이용을 위한 AsyncTask  클래스 사용
    class loginAT extends AsyncTask<String,Void,String> {
        String result,sendMsg; // 리턴값과 POST 메시지를 저장하기위해 필요한 스트링 변수선언

        @Override
        protected String doInBackground(String... strings) { // excute() 매개값을 스트링 배열로 받아오는 메소드

            try{
                URL url = new URL(Url.Main+Url.Login); // 로그인 처리를 위한 JSP경로가 들어있는 URL 생성
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //해당 url에 접속할수 있도록 openConnection()
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 리퀘스트 컨탠트 타입을 지정
                httpURLConnection.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
                // 데이터를 보내기 위해 OutputStreamWriter 생성과 동시에 URL 연결
                OutputStreamWriter osw = new OutputStreamWriter(httpURLConnection.getOutputStream());
                // 데이터를 POST 형식으로 스트링 변수에 저장
                sendMsg = "userid="+strings[0]+"&userpw="+strings[1];

                osw.write(sendMsg);//OutputStreamWriter에 담아 전송
                osw.flush(); // OutputStreamWriter 초기화

                //jsp와 통신이 정상적으로 되었을 때 JSP으로 부터 리턴값을 받아온다.

                if(httpURLConnection.getResponseCode() == httpURLConnection.HTTP_OK) { // 만약 JSP 통신이 정상적으로 되었다면
                    //인풋스트림을 통해 리턴값을 UTF-8형식으로 받아온다
                    InputStreamReader tmp = new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8");
                    // 인풋스트림을 통해 받아온 값을 BufferedReader를 통해 읽어온다.
                    BufferedReader reader = new BufferedReader(tmp);
                    // BufferedReader로 읽어온 값을 스트링버퍼 변수에 저장하기 위해 변수를생성
                    StringBuffer buffer = new StringBuffer();

                    String str;
                    // 읽어 들어온 값을 스트링 변수(str)에 저장하는데 NULL이 아닐때까지 반복해서 읽어들인다.
                    while ((str = reader.readLine()) != null) {
                        //읽어 들인 값을 다시 스트링버퍼에 채워넣는다.
                        buffer.append(str);
                    }

                    // 최종적으로 다 읽어들인 값을 리턴값으로 보내주기위해  스트링 변수에 저장시켜준다.
                    result = buffer.toString();

                } else {
                    Log.i("통신 결과", httpURLConnection.getResponseCode()+"에러");
                    // 통신이 실패했을 때 실패한 이유를 알기 위한 로그
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            return result;  // 최종 값을 리턴해준다.
        } // doInBackground finish

    }   //class selectlist finish
}
