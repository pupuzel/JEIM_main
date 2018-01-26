package com.example.jock.jeim_main.Major;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jock.jeim_main.Another.GalleryBitmap;
import com.example.jock.jeim_main.Another.Pref;
import com.example.jock.jeim_main.Another.UsedID;
import com.example.jock.jeim_main.R;

import java.util.ArrayList;
import java.util.List;


public class Major_board_newboard extends Activity implements View.OnClickListener {
    private int REQ_CODE_SELECT_IMAGE =200;
    private String imgPath,imgName;
    private Button btn_cancle,btn_cheak,btn_addimg;
    private EditText edit_content;
    private LinearLayout imglayout;
    private HorizontalScrollView horizontalScrollView;
    private List<ImageView> clearlist = new ArrayList<ImageView>();
    private List<FrameLayout> framlist = new ArrayList<FrameLayout>();
    private List<byte[]> imgbytelist = new ArrayList<byte[]>();
    private ProgressDialog mProgress;
    private ProgressBar progressBar;

    private Major_board_newboardNotice notice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.major_board_newboard);

        ActivityCompat.requestPermissions(this ,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},MODE_PRIVATE);

        /*  find  */
        btn_cancle = (Button) findViewById(R.id.department_newboard_cancle_btn);
        btn_cheak = (Button) findViewById(R.id.department_newboard_cheak_btn);
        btn_addimg = (Button) findViewById(R.id.department_newboard_addimg_btn);
        edit_content = (EditText) findViewById(R.id.department_newboard_content_edit);
        imglayout = (LinearLayout) findViewById(R.id.department_newboard_imglayout);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.department_newboard_scroll_imglayout);
        progressBar = (ProgressBar) findViewById(R.id.department_newboard_progressbar);

        btn_cancle.setOnClickListener(this);
        btn_cheak.setOnClickListener(this);
        btn_addimg.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.department_newboard_addimg_btn :  //사진 추가 클릭

                if(imgbytelist.size() >=  5){
                    Toast.makeText(Major_board_newboard.this,"사진은 최대 5장까지 첨부할 수 있습니다",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,REQ_CODE_SELECT_IMAGE);
                }

                break;

            case R.id.department_newboard_cheak_btn :
                addboard();
                break;
            case R.id.department_newboard_cancle_btn :
                finish();
                break;

        }
        for(int i=0;i < clearlist.size();i++){
            if(v.getId() == clearlist.get(i).getId()){
                imglayout.removeView(framlist.get(i));
                framlist.remove(i);
                clearlist.remove(i);
                imgbytelist.remove(i);
                break;
            }
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    String name_Str = getImageNameToUri(data.getData());
                    Toast.makeText(getBaseContext(),name_Str, Toast.LENGTH_SHORT).show();
                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    GalleryBitmap galleryBitmap = new GalleryBitmap(image_bitmap,imgPath);
                    setImgPath(galleryBitmap.getBitmap(),galleryBitmap.getByteArray());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void setImgPath(Bitmap imgbitmap,byte[] imgbyte) {

        FrameLayout frameLayout = new FrameLayout(this);
        LinearLayout.LayoutParams parm1 = new LinearLayout.LayoutParams(horizontalScrollView.getWidth()/3, horizontalScrollView.getHeight());
        frameLayout.setLayoutParams(parm1);
        imglayout.addView(frameLayout);
        framlist.add(frameLayout);

        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(imgbitmap);
        FrameLayout.LayoutParams parm2 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        parm2.setMargins(10,10,10,10);
        imageView.setLayoutParams(parm2);
        frameLayout.addView(imageView);

        ImageView clear = new ImageView(this);
        clear.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.bg_clear_circle));
        clear.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_clear));
        FrameLayout.LayoutParams parm3 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parm3.gravity = Gravity.RIGHT;
        clear.setLayoutParams(parm3);
        clear.setId(UsedID.departmentimg(clearlist.size()));
        frameLayout.addView(clear);
        clearlist.add(clear);
        clear.setOnClickListener(this);

        imgbytelist.add(imgbyte);
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

    public void addboard(){
        if(edit_content.getText().toString().length() == 0){
            Toast.makeText(Major_board_newboard.this,"내용을 입력해주세요",Toast.LENGTH_SHORT).show();
        }else if(imgbytelist.size() > 5){
            Toast.makeText(Major_board_newboard.this,"사진은 최대 5장까지 첨부할 수 있습니다",Toast.LENGTH_SHORT).show();
        }else{
            mProgress = ProgressDialog.show(this, "서버에 저장하는 중입니다.", "잠시만 기다려 주세요.",true);
            mProgress.setCancelable(false);
            new Task().start();
        }
    }

    private class Task extends Thread{
        String result;

        @Override
        public void run() {
            Pref.Login = getSharedPreferences("Login", Activity.MODE_PRIVATE);
            String userid =  Pref.Login.getString("회원아이디", null);
            String content = edit_content.getText().toString();
            notice = new Major_board_newboardNotice(userid,content,imgbytelist);

            try {
                result = new Major_board_newboardTask(mProgress).execute(notice).get();
            } catch (Exception e) {  e.printStackTrace(); }

            if (result.equals("success")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgress.dismiss();
                        Toast.makeText(getApplicationContext(),"게시글이 등록되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(getApplicationContext(),Major_board_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }else if(result.equals("fail")){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgress.dismiss();
                        Toast.makeText(getApplicationContext(),"저장할 수 없습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }


}
