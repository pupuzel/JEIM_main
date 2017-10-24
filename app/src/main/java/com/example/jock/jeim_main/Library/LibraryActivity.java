package com.example.jock.jeim_main.Library;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jock.jeim_main.Bottom.GongjiActivity;
import com.example.jock.jeim_main.Main.MainActivity;
import com.example.jock.jeim_main.R;
import com.example.jock.jeim_main.Task.LibraryTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LibraryActivity extends AppCompatActivity implements AbsListView.OnScrollListener,
                                                                  View.OnClickListener,TextView.OnEditorActionListener{

    private Spinner spinner;
    private ArrayAdapter Spinneradapter;
    private EditText edit_library_search;
    private TextView txt_back;
    private Button btn_search;
    private ListView listView;
    private ProgressBar progressBar;

    private String editvalue,groupvalue,result,pagemin,pagemax;
    private List<LibraryNotice> NoticeList  = new ArrayList<LibraryNotice>();
    private LibraryAdapter adapter;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private boolean lastItemVisibleFlag = false;    // 리스트 스크롤이 마지막 셀(맨 바닥)로 이동했는지 체크할 변수
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수
    private final int OFFSET = 10;                  // 한 페이지마다 로드할 데이터 갯수.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_main);

        // 검색 분류 부분 스피너 가져오기
        spinner = (Spinner)findViewById(R.id.Library_search_spinner);
        Spinneradapter = ArrayAdapter.createFromResource(this, R.array.Library,android.R.layout.simple_spinner_item);
        spinner.setAdapter(Spinneradapter);

        // 각각 위젯들 find 연결시켜주기
        listView = (ListView) findViewById(R.id.Library_listview) ;
        edit_library_search = (EditText) findViewById(R.id.Library_search_edit);
        progressBar = (ProgressBar) findViewById(R.id.Library_progressbar);
        btn_search = (Button) findViewById(R.id.Library_search_btn);
        txt_back = (TextView) findViewById(R.id.Library_txt_back);

        btn_search.setOnClickListener(this);
        edit_library_search.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edit_library_search.setOnEditorActionListener(this);
        txt_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.Library_search_btn :  // 검색 버튼 클릭시
                    search();
                break;
            case R.id.Library_txt_back :
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                break;
        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
            search();
            return true;
        }
        return false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                result = new LibraryTask().execute(editvalue, groupvalue, String.valueOf(OFFSET), pagemax).get();
                addText(result);
            }catch (Exception e){}
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
        pagemax = String.valueOf(totalItemCount);

    }

    /*  리스트뷰 페이징 데이터 가져오기*/
    private void addText(String json){
        mLockListView = true;
        String code,title,writenm,publish,writedate;
        int count = 0;

        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject;

            while (count < jsonArray.length()) {
                jsonObject = jsonArray.getJSONObject(count);
                code = jsonObject.getString("도서코드");
                title = jsonObject.getString("도서제목");
                writenm = jsonObject.getString("도서저자");
                publish = jsonObject.getString("도서출판사");
                writedate = jsonObject.getString("도서출판일");
                NoticeList.add(new LibraryNotice(code,title,writenm,publish,writedate));
                count++;
            }
        } catch (JSONException e) {  e.printStackTrace();}

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                mLockListView = false;
            }
        },1000);

    }

    /*  리스트뷰 처음 데이터 가져오기*/
    private void setText(String json){
        String code,title,writenm,publish,writedate;
        int count = 0;
        try {
            jsonArray = new JSONArray(json);
            JSONObject jsonObject;
            NoticeList.clear();

            while (count < jsonArray.length()) {
                jsonObject = jsonArray.getJSONObject(count);
                code = jsonObject.getString("도서코드");
                title = jsonObject.getString("도서제목");
                writenm = jsonObject.getString("도서저자");
                publish = jsonObject.getString("도서출판사");
                writedate = jsonObject.getString("도서출판일");
                NoticeList.add(new LibraryNotice(code,title,writenm,publish,writedate));
                count++;
            }
            adapter = new LibraryAdapter(getApplicationContext() ,NoticeList);
            listView.setAdapter(adapter);
            listView.setOnScrollListener(this);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void search(){
        editvalue = edit_library_search.getText().toString();
        groupvalue = spinner.getSelectedItem().toString();
        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);  // 키패드 입력창 설정
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);  // 키패드 입력창 보여주기 or 숨겨주기

        try {
            result = new LibraryTask().execute(editvalue,groupvalue,String.valueOf(OFFSET),null).get();
            setText(result);
        }catch (Exception e){
            e.printStackTrace();
        }
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
