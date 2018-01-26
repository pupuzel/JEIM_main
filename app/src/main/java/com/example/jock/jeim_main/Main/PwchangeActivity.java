package com.example.jock.jeim_main.Main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

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


public class PwchangeActivity extends AppCompatActivity implements View.OnClickListener{

    String userid,current_pass,chage_pass;
    EditText edit_current,edit_pass1,edit_pass2;
    TextView txt_back;
    Button btn_check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_change);
        edit_current = (EditText) findViewById(R.id.passfind_edit_mypass);
        edit_pass1 = (EditText) findViewById(R.id.passfind_edit_chagepass);
        edit_pass2 = (EditText) findViewById(R.id.passfind_edit_chagepass_check);
        txt_back = (TextView) findViewById(R.id.password_txt_back);
        btn_check = (Button) findViewById(R.id.passfind_btn_check);

        txt_back.setOnClickListener(this);
        btn_check.setOnClickListener(this);
        Intent intent = getIntent();
        userid = intent.getStringExtra("회원아이디");
        try {
            String result = new StudentTask().execute(userid).get();
            getPass(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.passfind_btn_check){
            if(edit_current.getText().toString().equals("") || edit_current.getText().toString() == null){
                Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
            }else if(edit_pass1.getText().toString().equals("") || edit_pass1.getText().toString() == null){
                Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
            }else if(edit_pass2.getText().toString().equals("") || edit_pass2.getText().toString() == null){
                Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
            }else if( !(edit_current.getText().toString().equals(current_pass)) ){
                Toast.makeText(getApplicationContext(),"현재 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show();
            }else if( !(edit_pass1.getText().toString().equals(edit_pass2.getText().toString()))){
                Toast.makeText(getApplicationContext(),"변경할 비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
            }else{
                String passvalue = edit_pass1.getText().toString();
                new PasswordChangeTask().execute(userid,passvalue);
                Toast.makeText(getApplicationContext(),"변경되었습니다",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        }

        if(v.getId() == R.id.password_txt_back){
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getPass(String jsonresult){
        try {
            JSONArray jsonArray = new JSONArray(jsonresult);
            JSONObject jsonObject;
            int count = 0;
            while (count < jsonArray.length()) {
                jsonObject = jsonArray.getJSONObject(count);
                current_pass = jsonObject.getString("암호");
                count++;
            }
        } catch (Exception e) { e.printStackTrace();}
    }

    class StudentTask extends AsyncTask<String,Void,String> {

        String sendMSG;
        String result;

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(Url.Main+Url.Passwordcheck);
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
