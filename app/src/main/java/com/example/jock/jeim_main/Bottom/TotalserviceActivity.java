package com.example.jock.jeim_main.Bottom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jock.jeim_main.Bus.BusActivity;
import com.example.jock.jeim_main.FAQ.FAQActivity;
import com.example.jock.jeim_main.Fiction.FictionActivity;
import com.example.jock.jeim_main.Food.FoodMainActivity;
import com.example.jock.jeim_main.Jooungo.JooungoActivity;
import com.example.jock.jeim_main.Library.LibraryActivity;
import com.example.jock.jeim_main.Main.IntroActivity;
import com.example.jock.jeim_main.Major.Major_board_activity;
import com.example.jock.jeim_main.Major.Major_gongji_activity;
import com.example.jock.jeim_main.Major.Major_key_main;
import com.example.jock.jeim_main.R;


public class TotalserviceActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13;
    TextView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_total);

        btn1 = (Button) findViewById(R.id.bottom_total_gongji);
        btn2 = (Button) findViewById(R.id.bottom_total_freeboard);
        btn3 = (Button) findViewById(R.id.bottom_total_key);
        btn4 = (Button) findViewById(R.id.bottom_total_book);
        btn5 = (Button) findViewById(R.id.bottom_total_bus);
        btn6 = (Button) findViewById(R.id.bottom_total_restaurant);
        btn7 = (Button) findViewById(R.id.bottom_total_timetable);
        btn8 = (Button) findViewById(R.id.bottom_total_jooungo);
        btn9 = (Button) findViewById(R.id.bottom_total_todayfood);
        btn10 = (Button) findViewById(R.id.bottom_total_fiction);
        btn11 = (Button) findViewById(R.id.bottom_total_alram);
        btn12 = (Button) findViewById(R.id.bottom_total_intro);
        btn13 = (Button) findViewById(R.id.bottom_total_faq);
        back = (TextView) findViewById(R.id.bottom_total_back);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bottom_total_gongji :
                startActivity(new Intent(getApplicationContext(), Major_gongji_activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bottom_total_freeboard :
                startActivity(new Intent(getApplicationContext(), Major_board_activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bottom_total_key :
                startActivity(new Intent(getApplicationContext(), Major_key_main.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bottom_total_book :
                startActivity(new Intent(getApplicationContext(), LibraryActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bottom_total_bus :
                startActivity(new Intent(getApplicationContext(), BusActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bottom_total_restaurant :
                startActivity(new Intent(getApplicationContext(), StudentfoodActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bottom_total_timetable :
                startActivity(new Intent(getApplicationContext(), TimetableActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bottom_total_jooungo :
                startActivity(new Intent(getApplicationContext(), JooungoActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bottom_total_todayfood :
                startActivity(new Intent(getApplicationContext(), FoodMainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bottom_total_fiction :
                startActivity(new Intent(getApplicationContext(), FictionActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bottom_total_alram :
                startActivity(new Intent(getApplicationContext(), GongjiActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bottom_total_intro :
                startActivity(new Intent(getApplicationContext(), IntroActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bottom_total_faq :
                startActivity(new Intent(getApplicationContext(), FAQActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bottom_total_back :
                onBackPressed();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
