package com.example.jock.jeim_main.Major;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.R;


public class Department_key_have extends AppCompatActivity implements View.OnClickListener{
    private Intent intent;
    private String key_apply_name,key_num,result;
    private Button btn_return,btn_apply;
    private TextView txt_title,txt_num;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_key_have);

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
                            result = new Department_key_Task().execute("null","null",key_num).get();
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
                            result = new Department_key_Task().execute("apply","apply",key_num).get();
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
            key_num = intent.getStringExtra("호실");

            if(key_apply_name != null){
                txt_title.setVisibility(View.VISIBLE);
                txt_title.setText(key_apply_name+"님이 인수신청함");
                btn_apply.setEnabled(true);
                btn_apply.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.bg_navy));
                btn_apply.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.white));
            }
            txt_num.setText(key_num+"호");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
