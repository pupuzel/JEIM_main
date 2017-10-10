package com.example.jock.jeim_main.Major;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.jock.jeim_main.GongjiActivity;
import com.example.jock.jeim_main.MainActivity;
import com.example.jock.jeim_main.R;

public class DepartmentActivity extends AppCompatActivity {

    Button btn_comjung;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        btn_comjung = (Button) findViewById(R.id.department_btn_comjung);
        btn_comjung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , Department_detail.class);
                startActivity(intent);
            }
        });
    }

    /* 바텀바 컨트롤 메소드 */
    public void Bottom(View v){
        switch (v.getId()){
            case R.id.bottom_gongji :
                Intent intent = new Intent(getApplicationContext(),GongjiActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_home :
                Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;
            case R.id.bottom_food :
                break;
            case R.id.bottom_schedule :
                break;
            case R.id.bottom_total :
                break;
        }
    }
}
