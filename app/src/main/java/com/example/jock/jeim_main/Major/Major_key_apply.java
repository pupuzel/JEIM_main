package com.example.jock.jeim_main.Major;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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


public class Major_key_apply extends AppCompatActivity implements View.OnClickListener{

    TextView txt_key,txt_title;
    Button btn_call,btn_apply;

    Intent intent;
    AlertDialog.Builder builder;
    String key_num,key_have_name,key_have_phone,key_apply_id,PrefUserid,result;
    boolean ischeakapply;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.major_key_apply);

        ActivityCompat.requestPermissions(this ,new String[] {Manifest.permission.CALL_PHONE},MODE_PRIVATE);

        txt_key = (TextView) findViewById(R.id.department_key_apply_txt_num);
        txt_title = (TextView) findViewById(R.id.department_key_apply_txt_title);
        btn_call = (Button) findViewById(R.id.department_key_apply_btn_call);
        btn_apply = (Button) findViewById(R.id.department_key_apply_btn_apply);

        btn_call.setOnClickListener(this);
        btn_apply.setOnClickListener(this);
        setData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.department_key_apply_btn_call :
                calling();
                break;

            case R.id.department_key_apply_btn_apply :
                builder = new AlertDialog.Builder(Major_key_apply.this);
                if(ischeakapply == false) {
                    builder.setTitle("인수신청 하시겠습니까?");
                    builder.setMessage("보유자가 인계확인을 해주어야합니다");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                result = new Major_key_Task().execute("null", PrefUserid, key_num).get();
                                if (result.equals("success")) {
                                    Toast.makeText(getApplicationContext(), "키 인수신청 되었습니다", Toast.LENGTH_LONG).show();
                                    finish();
                                }else if(result.equals("fail")){
                                    Toast.makeText(getApplicationContext(), "이미 다른사람이 인수신청 하였습니다", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }else{
                    builder.setTitle("인수신청을 취소 하겠습니까?");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                result = new Major_key_Task().execute("cencle", "cencle", key_num).get();
                                if (result.equals("success")) {
                                    Toast.makeText(getApplicationContext(), "인수신청이 취소되었습니다", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }
                break;
        }
    }

    public void setData(){
        try{
            intent = getIntent();
            key_num = intent.getStringExtra("호실");
            key_have_name = intent.getStringExtra("소유자이름");
            key_have_phone = intent.getStringExtra("소유자번호");
            key_apply_id = intent.getStringExtra("신청자학번");

            Pref.Login = getSharedPreferences("Login", Activity.MODE_PRIVATE);
            PrefUserid = Pref.Login.getString("회원아이디",null);

            txt_key.setText(key_num+" 호");
            txt_title.setText(key_have_name+"님이 열쇠를 소유하고 있습니다");
            if(key_apply_id.equals(PrefUserid)){;
                btn_apply.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.cancel));
                ischeakapply = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void calling(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+key_have_phone));
        try {
            startActivity(intent);
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
