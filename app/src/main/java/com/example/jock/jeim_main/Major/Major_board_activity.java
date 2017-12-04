package com.example.jock.jeim_main.Major;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.Another.Pref;
import com.example.jock.jeim_main.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Major_board_activity extends AppCompatActivity implements View.OnClickListener,
                                                                       SwipeRefreshLayout.OnRefreshListener,
                                                                       AbsListView.OnScrollListener{
    private TextView txt_back,txt_newboard,txt_myboard;
    private ListView listView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipe;

    private int page = 0;
    private final int OFFSET = 0;                  // 한 페이지마다 로드할 데이터 갯수.
    private boolean lastItemVisibleFlag = false;    // 리스트 스크롤이 마지막 셀(맨 바닥)로 이동했는지 체크할 변수
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수

    private List<Major_boardNotice> noticeList = new ArrayList<Major_boardNotice>();
    private Major_boardAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.major_board_main);

        txt_back = (TextView) findViewById(R.id.department_board_back);
        txt_newboard = (TextView) findViewById(R.id.department_board_newboard);
        txt_myboard = (TextView) findViewById(R.id.department_board_myboard);
        listView = (ListView) findViewById(R.id.department_board_listview);
        swipe = (SwipeRefreshLayout) findViewById(R.id.department_board_swipe);
        progressBar = (ProgressBar) findViewById(R.id.department_board_progressbar);


        txt_back.setOnClickListener(this);
        txt_newboard.setOnClickListener(this);
        txt_myboard.setOnClickListener(this);
        swipe.setOnRefreshListener(this);
        listView.setOnScrollListener(this);

        try {
            String result = new Major_boardTask().execute(String.valueOf(OFFSET)).get();
            setDate(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.department_board_back :
                onBackPressed();
                break;
            case R.id.department_board_newboard :
                Pref.Login = getSharedPreferences("Login", Activity.MODE_PRIVATE);
                String userid =  Pref.Login.getString("회원아이디", null);

                if(userid != null) {
                    Intent intent = new Intent(getApplicationContext(), Major_board_newboard.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"로그인을 해야 작성할 수 있습니다.",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.department_board_myboard :

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRefresh() {
        try {
            String result = new Major_boardTask().execute(String.valueOf(OFFSET)).get();
            setDate(result);
        } catch (Exception e) { e.printStackTrace(); }
        swipe.setRefreshing(false);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                String result = new Major_boardTask().execute(String.valueOf(page)).get();
                addItem(result);
            }catch (Exception e){ e.printStackTrace();}

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
        page = totalItemCount;
    }

    public void setDate(String result){
        Pref.Login = getSharedPreferences("Login",Activity.MODE_PRIVATE);
        String username = Pref.Login.getString("회원이름",null);
        if(username != null){
            txt_myboard.setVisibility(View.VISIBLE);
            txt_myboard.setText(username);
        }
        noticeList.clear();
        try{
            JSONArray jsonArray = new JSONArray(result);
            JSONObject json;
            int count = 0;
            String userid,usernm,content,date,code,reviewcount;
            List<String> imglist;
            while( count < jsonArray.length()){
                imglist = new ArrayList<String>();

                json = jsonArray.getJSONObject(count);
                code = json.getString("게시판코드");
                reviewcount = json.getString("댓글개수");
                userid = json.getString("작성자학번");
                usernm = json.getString("작성자이름");
                content = json.getString("내용");
                date = json.getString("날짜");
                if(!json.getString("이미지1").equals("null")){ imglist.add(json.getString("이미지1"));}
                if(!json.getString("이미지2").equals("null")){ imglist.add(json.getString("이미지2"));}
                if(!json.getString("이미지3").equals("null")){ imglist.add(json.getString("이미지3"));}
                if(!json.getString("이미지4").equals("null")){ imglist.add(json.getString("이미지4"));}
                if(!json.getString("이미지5").equals("null")){ imglist.add(json.getString("이미지5"));}
                noticeList.add(new Major_boardNotice(code,userid,usernm,content,date,imglist,reviewcount));
                count++;
            }

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int displayheight = displayMetrics.heightPixels;// 세로
            int displaywidth = displayMetrics.widthPixels; // 가로

            adapter = new Major_boardAdapter(getApplicationContext(),noticeList,displayheight,displaywidth);
            listView.setAdapter(adapter);
            listView.setOnScrollListener(this);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void addItem(String result){
        // 리스트에 다음 데이터를 입력할 동안에 이 메소드가 또 호출되지 않도록 mLockListView 를 true로 설정한다.
        mLockListView = true;

        try{
            JSONArray jsonArray = new JSONArray(result);
            JSONObject json;
            int count = 0;
            String userid,usernm,content,date,code,reviewcount;
            List<String> imglist;
            while( count < jsonArray.length()){
                imglist = new ArrayList<String>();

                json = jsonArray.getJSONObject(count);
                code = json.getString("게시판코드");
                reviewcount = json.getString("댓글개수");
                userid = json.getString("작성자학번");
                usernm = json.getString("작성자이름");
                content = json.getString("내용");
                date = json.getString("날짜");
                if(!json.getString("이미지1").equals("null")){ imglist.add(json.getString("이미지1"));}
                if(!json.getString("이미지2").equals("null")){ imglist.add(json.getString("이미지2"));}
                if(!json.getString("이미지3").equals("null")){ imglist.add(json.getString("이미지3"));}
                if(!json.getString("이미지4").equals("null")){ imglist.add(json.getString("이미지4"));}
                if(!json.getString("이미지5").equals("null")){ imglist.add(json.getString("이미지5"));}
                noticeList.add(new Major_boardNotice(code,userid,usernm,content,date,imglist,reviewcount));
                adapter.notifyDataSetChanged();
                count++;
            }
            progressBar.setVisibility(View.GONE);
            mLockListView = false;
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
