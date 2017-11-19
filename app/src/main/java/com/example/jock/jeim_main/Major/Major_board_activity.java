package com.example.jock.jeim_main.Major;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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


public class Major_board_activity extends AppCompatActivity implements View.OnClickListener
                                                                      {
    private TextView txt_back,txt_newboard;
    private ListView listView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipe;
    private Major_boardTask task;
    private String result;
    private int page = 0;

    private List<Major_boardNotice> noticeList = new ArrayList<Major_boardNotice>();
    private Major_boardAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.major_board_main);
        txt_back = (TextView) findViewById(R.id.department_board_back);
        txt_newboard = (TextView) findViewById(R.id.department_board_newboard);
        listView = (ListView) findViewById(R.id.department_board_listview);
        swipe = (SwipeRefreshLayout) findViewById(R.id.department_board_swipe);
        progressBar = (ProgressBar) findViewById(R.id.department_board_progressbar);
        txt_back.setOnClickListener(this);
        txt_newboard.setOnClickListener(this);
        //swipe.setOnRefreshListener(this);

        try {
            task = new Major_boardTask();
            result = task.execute(String.valueOf(page)).get();
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
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

 /*   @Override
    public void onRefresh() {

    }*/

/*    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }*/

    public void setDate(String result){
        noticeList.clear();
        try{
            JSONArray jsonArray = new JSONArray(result);
            JSONObject json;
            int count = 0;
            String userid,usernm,content,date,img1,img2,img3,img4,img5;
            List<String> imglist;
            while( count < jsonArray.length()){
                imglist = new ArrayList<String>();

                json = jsonArray.getJSONObject(count);
                userid = json.getString("작성자학번");
                usernm = json.getString("작성자이름");
                content = json.getString("내용");
                date = json.getString("날짜");
                if(!json.getString("이미지1").equals("null")){ imglist.add(json.getString("이미지1"));}
                if(!json.getString("이미지2").equals("null")){ imglist.add(json.getString("이미지2"));}
                if(!json.getString("이미지3").equals("null")){ imglist.add(json.getString("이미지3"));}
                if(!json.getString("이미지4").equals("null")){ imglist.add(json.getString("이미지4"));}
                if(!json.getString("이미지5").equals("null")){ imglist.add(json.getString("이미지5"));}
                noticeList.add(new Major_boardNotice(userid,usernm,content,date,imglist));
                count++;
            }
            adapter = new Major_boardAdapter(getApplicationContext(),noticeList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
