package com.example.jock.jeim_main.Food;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jock.jeim_main.Another.Pref;
import com.example.jock.jeim_main.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FoodTab3Activity extends Fragment implements View.OnClickListener,
                                                  SwipeRefreshLayout.OnRefreshListener,
                                                  AbsListView.OnScrollListener{

    private Bundle bundle;
    private String foodjson;
    private View v;
    private ListView listView;
    private List<FoodTab3_Notice> NoticeList = new ArrayList<FoodTab3_Notice>();
    private Button btn_check;
    private EditText edit_content;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipe;
    private Context context;
    private FoodTab3_Adapter adapter;

    private boolean lastItemVisibleFlag = false;    // 리스트 스크롤이 마지막 셀(맨 바닥)로 이동했는지 체크할 변수
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수
    private String prefUsername;
    private String name,content,date,code;
    private int totalpage = 0;                       // 현재 총 페이징 갯수
    private final int OFFSET = 10;                  // 한 페이지마다 로드할 데이터 갯수.

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getArguments();
        code = bundle.getString("code");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.food_detail_tab3,container,false); //텝호스트 레이아웃 가리킴

        listView = (ListView) v.findViewById(R.id.food_review_listview);
        edit_content = (EditText) v.findViewById(R.id.food_review_edit_content);
        swipe = (SwipeRefreshLayout) v.findViewById(R.id.food_review_swipe);
        btn_check = (Button) v.findViewById(R.id.food_review_btn_check);
        progressBar = (ProgressBar) v.findViewById(R.id.food_review_progressbar);
        context = v.getContext();

        swipe.setOnRefreshListener(this);
        btn_check.setOnClickListener(this);

        onRefresh();
        return v;
    }

    // 리스트뷰 새로고침
    @Override
    public void onRefresh() {
        try {
            String result = new FoodTab3_Task(context).execute("Refresh",String.valueOf(OFFSET),null,code).get();
            setting(result);
        } catch (Exception e) { e.printStackTrace(); }
        swipe.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.food_review_btn_check :
                if (LoginCheck() != null) {   // 로그인이 되어있다면
                    String content = edit_content.getText().toString(); // 내용을 스트링 변수에 저장
                    String userID = LoginCheck();
                    if(content == null || content.equals("")){   //내용이 비어있다면

                        Toast.makeText(v.getContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }else {  // 내용이 있다면

                        try {
                            String result = new FoodTab3_Task(context).execute(userID,content,String.valueOf(OFFSET),code).get();
                            setting(result);
                            edit_content.setText("");
                        } catch (Exception e) {}

                    }
                } else {  //로그인이 안되어있다면
                    Toast.makeText(v.getContext(), "로그인을해주세요", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    // 리스트뷰 페이징
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                String result = new FoodTab3_Task(context).execute("Refresh", String.valueOf(OFFSET), String.valueOf(totalpage),code).get();
                addItem(result);
            }catch (Exception e){ e.printStackTrace();}

        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
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
                NoticeList.add(new FoodTab3_Notice(name, content, date));
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
        prefUsername = Pref.Login.getString("회원아이디",null);
        return prefUsername;
    }
    /*  리스트뷰에 데이터 뿌려주기 */
    public void setting(String jsontext) {
        NoticeList.clear();
        int count = 0;
        try {
            JSONArray jsonArray = new JSONArray(jsontext);
            JSONObject jsonObject;

            while (count < jsonArray.length()){

                jsonObject = jsonArray.getJSONObject(count);
                name = jsonObject.getString("회원이름");
                content = jsonObject.getString("내용");
                date = jsonObject.getString("날짜").substring(0,16);
                NoticeList.add(new FoodTab3_Notice(name,content,date));
                count++;
            }
            adapter = new FoodTab3_Adapter(v.getContext() , NoticeList);
            listView.setAdapter(adapter);
            listView.setOnScrollListener(this);
            adapter.notifyDataSetChanged();
        }catch (Exception e){}
    }
}
