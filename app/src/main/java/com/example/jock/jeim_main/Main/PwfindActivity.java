package com.example.jock.jeim_main.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.Another.Url;
import com.example.jock.jeim_main.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class PwfindActivity extends AppCompatActivity {

    EditText editText;
    Button btn_check;
    String num,email,editValue;
    TextView txt_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_find);

        editText = (EditText) findViewById(R.id.password_edit_content);
        txt_back = (TextView) findViewById(R.id.password_txt_back);
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        btn_check = (Button) findViewById(R.id.password_btn_check);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    editValue = editText.getText().toString();
                    String result = new StudentTask().execute(editValue).get();
                    if(isCheckNum(result)){
                        Checked();
                    }else {
                        Toast.makeText(getApplicationContext(),"등록된 회원이 아닙니다",Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public boolean isCheckNum(String jsonresult){
            boolean result = false;
        try {
            JSONArray jsonArray = new JSONArray(jsonresult);
            JSONObject jsonObject;
            int count = 0;
            while (count < jsonArray.length()) {
                jsonObject = jsonArray.getJSONObject(count);
                num = jsonObject.getString("학번");
                email = jsonObject.getString("이메일");
                count++;
            }
            if(editValue.equals(num)){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) { e.printStackTrace();}

        return false;
    }

    private void Checked(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("임시 비밀번호를 발송하시겠습니까?");
        builder.setPositiveButton("확인",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String result = new PasswordTask().execute(num,email).get();
                    if(result.equals("true")){
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        Toast.makeText(getApplicationContext(),"이메일을 확인해주세요",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"이메일 전송에 실패했습니다.",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){ e.printStackTrace(); }
            }
        });
        builder.setNegativeButton("취소",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
    class StudentTask extends AsyncTask<String,Void,String>{

        String sendMSG;
        String result;

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(Url.Main+Url.Passwordfind);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setUseCaches(false);
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.connect();
                OutputStream outputStream = con.getOutputStream();

                sendMSG ="num="+params[0];
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
}
