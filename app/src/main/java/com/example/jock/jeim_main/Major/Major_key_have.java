package com.example.jock.jeim_main.Major;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

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


public class Major_key_have extends AppCompatActivity implements View.OnClickListener{
    private Intent intent;
    private String key_apply_name,key_num,result,PrefUserid,key_apply_id;
    private Button btn_return,btn_apply;
    private TextView txt_title,txt_num;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.major_key_have);

        btn_return = (Button) findViewById(R.id.department_key_have_btn_return);
        btn_apply = (Button) findViewById(R.id.department_key_have_btn_apply);
        txt_title = (TextView) findViewById(R.id.department_key_have_txt_title);
        txt_num = (TextView) findViewById(R.id.department_key_have_txt_num);

        btn_apply.setOnClickListener(this);
        btn_return.setOnClickListener(this);
        setData();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.department_key_have_btn_return : // 반납하기 클릭
                builder = new AlertDialog.Builder(this);
                builder.setTitle("과사에 키를 반납하시겠습니까?");
                builder.setPositiveButton("확인",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            result = new Major_key_Task().execute("null","null",key_num).get();
                            if(result.equals("success")){
                                Toast.makeText(getApplicationContext(),"반납완료",Toast.LENGTH_SHORT).show();
                                finish();
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
                break;

            case R.id.department_key_have_btn_apply : // 인계하기 클릭
                builder = new AlertDialog.Builder(this);
                builder.setTitle(key_apply_name+"님에게 키를 인계하시겠습니까?");
                builder.setPositiveButton("확인",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            result = new Major_key_Task().execute(key_apply_id,"apply",key_num).get();
                            if(result.equals("success")){
                                Toast.makeText(getApplicationContext(),"반납완료",Toast.LENGTH_SHORT).show();
                                finish();
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
                break;
        }

    }

    public void setData(){
        try{
            intent = getIntent();
            key_apply_name = intent.getStringExtra("신청자이름");
            key_apply_id = intent.getStringExtra("신청자학번");
            key_num = intent.getStringExtra("호실");

            Pref.Login = getSharedPreferences("Login", Activity.MODE_PRIVATE);
            PrefUserid = Pref.Login.getString("회원아이디",null);

            if(key_apply_name != null){
                txt_title.setVisibility(View.VISIBLE);
                txt_title.setText(key_apply_name+"님이 인수신청하셨습니다.");
                btn_apply.setEnabled(true);
                btn_apply.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.bg_navy));
                btn_apply.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
            }
            txt_num.setText(key_num+"호");
        }catch (Exception e){
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