package com.example.jock.jeim_main.Major;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.Another.Pref;
import com.example.jock.jeim_main.Bottom.GongjiActivity;
import com.example.jock.jeim_main.Bottom.StudentfoodActivity;
import com.example.jock.jeim_main.Bottom.TimetableActivity;
import com.example.jock.jeim_main.Main.MainActivity;
import com.example.jock.jeim_main.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class DepartmentActivity extends AppCompatActivity  implements View.OnClickListener{

    LinearLayout search_view1,search_view2,search_view3;
    Button btn_news,btn_community,btn_keyadmin;
    Button btn_key302,btn_key303,btn_key304,btn_key305;
    TextView txt_back;
    Animation animation;

    String result,prefUsernum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_main);

        animation = new AlphaAnimation(0, 1);
        animation.setDuration(1000);

        search_view1=(LinearLayout)findViewById(R.id.search_view1);
        search_view2=(LinearLayout)findViewById(R.id.search_view2);
        search_view3=(LinearLayout)findViewById(R.id.search_view3);
        txt_back = (TextView) findViewById(R.id.department_btn_back);
        btn_news = (Button) findViewById(R.id.department_btn_news);
        btn_community=(Button)findViewById(R.id.department_btn_community);
        btn_keyadmin=(Button)findViewById(R.id.department_btn_keyadmin);
        btn_key302 = (Button)findViewById(R.id.department_btn_key302);
        btn_key303 = (Button)findViewById(R.id.department_btn_key303);
        btn_key304 = (Button)findViewById(R.id.department_btn_key304);
        btn_key305 = (Button)findViewById(R.id.department_btn_key305);

        txt_back.setOnClickListener(this);
        btn_news.setOnClickListener(this);
        btn_community.setOnClickListener(this);
        btn_keyadmin.setOnClickListener(this);
        btn_key302.setOnClickListener(this);
        btn_key303.setOnClickListener(this);
        btn_key304.setOnClickListener(this);
        btn_key305.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.department_btn_back :
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                break;

            case R.id.department_btn_news:
                //레이아웃 보이기기
                search_view1.setAnimation(animation);
                if(search_view1.getVisibility() == View.GONE) {
                    search_view1.setVisibility(View.VISIBLE);
                }else if(search_view1.getVisibility()==View.VISIBLE){
                    search_view1.setVisibility(View.GONE);
                }
                break;

            case R.id.department_btn_community:
                search_view2.setAnimation(animation);
                //레이아웃 보이기기
                if(search_view2.getVisibility() == View.GONE){
                    search_view2.setVisibility(View.VISIBLE);
                }else if(search_view2.getVisibility()==View.VISIBLE) {
                    search_view2.setVisibility(View.GONE);
                }
                break;

            case R.id.department_btn_keyadmin:
                search_view3.setAnimation(animation);
                if(search_view3.getVisibility() == View.GONE){
                    search_view3.setVisibility(View.VISIBLE);
                }else if(search_view3.getVisibility()==View.VISIBLE){
                    search_view3.setVisibility(View.GONE);
                }
                break;

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
            result = new Department_key_Task().execute("cheak","cheak",num).get();

            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String key_have_id = jsonObject.getString("소유자학번");
            String key_have_name = jsonObject.getString("소유자이름");
            String key_have_phone = jsonObject.getString("소유자번호");
            String key_apply_id = jsonObject.getString("신청자학번");
            String key_apply_name = jsonObject.getString("신청자이름");

            if(key_have_id.equals("null")){   // 소유자가 없다면
                Intent intent = new Intent(getApplicationContext(),Department_key_none.class);
                intent.putExtra("호실",num);
                startActivity(intent);
            }else{ // 소유자가 있다면

                if(key_have_id.equals(prefUsernum)){ // 소유자가 나라면
                    Intent intent = new Intent(getApplicationContext(),Department_key_have.class);
                    intent.putExtra("호실",num);
                    if(key_apply_name != "null"){
                        intent.putExtra("신청자이름",key_apply_name);
                    }
                    startActivity(intent);

                }else{  // 소유자가 내가 아니라면
                    Intent intent = new Intent(getApplicationContext(),Department_key_apply.class);
                    intent.putExtra("호실",num);
                    intent.putExtra("소유자이름",key_have_name);
                    intent.putExtra("소유자번호",key_have_phone);
                    intent.putExtra("신청자학번",key_apply_id);
                    startActivity(intent);
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
                break;
            case R.id.bottom_schedule :
                Intent intent3 = new Intent(getApplicationContext(), TimetableActivity.class);
                startActivity(intent3);

                break;
            case R.id.bottom_total :

                break;
        }
    }
}
