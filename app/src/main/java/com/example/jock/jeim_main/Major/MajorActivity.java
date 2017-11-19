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

import com.example.jock.jeim_main.Another.Pref;
import com.example.jock.jeim_main.Bottom.GongjiActivity;
import com.example.jock.jeim_main.Bottom.StudentfoodActivity;
import com.example.jock.jeim_main.Bottom.TimetableActivity;
import com.example.jock.jeim_main.Main.MainActivity;
import com.example.jock.jeim_main.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class MajorActivity extends AppCompatActivity  implements View.OnClickListener{

    LinearLayout search_view1,search_view2,search_view3;
    Button btn_news,btn_community,btn_keyadmin,btn_board,btn_gongji;
    Button btn_key;
    TextView txt_back;
    Animation animation;

    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.major_main);

        animation = new AlphaAnimation(0, 1);
        animation.setDuration(1000);

        search_view1=(LinearLayout)findViewById(R.id.search_view1);
        search_view2=(LinearLayout)findViewById(R.id.search_view2);
        search_view3=(LinearLayout)findViewById(R.id.search_view3);
        txt_back = (TextView) findViewById(R.id.department_btn_back);
        btn_news = (Button) findViewById(R.id.department_btn_news);
        btn_community=(Button)findViewById(R.id.department_btn_community);
        btn_gongji =(Button) findViewById(R.id.department_btn_gongji);
        btn_board = (Button) findViewById(R.id.department_btn_board);
        btn_keyadmin=(Button)findViewById(R.id.department_btn_keyadmin);
        btn_key = (Button) findViewById(R.id.department_btn_key);

        txt_back.setOnClickListener(this);
        btn_news.setOnClickListener(this);
        btn_community.setOnClickListener(this);
        btn_gongji.setOnClickListener(this);
        btn_board.setOnClickListener(this);
        btn_keyadmin.setOnClickListener(this);
        btn_key.setOnClickListener(this);

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

            case R.id.department_btn_key :
                Intent intent = new Intent(getApplicationContext(),Major_key_main.class);
                startActivity(intent);
                break;

            case R.id.department_btn_board :
                startActivity(new Intent(getApplicationContext(),Major_board_activity.class));
                break;

            case R.id.department_btn_gongji :
                startActivity(new Intent(getApplicationContext(),Major_gongji_activity.class));
        }
    }
    /* 바텀바 컨트롤 메소드 */
    public void Bottom(View v){
        switch (v.getId()){
            case R.id.bottom_gongji :
                Intent intent = new Intent(getApplicationContext(),GongjiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.bottom_home :
                Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
                break;
            case R.id.bottom_food :
                Intent intent4 = new Intent(getApplicationContext(),StudentfoodActivity.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
