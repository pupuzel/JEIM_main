package com.example.jock.jeim_main.Jooungo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.jock.jeim_main.Another.GalleryBitmap;
import com.example.jock.jeim_main.R;
import com.example.jock.jeim_main.Another.Url;


public class JooungoUpdateActivity extends AppCompatActivity implements View.OnClickListener{

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

    private LinearLayout updateimgLayout;
    private FrameLayout Frame1,Frame2,Frame3;
    private ImageView updateimg1,updateimg2,updateimg3;
    private ImageView clear1,clear2,clear3;

    private Animation animation;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jooungo_updateboard);

        spinner = (Spinner)findViewById(R.id.Jooungo_updateboard_spiner);
        adapter = ArrayAdapter.createFromResource(this, R.array.Jooungo_newboard,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        info = new JooungoUpdateNotice();
        calcle = (Button)findViewById(R.id.btn_jooungo_updateboard_calcle);
        check = (Button)findViewById(R.id.btn_jooungo_updateboard_check);

        price = (EditText)findViewById(R.id.edit_jooungo_updateboard_price);
        title = (EditText)findViewById(R.id.edit_jooungo_updateboard_title);
        contents = (EditText)findViewById(R.id.edit_jooungo_updateboard_contents);

        updateimgLayout = (LinearLayout) findViewById(R.id.linear_jooungo_update_addimg);
        Frame1 = (FrameLayout) findViewById(R.id.layout_jooungo_update_updateimg1);
        Frame2 = (FrameLayout) findViewById(R.id.layout_jooungo_update_updateimg2);
        Frame3 = (FrameLayout) findViewById(R.id.layout_jooungo_update_updateimg3);
        updateimg1 = (ImageView) findViewById(R.id.btn_text_jooungo_updateimg1);
        updateimg2 = (ImageView) findViewById(R.id.btn_text_jooungo_updateimg2);
        updateimg3 = (ImageView) findViewById(R.id.btn_text_jooungo_updateimg3);
        clear1 = (ImageView) findViewById(R.id.imageview_jooungo_update_clearimg1);
        clear2 = (ImageView) findViewById(R.id.imageview_jooungo_update_clearimg2);
        clear3 = (ImageView) findViewById(R.id.imageview_jooungo_update_clearimg3);


        updateimg1.setOnClickListener(this);
        updateimg2.setOnClickListener(this);
        updateimg3.setOnClickListener(this);
        clear1.setOnClickListener(this);
        clear2.setOnClickListener(this);
        clear3.setOnClickListener(this);
        calcle.setOnClickListener(this);
        check.setOnClickListener(this);

        getBoardvalue();

        animation = new AlphaAnimation(0, 1);
        animation.setDuration(1000);

    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.btn_jooungo_updateboard_calcle :   //뒤로가기 버튼 클릭
                onBackPressed();
                break;

            case R.id.btn_jooungo_updateboard_check : // 게시판 등록 버튼 클릭
                addboard();
                break;

            case R.id.btn_text_jooungo_updateimg1 :  //첫번쨰 사진추가 클릭
                startActivityForResult(getImg(v.getId()), REQ_CODE_SELECT_IMAGE); // 앨범으로 이동할때 각 버튼에 대한 int값(getId())을 던져주고 사진을 선택하면 onActivityResult()콜백
                break;
            case R.id.btn_text_jooungo_updateimg2 :  //두번쨰 사진추가 클릭
                startActivityForResult(getImg(v.getId()), REQ_CODE_SELECT_IMAGE);
                break;
            case R.id.btn_text_jooungo_updateimg3 :  //세번쨰 사진추가 클릭
                startActivityForResult(getImg(v.getId()), REQ_CODE_SELECT_IMAGE);
                break;

            case R.id.imageview_jooungo_update_clearimg1 :  // 첫번째 사진 삭제 버튼
                updateimgLayout.setAnimation(animation);
                if(info.size() == 1){
                    Frame2.setVisibility(View.INVISIBLE);
                    clear1.setVisibility(View.INVISIBLE);
                    updateimg1.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.ic_add));
                    updateimg1.setImageBitmap(null);
                    info.setImg1(null);
                }else if(info.size() == 2){
                    Frame3.setVisibility(View.INVISIBLE);
                    clear2.setVisibility(View.INVISIBLE);
                    updateimg1.setImageBitmap(getImgbitmap(updateimg2));
                    updateimg2.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.ic_add));
                    updateimg2.setImageBitmap(null);
                    info.setImg1(info.getimg(1));
                    info.setImg2(null);
                }else if(info.size() == 3){
                    clear3.setVisibility(View.INVISIBLE);
                    updateimg1.setImageBitmap(getImgbitmap(updateimg2));
                    updateimg2.setImageBitmap(getImgbitmap(updateimg3));
                    updateimg3.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.ic_add));
                    updateimg3.setImageBitmap(null);
                    info.setImg1(info.getimg(1));
                    info.setImg2(info.getimg(2));
                    info.setImg3(null);
                }
                break;
            case R.id.imageview_jooungo_update_clearimg2 :  // 두번째 사진 삭제 버튼
                updateimgLayout.setAnimation(animation);
                if(info.size() == 2){
                    Frame3.setVisibility(View.INVISIBLE);
                    clear2.setVisibility(View.INVISIBLE);
                    updateimg2.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.ic_add));
                    updateimg2.setImageBitmap(null);
                    info.setImg2(null);
                }else if(info.size() == 3){
                    updateimg2.setImageBitmap(getImgbitmap(updateimg3));
                    updateimg3.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.ic_add));
                    updateimg3.setImageBitmap(null);
                    clear3.setVisibility(View.INVISIBLE);
                    info.setImg2(info.getimg(2));
                    info.setImg3(null);
                }
                break;
            case R.id.imageview_jooungo_update_clearimg3 :  // 세번째 사진 삭제 버튼
                updateimgLayout.setAnimation(animation);
                clear3.setVisibility(View.INVISIBLE);
                updateimg3.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.ic_add));
                updateimg3.setImageBitmap(null);
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
                        case R.id.btn_text_jooungo_updateimg1:
                            info.setImg1(galleryBitmap.getByteArray());
                            updateimg1.setImageBitmap(galleryBitmap.getBitmap());
                            clear1.setVisibility(View.VISIBLE);
                            Frame2.setVisibility(View.VISIBLE);
                            break;
                        case R.id.btn_text_jooungo_updateimg2:
                            info.setImg2(galleryBitmap.getByteArray());
                            updateimg2.setImageBitmap(galleryBitmap.getBitmap());
                            clear2.setVisibility(View.VISIBLE);
                            Frame3.setVisibility(View.VISIBLE);
                            break;
                        case R.id.btn_text_jooungo_updateimg3:
                            info.setImg3(galleryBitmap.getByteArray());
                            updateimg3.setImageBitmap(galleryBitmap.getBitmap());
                            clear3.setVisibility(View.VISIBLE);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //앨범으로 이동해서 사진 가져오기
    public Intent getImg(int getId){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        addimgIDVALUE= getId; //선택한 위젯

        return  intent;
    }
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

    //사진 이름 가져오기
    public String getImageNameToUri(Uri data) {

        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        imgPath = cursor.getString(column_index);
        imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        return imgName;
    }

    //이미지 비트맵 가져오기
    public Bitmap getImgbitmap(ImageView updateimg) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable)updateimg.getDrawable();
        return bitmapDrawable.getBitmap();
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
            //mProgress = ProgressDialog.show(this, "서버에 저장하는 중입니다.", "잠시만 기다려 주세요.");
            //mProgress.setCancelable(false);
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
                       // mProgress.dismiss();
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
                       // mProgress.dismiss();
                        Toast.makeText(getApplicationContext(),"서버저장에 실패함",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    //기존 게시판 이미지 뿌려주기 클래스
    private class Uitask extends Thread{
        String img1,img2,img3;
        int imgTotalcount = 0;
        int imgcount = 0;
        boolean ischeck = true;
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
                    mProgress = new ProgressDialog(JooungoUpdateActivity.this);
                    mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgress.setMessage("잠시만 기다려주세요 로딩중입니다.");
                    mProgress.setCancelable(false);
                    mProgress.show();
                    if( (img1.equals("null")) == false){
                        imgTotalcount = 1;
                    }if( (img2.equals("null")) == false){
                        imgTotalcount = 2;
                    }if( (img3.equals("null")) == false){
                        imgTotalcount = 3;
                    }if(imgTotalcount == 0){
                        mProgress.dismiss();
                    }
                    if( (img1.equals("null")) == false){
                        Glide.with(getApplicationContext())
                                .load(Url.ImgTake +img1)
                                .asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        GalleryBitmap galleryBitmap = new GalleryBitmap(resource);
                                        info.setImg1(galleryBitmap.getByteArray());
                                        updateimg1.setImageBitmap(galleryBitmap.getBitmap());
                                        updateimg1.setBackgroundDrawable(null);
                                        clear1.setVisibility(View.VISIBLE);
                                        Frame2.setVisibility(View.VISIBLE);
                                        imgcount++;

                                        if(imgTotalcount == imgcount){
                                            mProgress.dismiss();
                                        }
                                    }
                                });
                    }
                    if((img2.equals("null")) == false){
                        Glide.with(getApplicationContext())
                                .load(Url.ImgTake +img2)
                                .asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        GalleryBitmap galleryBitmap = new GalleryBitmap(resource);
                                        info.setImg2(galleryBitmap.getByteArray());
                                        updateimg2.setImageBitmap(galleryBitmap.getBitmap());
                                        updateimg2.setBackgroundDrawable(null);
                                        clear2.setVisibility(View.VISIBLE);
                                        Frame3.setVisibility(View.VISIBLE);
                                        imgcount++;

                                        if(imgTotalcount == imgcount){
                                            mProgress.dismiss();
                                        }
                                    }
                                });
                    }
                    if((img3.equals("null")) == false){
                        Glide.with(getApplicationContext())
                                .load(Url.ImgTake +img3)
                                .asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        GalleryBitmap galleryBitmap = new GalleryBitmap(resource);
                                        info.setImg3(galleryBitmap.getByteArray());
                                        updateimg3.setImageBitmap(galleryBitmap.getBitmap());
                                        updateimg3.setBackgroundDrawable(null);
                                        clear3.setVisibility(View.VISIBLE);
                                        imgcount++;

                                        if(imgTotalcount == imgcount){
                                            mProgress.dismiss();
                                        }
                                    }
                                });

                    }

                }
            });

        }
    }
}// finish class
