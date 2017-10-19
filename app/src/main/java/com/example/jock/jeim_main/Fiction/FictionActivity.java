package com.example.jock.jeim_main.Fiction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jock.jeim_main.Bottom.GongjiActivity;
import com.example.jock.jeim_main.MainActivity;
import com.example.jock.jeim_main.R;
import com.example.jock.jeim_main.Task.FictionTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FictionActivity extends AppCompatActivity implements View.OnClickListener,
                                                                  SwipeRefreshLayout.OnRefreshListener,
                                                                  AbsListView.OnScrollListener{

    private SwipeRefreshLayout swipe;
    private EditText edit_content;
    private Button btn_check,btn_cencle;
    private ListView listView;
    private ProgressBar progressBar;

    private SharedPreferences pref;
    private List<FictionNotice> fictionNoticeList  = new ArrayList<FictionNotice>();
    private FictionAdapter adapter;

    private boolean lastItemVisibleFlag = false;    // 리스트 스크롤이 마지막 셀(맨 바닥)로 이동했는지 체크할 변수
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수
    private String prefUsername;
    private String name,content,date;
    private int totalpage = 0;                       // 현재 총 페이징 갯수
    private final int OFFSET = 10;                  // 한 페이지마다 로드할 데이터 갯수.

    private Animation shake;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fiction_main);
        listView = (ListView) findViewById(R.id.fiction_listview);
        edit_content = (EditText) findViewById(R.id.fiction_edit_content);
        swipe = (SwipeRefreshLayout) findViewById(R.id.fiction_swipe);
        btn_check = (Button) findViewById(R.id.fiction_btn_check);
        btn_cencle = (Button) findViewById(R.id.fiction_btn_cancel);
        progressBar = (ProgressBar) findViewById(R.id.fiction_progressbar);

        btn_check.setOnClickListener(this);
        btn_cencle.setOnClickListener(this);
        swipe.setOnRefreshListener(this);
        shake = AnimationUtils.loadAnimation(this,R.anim.shake);
        onRefresh();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fiction_btn_check :

            if (LoginCheck() != null) {   // 로그인이 되어있다면
                String content = edit_content.getText().toString(); // 내용을 스트링 변수에 저장
                if(content == null || content.equals("")){   //내용이 비어있다면

                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                    edit_content.startAnimation(shake);
                }else {  // 내용이 있다면

                    try {
                        String result = new FictionTask().execute(prefUsername, content,null).get();
                        setting(result);
                        edit_content.setText("");
                    } catch (Exception e) {}

                }
            } else {  //로그인이 안되어있다면
                Toast.makeText(getApplicationContext(), "로그인을해주세요", Toast.LENGTH_SHORT).show();
                btn_check.startAnimation(shake);
            }
            break;

            case R.id.fiction_btn_cancel :
                edit_content.setText("");
        }
    }

    // 리스트뷰 새로고침
    @Override
    public void onRefresh() {
        try {
            String result = new FictionTask().execute("Refresh",String.valueOf(OFFSET),null).get();
            setting(result);
        } catch (Exception e) { e.printStackTrace(); }
        swipe.setRefreshing(false);
    }
    // 리스트뷰 페이징
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 1. OnScrollListener.SCROLL_STATE_IDLE : 스크롤이 이동하지 않을때의 이벤트(즉 스크롤이 멈추었을때).
        // 2. lastItemVisibleFlag : 리스트뷰의 마지막 셀의 끝에 스크롤이 이동했을때.
        // 3. mLockListView == false : 데이터 리스트에 다음 데이터를 불러오는 작업이 끝났을때.
        // 1, 2, 3 모두가 true일때 다음 데이터를 불러온다.
       if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false) {
            progressBar.setVisibility(View.VISIBLE);
           try {
               String result = new FictionTask().execute("Refresh", String.valueOf(totalpage), String.valueOf(OFFSET)).get();
               addItem(result);
           }catch (Exception e){ e.printStackTrace();}

        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            // firstVisibleItem : 화면에 보이는 맨위 첫번째 리스트의 아이템 번호.
            // visibleItemCount : 화면 걸쳐 보이는 리스트 아이템의 갯수
            // totalItemCount :   현재 리스트 전체의 총 갯수
            // 리스트의 갯수가 0개 이상이고, 화면에 보이는 맨 하단까지의 아이템 갯수가 총 갯수보다 크거나 같을때.. 즉 리스트의 끝일때. true
            lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            totalpage = totalItemCount;
    }


    private void addItem(String json){
        // 리스트에 다음 데이터를 입력할 동안에 이 메소드가 또 호출되지 않도록 mLockListView 를 true로 설정한다.
        mLockListView = true;
        int count = 0;
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject;
                while (count < jsonArray.length()) {
                    jsonObject = jsonArray.getJSONObject(count);
                    name = jsonObject.getString("회원이름");
                    content = jsonObject.getString("내용");
                    date = jsonObject.getString("날짜").substring(0, 16);
                    fictionNoticeList.add(new FictionNotice(name, content, date));
                    count++;
                }

        } catch (Exception e) { e.printStackTrace(); }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                mLockListView = false;
            }
        },1000);

    }

    public String LoginCheck(){
        pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);
        prefUsername = pref.getString("회원아이디",null);
        return prefUsername;
    }


    /*  리스트뷰에 데이터 뿌려주기 */
    public void setting(String jsontext) {
            fictionNoticeList.clear();
            int count = 0;
        try {
            JSONArray jsonArray = new JSONArray(jsontext);
            JSONObject jsonObject;

            while (count < jsonArray.length()){
                jsonObject = jsonArray.getJSONObject(count);
                name = jsonObject.getString("회원이름");
                content = jsonObject.getString("내용");
                date = jsonObject.getString("날짜").substring(0,16);
                fictionNoticeList.add(new FictionNotice(name,content,date));
                count++;
            }
            adapter = new FictionAdapter(getApplicationContext() , fictionNoticeList);
            listView.setAdapter(adapter);
            listView.setOnScrollListener(this);
            adapter.notifyDataSetChanged();
        }catch (Exception e){}
    }


    /* 바텀바 컨트롤 메소드 */
    public void Bottom(View v){
        switch (v.getId()){
            case R.id.bottom_gongji :  // 공지 액티비티로 이동
                Intent intent = new Intent(getApplicationContext(),GongjiActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_home :   // 홈 액티비티로 이동
                Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.bottom_food :

                break;
            case R.id.bottom_schedule :

                break;
            case R.id.bottom_total :

                break;
        }
    }
}
