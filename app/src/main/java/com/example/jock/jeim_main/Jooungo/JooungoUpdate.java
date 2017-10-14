package com.example.jock.jeim_main.Jooungo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jock.jeim_main.R;
import com.example.jock.jeim_main.Task.JooungoUpdateTask;
import com.example.jock.jeim_main.Url;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class JooungoUpdate extends AppCompatActivity implements View.OnClickListener{

    private int REQ_CODE_SELECT_IMAGE =100;
    private int addimgIDVALUE = 0;
    private Jooungoboardinfo info;
    private SharedPreferences pref;
    private ArrayAdapter adapter;
    private ProgressDialog mProgress;

    private Button calcle,check;
    private EditText price,title,contents;
    private ImageView updateimg1,updateimg2,updateimg3;
    private Drawable drawable;
    private Spinner spinner;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_jooungo_updateboard);

        spinner = (Spinner)findViewById(R.id.Jooungo_updateboard_spiner);
        adapter = ArrayAdapter.createFromResource(this, R.array.Jooungo_newboard,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        info = new Jooungoboardinfo();
        calcle = (Button)findViewById(R.id.btn_jooungo_updateboard_calcle);
        check = (Button)findViewById(R.id.btn_jooungo_updateboard_check);

        price = (EditText)findViewById(R.id.edit_jooungo_updateboard_price);
        title = (EditText)findViewById(R.id.edit_jooungo_updateboard_title);
        contents = (EditText)findViewById(R.id.edit_jooungo_updateboard_contents);

        updateimg1 = (ImageView) findViewById(R.id.btn_text_jooungo_updateimg1);
        updateimg2 = (ImageView) findViewById(R.id.btn_text_jooungo_updateimg2);
        updateimg3 = (ImageView) findViewById(R.id.btn_text_jooungo_updateimg3);

        updateimg1.setOnClickListener(this);
        updateimg2.setOnClickListener(this);
        updateimg3.setOnClickListener(this);
        calcle.setOnClickListener(this);
        check.setOnClickListener(this);

        getBoardvalue();

    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.btn_jooungo_updateboard_calcle :   //뒤로가기 버튼 클릭
                onBackPressed();
                break;

            case R.id.btn_jooungo_updateboard_check : // 게시판 등록 버튼 클릭
                addboard();
                break;

            default:  //그외 사진추가 클릭
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                addimgIDVALUE= v.getId(); //선택한 위젯
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE); // 앨범으로 이동할때 각 버튼에 대한 int값을 던져주고 사진을 선택하면 onActivityResult()콜백
                break;
        } // finish switch

    } // finish onClick()


    //게시판 정보 가져오기
    private void getBoardvalue(){

        Intent intent = getIntent();
        info.setBoardcode(intent.getStringExtra("게시판코드"));
        String gettitle = intent.getStringExtra("제목");
        String getcontent = intent.getStringExtra("내용");
        int getprice = intent.getIntExtra("가격",0);
        int getgroup = intent.getIntExtra("분류",0);
        String getimg1 = intent.getStringExtra("이미지1");
        String getimg2 = intent.getStringExtra("이미지2");
        String getimg3 = intent.getStringExtra("이미지3");

        title.setText(gettitle);
        contents.setText(getcontent);
        price.setText(String.valueOf(getprice));
        spinner.setSelection(getgroup);
        adapter.notifyDataSetChanged();

        new Uitask(getimg1,getimg2,getimg3).start();
    }
    //사진을 가져오는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    String name_Str = getImageNameToUri(data.getData());

                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    image_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);

                    switch(addimgIDVALUE){
                        case R.id.btn_text_jooungo_updateimg1:
                            info.setImg1(byteArray.toByteArray());
                            updateimg1.setImageBitmap(image_bitmap);
                            updateimg2.setVisibility(View.VISIBLE);
                            break;
                        case R.id.btn_text_jooungo_updateimg2:
                            info.setImg2(byteArray.toByteArray());
                            updateimg2.setImageBitmap(image_bitmap);
                            updateimg3.setVisibility(View.VISIBLE);
                            break;
                        case R.id.btn_text_jooungo_updateimg3:
                            info.setImg3(byteArray.toByteArray());
                            updateimg3.setImageBitmap(image_bitmap);
                            break;
                    }
                    Toast.makeText(getBaseContext(),name_Str, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //사진 이름 가져오기
    public String getImageNameToUri(Uri data) {

        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        return imgName;
    }


    public void addboard() { //게시판 등록 버튼 메소드
        String group = null;

        switch (spinner.getSelectedItem().toString()) {
            case "삽니다":
                group = "2";
                break;
            case "팝니다":
                group = "1";
                break;
            default: group = "0";
                break;
        }

        if (group == "0") {
            Toast.makeText(getApplicationContext(),"분류를 선택해주세요",Toast.LENGTH_SHORT).show();
        } else if(price.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(),"가격을 입력해주세요",Toast.LENGTH_SHORT).show();
        } else if(title.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(),"제목을 입력해주세요",Toast.LENGTH_SHORT).show();
        } else if(contents.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(),"내용을 입력해주세요",Toast.LENGTH_SHORT).show();
        } else  if (group != "0"){
            mProgress = ProgressDialog.show(this, "서버에 저장하는 중입니다.", "잠시만 기다려 주세요.");
            mProgress.setCancelable(false);
            new Task(group).start();
        }

    } // finish addboard


    class Task extends Thread{
        String result,group;

        Task(String group){
            this.group = group;
        }

        @Override
        public void run() {
            pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);
            info.setUserid(pref.getString("회원아이디", null));
            info.setPrice(price.getText().toString());
            info.setTitle(title.getText().toString());
            info.setContent(contents.getText().toString());
            info.setGroup(group);

            try {
                JooungoUpdateTask task = new JooungoUpdateTask();
                result = task.execute(info).get();
            } catch (Exception e) {  e.printStackTrace(); }

            if (result.equals("success")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgress.dismiss();
                        Toast.makeText(getApplicationContext(),"게시글 수정이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(getApplicationContext(),JooungoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgress.dismiss();
                        Toast.makeText(getApplicationContext(),"서버저장에 실패함",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    //기존 게시판 이미지 뿌려주기 클래스
    private class Uitask extends Thread{
        String img1,img2,img3;

        public Uitask(String img1,String img2,String img3) {
            this.img1 = img1;
            this.img2 = img2;
            this.img3 = img3;
        }

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    mProgress = new ProgressDialog(JooungoUpdate.this);
                    mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgress.setMessage("잠시만 기다려주세요 로딩중입니다.");
                    mProgress.show();

                    if( (img1.equals("null")) == false){

                        Glide.with(getApplicationContext())
                                .load(Url.Main + Url.ImgTake +img1)
                                .asBitmap()
                                .thumbnail(0.1f)
                                .into(updateimg1);
                        updateimg2.setVisibility(View.VISIBLE);

                    }
                    if((img2.equals("null")) == false){

                        Glide.with(getApplicationContext())
                                .load(Url.Main + Url.ImgTake +img2)
                                .asBitmap()
                                .thumbnail(0.1f)
                                .into(updateimg2);
                        updateimg3.setVisibility(View.VISIBLE);

                    }
                    if((img3.equals("null")) == false){

                        Glide.with(getApplicationContext())
                                .load(Url.Main + Url.ImgTake +img3)
                                .asBitmap()
                                .thumbnail(0.1f)
                                .into(updateimg3);

                    }
                }

            });

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgress.dismiss();
                }
            });
        }
    }
}// finish class
