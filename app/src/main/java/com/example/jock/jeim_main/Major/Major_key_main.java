package com.example.jock.jeim_main.Major;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jock.jeim_main.Another.Pref;
import com.example.jock.jeim_main.Bottom.GongjiActivity;
import com.example.jock.jeim_main.Bottom.StudentfoodActivity;
import com.example.jock.jeim_main.Bottom.TimetableActivity;
import com.example.jock.jeim_main.Main.MainActivity;
import com.example.jock.jeim_main.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jock on 2017-11-17.
 */

public class Major_key_main extends AppCompatActivity implements View.OnClickListener {
    Button department_btn_key302,department_btn_key303,department_btn_key304,department_btn_key305;
    String result,prefUsernum;
    TextView txt_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.major_key_main);

        department_btn_key302 = (Button)findViewById(R.id.department_btn_key302);
        department_btn_key303 = (Button)findViewById(R.id.department_btn_key303);
        department_btn_key304 = (Button)findViewById(R.id.department_btn_key304);
        department_btn_key305 = (Button)findViewById(R.id.department_btn_key305);

        department_btn_key302.setOnClickListener(this);
        department_btn_key303.setOnClickListener(this);
        department_btn_key304.setOnClickListener(this);
        department_btn_key305.setOnClickListener(this);

        txt_back = (TextView) findViewById(R.id.department_btn_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.department_homeback :
                Intent intent = new Intent(getApplicationContext(), MajorActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            case R.id.department_btn_key302 :
                cheak("302");
                break;
            case R.id.department_btn_key303 :
                cheak("303");
                break;
            case R.id.department_btn_key304 :
                cheak("304");
                break;
            case R.id.department_btn_key305 :
                cheak("305");
                break;
        }
    }
    public void cheak(String num){
        try {
            Pref.Login = getSharedPreferences("Login", Activity.MODE_PRIVATE);
            prefUsernum = Pref.Login.getString("회원아이디",null);
            result = new Major_key_Task().execute("cheak","cheak",num).get();

            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String key_state = jsonObject.getString("키상태");
            String key_have_id = jsonObject.getString("소유자학번");
            String key_have_name = jsonObject.getString("소유자이름");
            String key_have_phone = jsonObject.getString("소유자번호");
            String key_apply_id = jsonObject.getString("신청자학번");
            String key_apply_name = jsonObject.getString("신청자이름");

            if(key_state.equals("1")){   // 과사에있다면
                Intent intent = new Intent(getApplicationContext(),Major_key_none.class);
                intent.putExtra("호실",num);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
            }else if(key_state.equals("0")){ // 소유자가 있다면

                if(key_have_id.equals(prefUsernum)){ // 소유자가 나라면
                    Intent intent = new Intent(getApplicationContext(),Major_key_have.class);
                    intent.putExtra("호실",num);
                    if(key_apply_name != "null"){
                        intent.putExtra("신청자이름",key_apply_name);
                        intent.putExtra("신청자학번",key_apply_id);
                    }
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
                }else{  // 소유자가 내가 아니라면
                    Intent intent = new Intent(getApplicationContext(),Major_key_apply.class);
                    intent.putExtra("호실",num);
                    intent.putExtra("소유자이름",key_have_name);
                    intent.putExtra("소유자번호",key_have_phone);
                    intent.putExtra("신청자학번",key_apply_id);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /* 바텀바 컨트롤 메소드 */
    public void Bottom(View v){
        switch (v.getId()){
            case R.id.bottom_gongji :
                Intent intent = new Intent(getApplicationContext(),GongjiActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.bottom_home :
                Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent2);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
                break;
            case R.id.bottom_food :
                Intent intent4 = new Intent(getApplicationContext(),StudentfoodActivity.class);
                startActivity(intent4);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.bottom_schedule :
                Intent intent3 = new Intent(getApplicationContext(), TimetableActivity.class);
                startActivity(intent3);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.bottom_total :
                break;
        }
    }
}
