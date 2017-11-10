package com.example.jock.jeim_main.Jooungo;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.Another.GalleryBitmap;
import com.example.jock.jeim_main.R;

public class JooungoNewboardActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    private int REQ_CODE_SELECT_IMAGE =100;
    private int addimgIDVALUE = 0;
    private String imgPath,imgName;
    private JooungoUpdateNotice info;
    private SharedPreferences pref;
    private ArrayAdapter adapter;
    private ProgressDialog mProgress;

    private Button calcle,check;
    private EditText price,title,contents;
    private Drawable drawable;
    private Spinner spinner;
    private LinearLayout addimglayout;

    private FrameLayout Frame1,Frame2,Frame3;
    private TextView addimg1,addimg2,addimg3;
    private ImageView clear1,clear2,clear3;

    private Animation animation;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jooungo_newboard);

        ActivityCompat.requestPermissions(this ,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},MODE_PRIVATE);

        spinner = (Spinner)findViewById(R.id.Jooungo_newboard_spiner);
        adapter = ArrayAdapter.createFromResource(this, R.array.Jooungo_newboard,android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        info = new JooungoUpdateNotice();
        calcle = (Button)findViewById(R.id.btn_jooungo_newboard_calcle);
        check = (Button)findViewById(R.id.btn_jooungo_newboard_check);

        price = (EditText)findViewById(R.id.edit_jooungo_newboard_price);
        title = (EditText)findViewById(R.id.edit_jooungo_newboard_title);
        contents = (EditText)findViewById(R.id.edit_jooungo_newboard_contents);

        addimglayout = (LinearLayout) findViewById(R.id.linear_jooungo_newboard_addimg);
        Frame1 = (FrameLayout) findViewById(R.id.layout_jooungo_newboard_addimg1);
        Frame2 = (FrameLayout) findViewById(R.id.layout_jooungo_newboard_addimg2);
        Frame3 = (FrameLayout) findViewById(R.id.layout_jooungo_newboard_addimg3);
        addimg1 = (TextView) findViewById(R.id.btn_text_jooungo_addimg1);
        addimg2 = (TextView) findViewById(R.id.btn_text_jooungo_addimg2);
        addimg3 = (TextView) findViewById(R.id.btn_text_jooungo_addimg3);
        clear1 = (ImageView) findViewById(R.id.imageview_jooungo_newboard_clearimg1);
        clear2 = (ImageView) findViewById(R.id.imageview_jooungo_newboard_clearimg2);
        clear3 = (ImageView) findViewById(R.id.imageview_jooungo_newboard_clearimg3);

        addimg1.setOnClickListener(this);
        addimg2.setOnClickListener(this);
        addimg3.setOnClickListener(this);
        clear1.setOnClickListener(this);
        clear2.setOnClickListener(this);
        clear3.setOnClickListener(this);
        calcle.setOnClickListener(this);
        check.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

       animation = new AlphaAnimation(0, 1);
       animation.setDuration(1000);

    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.btn_jooungo_newboard_calcle :   //뒤로가기 버튼 클릭
                startActivity(new Intent(getApplicationContext(),JooungoActivity.class));
                finish();
                break;
            case R.id.btn_jooungo_newboard_check : // 게시판 등록 버튼 클릭
                addboard();
                break;
            case R.id.btn_text_jooungo_addimg1 :  //첫번쨰 사진추가 클릭
                startActivityForResult(getImg(v.getId()), REQ_CODE_SELECT_IMAGE); // 앨범으로 이동할때 각 버튼에 대한 int값(getId())을 던져주고 사진을 선택하면 onActivityResult()콜백
                break;
            case R.id.btn_text_jooungo_addimg2 :  //두번쨰 사진추가 클릭
                startActivityForResult(getImg(v.getId()), REQ_CODE_SELECT_IMAGE);
                break;
            case R.id.btn_text_jooungo_addimg3 :  //세번쨰 사진추가 클릭
                startActivityForResult(getImg(v.getId()), REQ_CODE_SELECT_IMAGE);
                break;

            case R.id.imageview_jooungo_newboard_clearimg1 :// 첫번째 사진삭제 클릭
                addimglayout.setAnimation(animation);
                if(info.size() == 1){
                    Frame2.setVisibility(View.INVISIBLE);
                    clear1.setVisibility(View.INVISIBLE);
                    addimg1.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.ic_add));
                    info.setImg1(null);
                }else if(info.size() == 2){
                    Frame3.setVisibility(View.INVISIBLE);
                    clear2.setVisibility(View.INVISIBLE);
                    addimg1.setBackgroundDrawable(new BitmapDrawable(getImgbitmap(addimg2)));
                    addimg2.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.ic_add));
                    info.setImg1(info.getimg(1));
                    info.setImg2(null);
                }else if(info.size() == 3){
                    clear3.setVisibility(View.INVISIBLE);
                    addimg1.setBackgroundDrawable(new BitmapDrawable(getImgbitmap(addimg2)));
                    addimg2.setBackgroundDrawable(new BitmapDrawable(getImgbitmap(addimg3)));
                    addimg3.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.ic_add));
                    info.setImg1(info.getimg(1));
                    info.setImg2(info.getimg(2));
                    info.setImg3(null);
                }
                break;
            case R.id.imageview_jooungo_newboard_clearimg2 :  // 두번째 사진삭제 클릭
                addimglayout.setAnimation(animation);
                if(info.size() == 2){
                    Frame3.setVisibility(View.INVISIBLE);
                    clear2.setVisibility(View.INVISIBLE);
                    addimg2.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.ic_add));
                    info.setImg2(null);
                }else if(info.size() == 3){
                    clear3.setVisibility(View.INVISIBLE);
                    addimg2.setBackgroundDrawable(new BitmapDrawable(getImgbitmap(addimg3)));
                    addimg3.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.ic_add));
                    info.setImg2(info.getimg(2));
                    info.setImg3(null);
                }
                break;
            case R.id.imageview_jooungo_newboard_clearimg3 :  // 세번째 사진삭제 클릭
                addimglayout.setAnimation(animation);
                clear3.setVisibility(View.INVISIBLE);
                addimg3.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.ic_add));
                info.setImg3(null);
                break;
        } // finish switch

    } // finish onClick()

    //사진을 가져오는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    String name_Str = getImageNameToUri(data.getData());
                    Toast.makeText(getBaseContext(),name_Str, Toast.LENGTH_SHORT).show();
                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    GalleryBitmap galleryBitmap = new GalleryBitmap(image_bitmap,imgPath);

                    switch(addimgIDVALUE){
                        case R.id.btn_text_jooungo_addimg1:
                            info.setImg1(galleryBitmap.getByteArray());
                            drawable = new BitmapDrawable(galleryBitmap.getBitmap());
                            addimg1.setBackgroundDrawable(drawable);
                            clear1.setVisibility(View.VISIBLE);
                            Frame2.setVisibility(View.VISIBLE);
                            break;
                        case R.id.btn_text_jooungo_addimg2:
                            info.setImg2(galleryBitmap.getByteArray());
                            drawable = new BitmapDrawable(galleryBitmap.getBitmap());
                            addimg2.setBackgroundDrawable(drawable);
                            clear2.setVisibility(View.VISIBLE);
                            Frame3.setVisibility(View.VISIBLE);
                            break;
                        case R.id.btn_text_jooungo_addimg3:
                            info.setImg3(galleryBitmap.getByteArray());
                            drawable = new BitmapDrawable(galleryBitmap.getBitmap());
                            addimg3.setBackgroundDrawable(drawable);
                            clear3.setVisibility(View.VISIBLE);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 스피너 아이템 메소드
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView)parent.getChildAt(0)).setTextSize(12);
        ((TextView)parent.getChildAt(0)).setGravity(Gravity.CENTER);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //앨범으로 이동해서 사진 가져오기
    public Intent getImg(int getId){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        addimgIDVALUE= getId; //선택한 위젯

        return  intent;
    }

    //이미지 비트맵 가져오기
    public Bitmap getImgbitmap(TextView addimg){
        addimg.setDrawingCacheEnabled(true);
        addimg.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addimg.layout(0, 0, addimg.getMeasuredWidth(), addimg.getMeasuredHeight());
        addimg.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(addimg.getDrawingCache());
        addimg.setDrawingCacheEnabled(false); // clear drawing cache

        return bitmap;
    }

    //사진 경로,이름 가져오기
    public String getImageNameToUri(Uri data) {

        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        imgPath = cursor.getString(column_index);
        imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

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
            mProgress = ProgressDialog.show(JooungoNewboardActivity.this, "서버에 저장하는 중입니다.", "잠시만 기다려 주세요.");
            mProgress.setCancelable(false);
            new Task(group).start();
        }

    } // finish addboard


   private class Task extends Thread{
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
                JooungoTask task = new JooungoTask();
                result = task.execute(info).get();
            } catch (Exception e) {  e.printStackTrace(); }

            if (result.equals("success")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgress.dismiss();
                        Toast.makeText(getApplicationContext(),"게시글이 등록되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(getApplicationContext(),JooungoActivity.class);
                startActivity(intent);
                finish();

            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgress.dismiss();
                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

}// finish class
