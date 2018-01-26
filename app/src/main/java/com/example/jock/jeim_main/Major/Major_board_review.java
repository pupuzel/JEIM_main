package com.example.jock.jeim_main.Major;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.Another.Pref;
import com.example.jock.jeim_main.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Major_board_review extends Activity implements View.OnClickListener,TextView.OnEditorActionListener{
    private TextView txt_back,txt_count;
    private EditText edit_content;
    private Button btn_cheak;
    private ListView listView;
    private LinearLayout layout;

    private String prefUsernum,boardCode;
    private int Height = 0;

    private Intent intent;

    private List<Major_board_reviewNotice> noticeList = new ArrayList<Major_board_reviewNotice>();
    private Major_board_reviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.major_board_review);

        /* find */
        layout = (LinearLayout) findViewById(R.id.major_board_review_layout);
        txt_back = (TextView) findViewById(R.id.major_board_review_back);
        txt_count = (TextView) findViewById(R.id.major_board_review_txt_count);
        edit_content = (EditText) findViewById(R.id.major_board_review_edit_content);
        btn_cheak = (Button) findViewById(R.id.major_board_review_btn_cheak);
        listView = (ListView) findViewById(R.id.major_board_review_listview);

        txt_back.setOnClickListener(this);
        btn_cheak.setOnClickListener(this);
        edit_content.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edit_content.setOnEditorActionListener(this);

        intent = getIntent();
        boardCode = intent.getStringExtra("게시판코드");

        try {
            String result = new Major_board_reviewTask().execute(prefUsernum,"null",boardCode).get();
            setReview(result);
        } catch (Exception e) {}

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.major_board_review_back :
                onBackPressed();
                break;
            case R.id.major_board_review_btn_cheak :
                addreview();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Height = layout.getHeight();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
            addreview();
            return true;
        }
        return false;
    }

    public void addreview(){
        Pref.Login = getSharedPreferences("Login", Activity.MODE_PRIVATE);
        prefUsernum = Pref.Login .getString("회원아이디","");
        if(prefUsernum != ""){
            String content = edit_content.getText().toString();
            if(content == null || content.equals("")){   //내용이 비어있다면
                Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
            }else {  // 내용이 있다면
                try {                                                     //작성자ID,내용,게시판코드,알람설정
                    String result = new Major_board_reviewTask().execute(prefUsernum,content,boardCode).get();
                    setReview(result);
                    edit_content.setText("");
                } catch (Exception e) {}

                InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);  // 키패드 입력창 설정
                immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);  // 키패드 입력창 보여주기 or 숨겨주기
            }
        }else{
            Toast.makeText(getApplicationContext(), "로그인을해주세요", Toast.LENGTH_SHORT).show();
        }
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
                noticeList.add(new Major_board_reviewNotice(name,content,date));
                count++;
            }
            ischeakHeight();
            adapter = new Major_board_reviewAdapter(this,noticeList);
            listView.setAdapter(adapter);
            txt_count.setText("댓글 "+String.valueOf(count)+"개");
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void ischeakHeight(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayheight = displayMetrics.heightPixels;// 세로
        int defaultheight = (int) (displayheight*0.7);
        if(Height > defaultheight){
            LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, defaultheight);
            listView.setLayoutParams(parm);
        }

    }
}
