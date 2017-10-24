package com.example.jock.jeim_main.Main;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jock.jeim_main.R;
import com.example.jock.jeim_main.Task.JoinTask;

public class    JoinActivity extends AppCompatActivity implements View.OnClickListener,
                                                               DatePickerDialog.OnDateSetListener {

    private ArrayAdapter adapter;
    private Spinner spinner;
    private EditText editID,editpass,editpasscheck,editNAME,editPHONE,editEMAIL1,editEMAIL2;
    private EditText editYear,editMonth,editDay;
    private Button btn_student_check,btn_Join;
    private RadioGroup gendergroup;

    private boolean ischeckPermission,ischeckStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        btn_student_check = (Button) findViewById(R.id.Join_btn_student_check);
        btn_Join = (Button) findViewById(R.id.Join_btn_join);
        editID = (EditText) findViewById(R.id.Join_edit_id);
        editpass = (EditText) findViewById(R.id.Join_edit_password);
        editpasscheck = (EditText) findViewById(R.id.Join_edit_password_check);
        editNAME = (EditText) findViewById(R.id.Join_edit_name);
        editPHONE = (EditText) findViewById(R.id.Join_edit_phone);
        editEMAIL1 = (EditText) findViewById(R.id.Join_edit_email1);
        editEMAIL2 = (EditText) findViewById(R.id.Join_edit_email2);
        editYear = (EditText) findViewById(R.id.Join_edit_year);
        editMonth = (EditText) findViewById(R.id.Join_edit_month);
        editDay = (EditText) findViewById(R.id.Join_edit_day);

        gendergroup = (RadioGroup) findViewById(R.id.Join_Radio_gendergroup);

        spinner = (Spinner)findViewById(R.id.majorSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.comjung,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btn_student_check.setOnClickListener(this);
        btn_Join.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Join_btn_student_check : // 학생인증 버튼 클릭
                isCheckCameraPermission();
                break;

            case R.id.Join_btn_join:  // 회원가입 버튼 클릭
                ischeckJoin();
                break;
        }
    }
    private String getgender(){  // Radio 버튼 성별 값 가져오기 매소드
        String result = null;
        if(gendergroup.getCheckedRadioButtonId() == R.id.Join_Radio_gender_male){
            result = "M";
        }else if(gendergroup.getCheckedRadioButtonId() == R.id.Join_Radio_gender_female){
            result = "F";
        }
        return  result;
    }



    private boolean ischeckStudent(String barcode){   // 학생 인증 메소드
        boolean result = false;

        if(editID.getText().toString().equals(barcode)){ // 바코드 스캔과 일치하면
            Toast.makeText(getApplicationContext(), "학생인증이 완료되었습니다", Toast.LENGTH_SHORT).show();
            editID.setClickable(false);
            editID.setFocusable(false);
            btn_student_check.setClickable(false);
            btn_student_check.setBackgroundResource(R.drawable.bg_white_bd_gray_radius);
            int color = R.color.whitegray;
            btn_student_check.setTextColor(Color.parseColor("#D5D5D5"));
            btn_student_check.setText("인증완료");
            ischeckStudent = true;
        }else {  // 일치하지 않으면
            Toast.makeText(getApplicationContext(), "입력된 아이디(학번)가 일치하지 않습니다", Toast.LENGTH_SHORT).show();

        }
        return  result;
    }

    private void ischeckJoin(){

        if(ischeckStudent == false){
            Toast.makeText(getApplicationContext(), "학생인증을 완료해주세요", Toast.LENGTH_SHORT).show();
        }else if (editpass.getText().toString().equals("") || editpass.getText().toString() == null){
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if (editpasscheck.getText().toString().equals("") || editpasscheck.getText().toString() == null){
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if ( (editpass.getText().toString().equals(editpasscheck.getText().toString())) == false  ){
            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
        }else if (getgender() == null){
            Toast.makeText(getApplicationContext(), "성별을 선택해주세요", Toast.LENGTH_SHORT).show();
        }else if(editNAME.getText().toString().equals("") || editNAME.getText().toString() == null){
            Toast.makeText(getApplicationContext(), "성함을 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if(editPHONE.getText().toString().equals("") || editPHONE.getText().toString() == null){
            Toast.makeText(getApplicationContext(), "휴대폰 번호를 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if(editEMAIL1.getText().toString().equals("") || editEMAIL1.getText().toString() == null){
            Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if(editEMAIL2.getText().toString().equals("") || editEMAIL2.getText().toString() == null){
            Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if(editYear.getText().toString().equals("") || editYear.getText().toString() == null){
            Toast.makeText(getApplicationContext(), "년도를 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if(editMonth.getText().toString().equals("") || editMonth.getText().toString() == null){
            Toast.makeText(getApplicationContext(), "월을 입력해주세요", Toast.LENGTH_SHORT).show();
        }else if(editDay.getText().toString().equals("") || editDay.getText().toString() == null){
            Toast.makeText(getApplicationContext(), "일을 입력해주세요", Toast.LENGTH_SHORT).show();
        }else{
            String state = String.valueOf(spinner.getSelectedItemPosition());
            String user_id = editID.getText().toString();
            String user_pw = editpass.getText().toString();
            String user_gender = getgender();
            String user_name = editNAME.getText().toString();
            String user_phone = editPHONE.getText().toString();
            String user_email1 = editEMAIL1.getText().toString();
            String user_email2 = editEMAIL2.getText().toString();

            String user_year = editYear.getText().toString();
            String user_month = editMonth.getText().toString();
            String user_day = editDay.getText().toString();
            String user_birth = user_year+user_month+user_day;
          try {
              String result =  new JoinTask().execute(user_id,user_pw,user_gender,user_name,user_phone,user_email1,user_email2,user_birth,state).get();

              if(result.equals("true")){
                  Toast.makeText(getApplicationContext(),"회원가입 완료",Toast.LENGTH_LONG).show();
                  startActivity(new Intent(getApplicationContext(),MainActivity.class));
                  finish();
              }else{
                  Toast.makeText(getApplicationContext(),"이미 가입된 회원입니다.",Toast.LENGTH_LONG).show();
              }
          }catch (Exception e){}
          }
        }

        // 인텐트 콜백 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case CamaraActivity.BarcodeResult :   // 바코드 결과 받고 돌아왔을때
                try {
                    String barcode = data.getStringExtra("result");
                    ischeckStudent(barcode);
                }catch (Exception e){}
                break;

            case CamaraActivity.OrdinaryResult :  // 뒤로가기 버튼 눌러서 돌아왔을때
                break;
        }
    }

    //달력 콜백 메소드
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    //카메라 펄미션 권한 체크
    public void  isCheckCameraPermission(){
        int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        if(permissionCamera == PackageManager.PERMISSION_GRANTED) {
            //권한 설정이 되어있다면 그대로 진행
            Intent intent = new Intent(getApplicationContext(),CamaraActivity.class);
            startActivityForResult(intent,0);
        } else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CALENDAR)){
                // 권한 설정이안되어있고 거절했던 이력이 있을때
            }else {
                // 권한 설정이안되어있고 거절했던 이력이 없을때 혹은 다시보지않기 했을때
            }
            ActivityCompat.requestPermissions(JoinActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
        }
    }

    //카메라 펄미션 권한 체크 콜백메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode ==0){
            Log.i("로그",String.valueOf(grantResults[0]));
            if(grantResults[0] == 0){
                //권한 승낙일 경우
                ischeckPermission =true;
                Intent intent = new Intent(getApplicationContext(),CamaraActivity.class);
                startActivityForResult(intent,0);
            }else {
                //권한 거절일 경우
                ischeckPermission = false;
                Toast.makeText(getApplicationContext(),"카메라 권한을 설정해야합니다",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
