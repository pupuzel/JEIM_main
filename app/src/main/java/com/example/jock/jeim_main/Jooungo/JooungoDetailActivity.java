package com.example.jock.jeim_main.Jooungo;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.jock.jeim_main.Another.GalleryBitmap;
import com.example.jock.jeim_main.R;
import com.example.jock.jeim_main.Another.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class JooungoDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences pref;
    private ProgressBar progressBar;
    private ProgressDialog mProgress;
    private Bitmap bitmapimg1,bitmapimg2,bitmapimg3;
    private Intent intent;
    private JooungoImgTask imgTask;
    private NumberFormat nf;
    private JSONObject imglistJson = new JSONObject();
    private JooungoDeleteTask deleteTask;
    private TextView txt_title,txt_username,txt_date,txt_price,txt_content,txt_group,txt_loding,txt_review,txt_newreview;
    private ImageView imageView1,imageView2,imageView3;
    private Button btn_delete,btn_update,btn_backlist,btn_completed;

    private List<JooungoDetailreviewNotice> noticeList = new ArrayList<JooungoDetailreviewNotice>();
    private JooungoDetailreviewAdapter adapter;
    private ListView listView;

    private int price,groupvalue;
    private String usernum,prefUsernum,boardCode,img1,img2,img3;

    private imgThread imgThread;
    private setJooungoDetail setJooungoDetail;
    private JooungoDetailTask jooungoDetailTask;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jooungo_detailboard);

        nf  = NumberFormat.getInstance();
        nf.setMaximumIntegerDigits(10);

        txt_title = (TextView) findViewById(R.id.txt_jooungo_detail_title);
        txt_username = (TextView) findViewById(R.id.txt_jooungo_detail_username);
        txt_date = (TextView) findViewById(R.id.txt_jooungo_detail_date);
        txt_price = (TextView) findViewById(R.id.txt_jooungo_detail_price);
        txt_content = (TextView) findViewById(R.id.txt_jooungo_detail_content);
        txt_group = (TextView) findViewById(R.id.txt_jooungo_detail_group);
        txt_loding = (TextView) findViewById(R.id.txt_jooungo_detail_lodingtxt);
        txt_newreview = (TextView) findViewById(R.id.jooungo_detail_txt_newreview);
        txt_review = (TextView) findViewById(R.id.jooungo_detail_txt_review);
        btn_delete = (Button) findViewById(R.id.btn_jooungo_delete);
        btn_update = (Button) findViewById(R.id.btn_jooungo_update);
        btn_backlist = (Button) findViewById(R.id.btn_jooungo_backlist);
        btn_completed = (Button) findViewById(R.id.btn_jooungo_detail_completed);
        imageView1 = (ImageView) findViewById(R.id.txt_jooungo_detail_img1);
        imageView2 = (ImageView) findViewById(R.id.txt_jooungo_detail_img2);
        imageView3 = (ImageView) findViewById(R.id.txt_jooungo_detail_img3);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_jooungo_detail);
        listView = (ListView) findViewById(R.id.jooungo_detail_listview);

        try{
            pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);
            prefUsernum = pref.getString("회원아이디",null);

            intent = getIntent();
            boardCode = intent.getStringExtra("게시판코드");
            mProgress = new ProgressDialog(JooungoDetailActivity.this);

            jooungoDetailTask = new JooungoDetailTask(mProgress);
            setJooungoDetail = new setJooungoDetail();
            String result = jooungoDetailTask.execute(boardCode).get();
            setJooungoDetail.execute(result);


        }catch (Exception e){   e.printStackTrace();  }

        btn_delete.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_backlist.setOnClickListener(this);
        btn_completed.setOnClickListener(this);
        txt_newreview.setOnClickListener(this);
    } // onCreate finish


    // 리스트뷰 아이템 갯수에 맞게 높이 조절해주는 메소드
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_jooungo_delete :  // 삭제버튼 클릭
                deletecheck();
                break;

            case R.id.btn_jooungo_update :  // 수정버튼 클릭
                updatecheck();
                break;

            case R.id.btn_jooungo_backlist : // 뒤로가기 버튼 클릭
                Intent intent = new Intent(getApplicationContext(),JooungoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_jooungo_detail_completed :  // 완료 버튼 클릭
                completedCheck();
                break;

            case R.id.jooungo_detail_txt_newreview :  // 댓글 작성 버튼 클릭
                reviewtext();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        try{
            jooungoDetailTask.wait();
            setJooungoDetail.wait();
        }catch (Exception e) {
            e.printStackTrace();
        }

        super.onBackPressed();
    }

    /*  리스트뷰에 데이터 뿌려주기 */
    public void setReview(String jsontext) {
        noticeList.clear();
        int count = 0;
        String name,content,date;
        try {
            JSONArray jsonArray = new JSONArray(jsontext);
            JSONObject jsonObject;
            while (count < jsonArray.length()){

                jsonObject = jsonArray.getJSONObject(count);
                name = jsonObject.getString("회원이름");
                content = jsonObject.getString("내용");
                date = jsonObject.getString("날짜").substring(0,16);
                noticeList.add(new JooungoDetailreviewNotice(name,content,date));
                count++;
            }
            adapter = new JooungoDetailreviewAdapter(this,noticeList);
            listView.setAdapter(adapter);
            setListViewHeightBasedOnChildren(listView);
            txt_review.setText("댓글 "+String.valueOf(count));
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void reviewtext(){
        final EditText edittext = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("댓글 입력");
        builder.setView(edittext);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if(prefUsernum != null){
                            String content = edittext.getText().toString();
                            if(content == null || content.equals("")){   //내용이 비어있다면
                                Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                            }else {  // 내용이 있다면
                                try {
                                    String result = new JooungoDetailreviewTask().execute(prefUsernum,content,boardCode).get();
                                    setReview(result);
                                } catch (Exception e) {}

                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "로그인을해주세요", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    private void deletecheck(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("정말 게시글을 삭제하시겠습니까?");
        builder.setPositiveButton("확인",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    imglistJson.put("게시판코드",boardCode);
                    deleteTask = new JooungoDeleteTask();
                    String result = deleteTask.execute(imglistJson).get();
                    if(result.equals("success")){
                        Intent intent = new Intent(getApplicationContext(),JooungoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),boardCode,Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("취소",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void completedCheck(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(groupvalue == 1){
            builder.setTitle("판매 완료로 등록하시겠습니까?\n 다시 되돌릴수없습니다. ");
        }else {
            builder.setTitle("구매 완료로 등록하시겠습니까?\n 다시 되돌릴수없습니다. ");
        }
        builder.setPositiveButton("확인",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String result = new JooungoCompletedTask().execute(boardCode).get();
                    if(result.equals("success")){
                        Intent intent = new Intent(getApplicationContext(),JooungoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"판매 완료등록을 할수없습니다 (서버에러)",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"판매 완료등록을 할수없습니다 (서버에러)",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("취소",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    private void updatecheck(){
        Intent intent = new Intent(getApplicationContext(),JooungoUpdateActivity.class);
        intent.putExtra("게시판코드",boardCode);
        intent.putExtra("제목",txt_title.getText().toString());
        intent.putExtra("내용",txt_content.getText().toString());
        intent.putExtra("가격",price);
        intent.putExtra("분류",groupvalue);
        intent.putExtra("이미지1",img1);
        intent.putExtra("이미지2",img2);
        intent.putExtra("이미지3",img3);
        startActivity(intent);
    }

    class setJooungoDetail extends AsyncTask<String,Void,String>{
        String reviewjson;
        JSONArray jsonArray;
        JSONObject json;
        @Override
        protected String doInBackground(String... params) {
            return params[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                jsonArray = new JSONArray(s);
                json = jsonArray.getJSONObject(0);

                img1 = json.getString("이미지1");
                img2= json.getString("이미지2");
                img3 = json.getString("이미지3");
                usernum = json.getString("작성자학번");
                txt_title.setText(json.getString("제목"));
                txt_content.setText(json.getString("내용"));
                txt_username.setText(json.getString("작성자"));
                txt_date.setText(json.getString("날짜").substring(0, 16));
                price = Integer.parseInt(json.getString("가격"));
                String pricefmt = nf.format(price);

                txt_price.setText(pricefmt + "원");

                if (json.getString("그룹").equals("1")) {
                    groupvalue = 1;
                    txt_group.setText("[팝니다]");
                } else {
                    groupvalue = 2;
                    txt_group.setText("[삽니다]");
                }

                if (json.getString("상태").equals("1")) {

                    if (groupvalue == 1) {
                        btn_completed.setText("판매완료 등록");
                    } else {
                        btn_completed.setText("구매완료 등록");
                    }
                    btn_completed.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.click_cencle));
                    btn_completed.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                    if (prefUsernum != null) {
                        btn_delete.setVisibility(View.VISIBLE);
                        btn_update.setVisibility(View.VISIBLE);
                        btn_completed.setVisibility(View.VISIBLE);
                    }

                } else {
                    if (groupvalue == 1) {
                        btn_completed.setText("판매완료");
                    } else {
                        btn_completed.setText("구매완료");
                    }
                    btn_completed.setVisibility(View.VISIBLE);
                    btn_completed.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.click_cencle));
                    btn_completed.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                    btn_completed.setEnabled(false);
                }

                reviewjson = new JooungoDetailreviewTask().execute(prefUsernum, null, boardCode).get();
                setReview(reviewjson);

                imgThread = new imgThread(img1,img2,img3); //이미지 가져오는 쓰레드는 별도의 쓰레드가 작업
                imgThread.start();
                mProgress.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // 이미지를 가져오는 쓰레드
    class imgThread extends Thread{
        String Simg1,Simg2,Simg3 = null;
        int imgTotalcount = 0;
        int imgcount = 0;
        imgThread(String img1,String img2,String img3){
            this.Simg1 = img1;
            this.Simg2 = img2;
            this.Simg3 = img3;
        }

        @Override
        public void run() {
            try {
                if(Simg1 != "null"){
                    imgTotalcount = 1;
                }if(Simg2 != "null"){
                    imgTotalcount = 2;
                }if(Simg3 != "null"){
                    imgTotalcount = 3;
                }
                if (Simg1 != "null") {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txt_loding.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.VISIBLE);

                            Glide.with(getApplicationContext())
                                    .load(Url.Main + Url.ImgTake +Simg1)
                                    .asBitmap()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                            imageView1.setImageBitmap(resource);
                                            imageView1.setVisibility(View.VISIBLE);
                                            imgcount++;
                                            if(imgTotalcount == imgcount){
                                                txt_loding.setVisibility(View.GONE);
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }
                    });
                    imglistJson.put("이미지1",Simg1);
                    if(Simg2 != "null"){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(getApplicationContext())
                                        .load(Url.Main + Url.ImgTake +Simg2)
                                        .asBitmap()
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)
                                        .into(new SimpleTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                                imageView2.setImageBitmap(resource);
                                                imageView2.setVisibility(View.VISIBLE);
                                                imgcount++;
                                                if(imgTotalcount == imgcount){
                                                    txt_loding.setVisibility(View.GONE);
                                                    progressBar.setVisibility(View.GONE);
                                                }
                                            }
                                        });
                            }
                        });
                        imglistJson.put("이미지2",Simg2);
                        if (Simg3 != "null"){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(getApplicationContext())
                                            .load(Url.Main + Url.ImgTake +Simg1)
                                            .asBitmap()
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(new SimpleTarget<Bitmap>() {
                                                @Override
                                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                                    imageView3.setImageBitmap(resource);
                                                    imageView3.setVisibility(View.VISIBLE);
                                                    imgcount++;
                                                    if(imgTotalcount == imgcount){
                                                        txt_loding.setVisibility(View.GONE);
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                }
                                            });
                                }
                            });

                            imglistJson.put("이미지3",Simg3);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
