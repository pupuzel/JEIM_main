package com.example.jock.jeim_main.Fiction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jock.jeim_main.GongjiActivity;
import com.example.jock.jeim_main.MainActivity;
import com.example.jock.jeim_main.R;
import com.example.jock.jeim_main.Task.FictionTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FictionActivity extends AppCompatActivity implements View.OnClickListener,
                                                                  SwipeRefreshLayout.OnRefreshListener{
    private SharedPreferences pref;
    private SwipeRefreshLayout swipe;
    private EditText edit_content;
    private Button btn_check,btn_cencle;
    private String prefUsername;
    private String name,content,date;
    private List<FictionNotice> fictionNoticeList  = new ArrayList<FictionNotice>();
    private ListView listView;
    private FictionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiction);
        listView = (ListView) findViewById(R.id.fiction_listview);
        edit_content = (EditText) findViewById(R.id.fiction_edit_content);
        swipe = (SwipeRefreshLayout) findViewById(R.id.fiction_swipe);
        btn_check = (Button) findViewById(R.id.fiction_btn_check);
        btn_cencle = (Button) findViewById(R.id.fiction_btn_cancel);

        btn_check.setOnClickListener(this);
        btn_cencle.setOnClickListener(this);
        swipe.setOnRefreshListener(this);
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

                }else {  // 내용이 있다면

                    try {
                        String result = new FictionTask().execute(prefUsername, content).get();
                        setting(result);
                        edit_content.setText("");
                    } catch (Exception e) {}

                }
            } else {  //로그인이 안되어있다면
                Toast.makeText(getApplicationContext(), "로그인안됨", Toast.LENGTH_SHORT).show();
            }
            break;

            case R.id.fiction_btn_cancel :
                edit_content.setText("");
        }
    }

    @Override
    public void onRefresh() {

        try {
            String result = new FictionTask().execute("Refresh","Refresh").get();
            setting(result);
        } catch (Exception e) { e.printStackTrace(); }

        swipe.setRefreshing(false);
    }

    public String LoginCheck(){
        pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);
        prefUsername = pref.getString("회원아이디",null);

        return prefUsername;
    }

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
