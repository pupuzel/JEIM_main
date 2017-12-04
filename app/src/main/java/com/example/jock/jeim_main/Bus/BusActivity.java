package com.example.jock.jeim_main.Bus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.jock.jeim_main.Bottom.GongjiActivity;
import com.example.jock.jeim_main.Bottom.StudentfoodActivity;
import com.example.jock.jeim_main.Bottom.TimetableActivity;
import com.example.jock.jeim_main.Bottom.TotalserviceActivity;
import com.example.jock.jeim_main.Main.MainActivity;
import com.example.jock.jeim_main.R;


public class BusActivity  extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_main);

        TextView bus_homeback = (TextView)findViewById(R.id.bus_homeback);
        bus_homeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backintent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backintent);
                finish();
            }
        });
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
                Intent intent5 = new Intent(getApplicationContext(), TotalserviceActivity.class);
                startActivity(intent5);
                break;
        }
    }
}
