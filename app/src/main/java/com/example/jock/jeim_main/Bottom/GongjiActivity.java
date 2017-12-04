package com.example.jock.jeim_main.Bottom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.Main.MainActivity;
import com.example.jock.jeim_main.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.animation;


public class GongjiActivity extends AppCompatActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    private TextView txt_back;
    private ListView listView;
    private SwipeRefreshLayout swipe;
    private List<GongjiNotice> NoticeList  = new ArrayList<GongjiNotice>();
    private GongjiAdater adapter;

    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_gongji);

        txt_back = (TextView) findViewById(R.id.bottom_gongji_back);
        listView = (ListView) findViewById(R.id.bottom_gongji_listview);
        swipe = (SwipeRefreshLayout) findViewById(R.id.bottom_gongji_swipe);

        txt_back.setOnClickListener(this);
        swipe.setOnRefreshListener(this);
        onRefresh();

        animation = new AlphaAnimation(0.5f, 1.2f);
        animation.setDuration(2500);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.startAnimation(animation);

                GongjiNotice item = (GongjiNotice) parent.getItemAtPosition(position);
                String str = item.getBoardcode();
                Intent intent = new Intent(getApplicationContext(), GongjiDetailActivity.class);
                intent.putExtra("코드",str);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_gongji_back :
                onBackPressed();
                break;
        }
    }

    @Override
    public void onRefresh() {
        try {
            String result = new GongjiTask().execute().get();
            setting(result);
        } catch (Exception e) { e.printStackTrace(); }
        swipe.setRefreshing(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /*  리스트뷰에 데이터 뿌려주기 */
    public void setting(String jsontext) {
        NoticeList.clear();
        int count = 0;
        try {
            JSONArray jsonArray = new JSONArray(jsontext);
            JSONObject jsonObject;
            String code,name,title,date;
            while (count < jsonArray.length()){
                jsonObject = jsonArray.getJSONObject(count);
                code = jsonObject.getString("코드");
                name = jsonObject.getString("이름");
                title = jsonObject.getString("제목");
                date = jsonObject.getString("날짜").substring(0,16);
                NoticeList.add(new GongjiNotice(code,"관리자",title,date));
                count++;
            }
            adapter = new GongjiAdater(getApplicationContext() ,NoticeList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }catch (Exception e){}
    }

    /* 바텀바 컨트롤 메소드 */
    public void Bottom(View v){
        switch (v.getId()){
            case R.id.bottom_gongji :
                Intent intent = new Intent(getApplicationContext(),GongjiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.bottom_home :
                Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
                break;
            case R.id.bottom_food :
                Intent intent4 = new Intent(getApplicationContext(),StudentfoodActivity.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent4);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.bottom_schedule :
                Intent intent3 = new Intent(getApplicationContext(), TimetableActivity.class);
                startActivity(intent3);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.bottom_total :
                Intent intent5 = new Intent(getApplicationContext(), TotalserviceActivity.class);
                intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent5);
                break;
        }
    }
}
