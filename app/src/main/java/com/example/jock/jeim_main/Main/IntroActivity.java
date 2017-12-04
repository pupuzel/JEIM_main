package com.example.jock.jeim_main.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jock.jeim_main.R;


public class IntroActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView dong,jock,gu,noyea;
    TextView intro_homeback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_intro);

        dong = (ImageView) findViewById(R.id.intro_dongdong);
        jock = (ImageView) findViewById(R.id.intro_jock);
        gu = (ImageView) findViewById(R.id.intro_gu);
        noyea = (ImageView) findViewById(R.id.intro_noyea);
        intro_homeback = (TextView)findViewById(R.id.intro_homeback);

        dong.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.dong));
        jock.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.jock));
        gu.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.gu));
        noyea.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.jun));

        intro_homeback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.intro_homeback : // 뒤로가기 버튼 클릭시 메인화면 이동
                Intent backhome = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backhome);
                finish();
                break;
        }
    }
}