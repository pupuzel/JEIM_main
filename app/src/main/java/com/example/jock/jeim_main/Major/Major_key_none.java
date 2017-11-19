package com.example.jock.jeim_main.Major;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.Another.Pref;
import com.example.jock.jeim_main.Bottom.GongjiActivity;
import com.example.jock.jeim_main.Bottom.StudentfoodActivity;
import com.example.jock.jeim_main.Bottom.TimetableActivity;
import com.example.jock.jeim_main.Main.MainActivity;
import com.example.jock.jeim_main.R;



public class Major_key_none extends AppCompatActivity {
    Button btn_have;
    TextView txt_num;
    AlertDialog.Builder builder;
    String result,key_num,prefUserid;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.major_key_none);

        intent = getIntent();
        key_num = intent.getStringExtra("호실");

        Pref.Login = getSharedPreferences("Login", Activity.MODE_PRIVATE);
        prefUserid = Pref.Login.getString("회원아이디",null);

        txt_num = (TextView) findViewById(R.id.department_key_none_txt_num);
        txt_num.setText(key_num+"호");
        btn_have = (Button) findViewById(R.id.department_key_none_btn_have);
        btn_have.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(Major_key_none.this);
                builder.setTitle("키를 소유하시겠습니까?");
                builder.setMessage("분실시 본인책임이 따릅니다");
                builder.setPositiveButton("확인",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            result = new Major_key_Task().execute(prefUserid,"null",key_num).get();
                            if(result.equals("success")){
                                Toast.makeText(getApplicationContext(),"키를 잘관리해주세요",Toast.LENGTH_LONG).show();
                                finish();
                            }else if(result.equals("fail")){
                                Toast.makeText(getApplicationContext(),"이미 다른사람이 소유하고있습니다",Toast.LENGTH_LONG).show();
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
                break;
        }
    }
}
