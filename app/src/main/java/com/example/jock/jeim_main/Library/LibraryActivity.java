package com.example.jock.jeim_main.Library;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.example.jock.jeim_main.Bottom.GongjiActivity;
import com.example.jock.jeim_main.Bottom.StudentfoodActivity;
import com.example.jock.jeim_main.Bottom.TimetableActivity;
import com.example.jock.jeim_main.Bottom.TotalserviceActivity;
import com.example.jock.jeim_main.Main.MainActivity;
import com.example.jock.jeim_main.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LibraryActivity extends AppCompatActivity implements AbsListView.OnScrollListener,
        View.OnClickListener,TextView.OnEditorActionListener{

    private Button spinner;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_main);

        // 검색 분류 부분 스피너 가져오기
        spinner = (Button) findViewById(R.id.Library_search_spinner);

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
        spinner.setOnClickListener(this);

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
            case R.id.Library_search_spinner :
                showChoiceDialog();
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
        groupvalue = spinner.getText().toString();
        InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);  // 키패드 입력창 설정
        immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);  // 키패드 입력창 보여주기 or 숨겨주기

        try {
            if(editvalue.equals("") || editvalue.length() == 0){
                Toast.makeText(getApplicationContext(),"검색 내용을 입력해주세요",Toast.LENGTH_SHORT).show();
            }else if(groupvalue.equals("선택")){
                Toast.makeText(getApplicationContext(),"검색 분류를 선택해주세요",Toast.LENGTH_SHORT).show();
            }else{
                result = new LibraryTask().execute(editvalue,groupvalue,String.valueOf(OFFSET),null).get();
                setText(result);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showChoiceDialog(){
        final String[] arraylist = getResources().getStringArray(R.array.Library);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final int[] selectedIndex = {0};
        dialog.setTitle("검색 분류를 선택하세요")
                .setSingleChoiceItems(arraylist, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedIndex[0] = which;
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedValue =  arraylist[selectedIndex[0]];
                        spinner.setText(selectedValue);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false).create().show();
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
                break;
            case R.id.bottom_schedule :
                Intent intent3 = new Intent(getApplicationContext(), TimetableActivity.class);
                startActivity(intent3);

                break;
            case R.id.bottom_total :
                Intent intent5 = new Intent(getApplicationContext(), TotalserviceActivity.class);
                startActivity(intent5);
                break;
        }
    }
}
