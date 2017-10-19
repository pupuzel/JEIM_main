package com.example.jock.jeim_main.Major;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jock.jeim_main.MainActivity;
import com.example.jock.jeim_main.R;

public class DepartmentActivity extends AppCompatActivity  implements View.OnClickListener{

    LinearLayout search_view1,search_view2,search_view3;
    Button btn_news,btn_community,btn_keyadmin;
    TextView txt_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_main);

        search_view1=(LinearLayout)findViewById(R.id.search_view1);
        search_view2=(LinearLayout)findViewById(R.id.search_view2);
        search_view3=(LinearLayout)findViewById(R.id.search_view3);
        txt_back = (TextView) findViewById(R.id.department_btn_back);
        btn_news = (Button) findViewById(R.id.btn_news);
        btn_community=(Button)findViewById(R.id.btn_community);
        btn_keyadmin=(Button)findViewById(R.id.btn_keyadmin);

        txt_back.setOnClickListener(this);
        btn_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation1 = new AlphaAnimation(0, 1);
                animation1.setDuration(1000);
                //레이아웃 보이기기
                if(search_view1.getVisibility() == View.GONE) {
                    search_view1.setVisibility(View.VISIBLE);
                    search_view1.setAnimation(animation1);
                }else if(search_view1.getVisibility()==View.VISIBLE){
                    search_view1.setVisibility(View.GONE);
                    search_view1.setAnimation(animation1);}

            }
        }); //closing the setOnClickListener method

        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation2 = new AlphaAnimation(0, 1);
                animation2.setDuration(1000);
                //레이아웃 보이기기
                if(search_view2.getVisibility() == View.GONE){
                    search_view2.setVisibility(View.VISIBLE);
                    search_view2.setAnimation(animation2);}
                else if(search_view2.getVisibility()==View.VISIBLE) {
                    search_view2.setVisibility(View.GONE);
                    search_view2.setAnimation(animation2);}
            }
        });
        btn_keyadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation3 = new AlphaAnimation(0, 1);
                animation3.setDuration(1000);
                if(search_view3.getVisibility() == View.GONE){
                    search_view3.setVisibility(View.VISIBLE);
                    search_view3.setAnimation(animation3); }
                else if(search_view3.getVisibility()==View.VISIBLE){
                    search_view3.setVisibility(View.GONE);
                    search_view3.setAnimation(animation3);}
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.department_btn_back :
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
                break;
        }
    }
}
