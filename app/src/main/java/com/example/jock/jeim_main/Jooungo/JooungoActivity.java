package com.example.jock.jeim_main.Jooungo;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.GongjiActivity;
import com.example.jock.jeim_main.MainActivity;
import com.example.jock.jeim_main.R;
import com.example.jock.jeim_main.Url;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class JooungoActivity  extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences pref;
    private selectlist task;
    private ListView jooungo_listview,jooungo_listview_buy;
    private JooungoAdapter adapter;
    private List<JooungoNotice> jooungoNoticeList = new ArrayList<JooungoNotice>();
    private ArrayAdapter Spinneradapter;

    private TextView btn_jooungo_newboard,jooungo_backhome,btn_jooungo_search;
    private EditText edit_jooungo_search;
    private Button btnsell,btnbuy;
    private Spinner spinner;
    int clickColor,clickedColor;
    private int btnCheckValue = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_jooungo);

        spinner = (Spinner)findViewById(R.id.Jooungo_spiner_list);
        Spinneradapter = ArrayAdapter.createFromResource(this, R.array.Jooungo,android.R.layout.simple_spinner_item);
        Spinneradapter.setDropDownViewResource(R.layout.customer_spinner);
        spinner.setAdapter(Spinneradapter);


        // 각각 위젯들 네비게이션
        btn_jooungo_newboard = (TextView)findViewById(R.id.btn_jooungo_newboard);
        jooungo_backhome = (TextView) findViewById(R.id.jooungo_homeback);
        btn_jooungo_search = (TextView) findViewById(R.id.btn_jooungo_search);
        edit_jooungo_search = (EditText) findViewById(R.id.edit_jooungo_search);
        btnsell = (Button) findViewById(R.id.btn_sell);
        btnbuy = (Button) findViewById(R.id.btn_buy);

        jooungo_listview = (ListView) findViewById(R.id.jooungo_listview_sell);
        jooungo_listview_buy = (ListView) findViewById(R.id.jooungo_listview_buy);

        clickedColor= getResources().getColor(R.color.btncliked);
        clickColor = getResources().getColor(R.color.btnclike);

        //처음 실행시 팝니다가 먼저 보여지게 실행
        task = new selectlist();
        task.execute("1",null,null);

        btn_jooungo_newboard.setOnClickListener(this);
        btnsell.setOnClickListener(this);
        btnbuy.setOnClickListener(this);
        btn_jooungo_search.setOnClickListener(this);
        jooungo_backhome.setOnClickListener(this);

        // 리스트뷰안에 있는 아이템들 즉 게시판을 클릭했을때 이벤트
        jooungo_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // parent는 AdapterView의 속성의 모두 사용 할 수 있다.
                // String tv = (String)parent.getAdapter().getItem(position);
                //Toast.makeText(getApplicationContext(), tv, Toast.LENGTH_SHORT).show();

                // view는 클릭한 Row의 view를 Object로 반환해 준다.
               // LinearLayout contents= (LinearLayout) view.findViewById(R.id.layout_contents);
                //contents.setVisibility(View.VISIBLE);

                // Position 은 클릭한 Row의 position 을 반환해 준다.
               //Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();

                JooungoNotice item = (JooungoNotice) parent.getItemAtPosition(position);
                String str = item.getBoardcode();

                Intent popup = new Intent(getApplicationContext(), JooungoDetail.class);
                popup.putExtra("게시판코드",str);
                startActivity(popup);
            }
        });


    } // onCreate finish

    public void search() {
        String searchValue = edit_jooungo_search.getText().toString();
        String itemgroup = spinner.getSelectedItem().toString();
        String groupValue = String.valueOf(btnCheckValue);
        switch (itemgroup.toString()){
            case "제목":
                task = new selectlist();
                task.execute(groupValue,searchValue,itemgroup);
                break;
            case "제목+내용":
                task = new selectlist();
                task.execute(groupValue,searchValue,itemgroup);
                break;
            case "작성자":
                task = new selectlist();
                task.execute(groupValue,searchValue,itemgroup);
                break;

        }


    }
    @Override
    public void onClick(View v) {

         switch (v.getId()){
            case R.id.btn_jooungo_newboard:  //글 작성 버튼 클릭시
                pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);
                String prefUsername = pref.getString("회원이름",null);
                if((prefUsername == null) == true){
                    Toast.makeText(getApplicationContext(),"로그인을 해야만 게시글을 작성할수 있습니다.",Toast.LENGTH_LONG).show();
                }else{
                    Intent newboard = new Intent(getApplicationContext(), JooungoNewboard.class);
                    startActivity(newboard);
                }
                break;

            case R.id.btn_sell : // 팝니다 클릭시
                task = new selectlist();
                task.execute("1",null,null);
                btnbuy.setBackgroundColor(clickColor);
                btnsell.setBackgroundColor(clickedColor);
                btnCheckValue = 1;
                 break;

            case R.id.btn_buy : // 삽니다 클릭시
                task = new selectlist();
                task.execute("2",null,null);
                btnbuy.setBackgroundColor(clickedColor);
                btnsell.setBackgroundColor(clickColor);
                btnCheckValue = 2;
                break;

             case R.id.btn_jooungo_search : // 검색 버튼 클릭시
                 InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                 immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                 search();
                 break;

             case R.id.jooungo_homeback : // 뒤로가기 버튼 클릭시
                 Intent backhome = new Intent(getApplicationContext(), MainActivity.class);
                 startActivity(backhome);
                 finish();
                 break;

        };

    }

    /* 바텀바 컨트롤 메소드 */
    public void Bottom(View v){
        switch (v.getId()){
            case R.id.bottom_gongji :
                Intent intent = new Intent(getApplicationContext(),GongjiActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_home :
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

    // 게시판 이용을 위한 AsyncTask 쓰레드 클래스 사용
    class selectlist extends AsyncTask<String,Void,String>{
        String result,sendMsg;
        String searchValue;
        String itemgroup;
        String group;
                @Override
                protected String doInBackground(String... strings) {
                    group = strings[0];
                    if(strings[2] != null) {
                        itemgroup = strings[2].toString();
                        searchValue = strings[1].toString();

                        switch (itemgroup){
                            case "제목":
                                sendMsg = "group=" + group + "&title=" + searchValue;
                                Log.i("그룹",group);
                                Log.i("타이틀", searchValue);
                                break;
                            case "제목+내용":
                                sendMsg = "group=" + group + "&content=" + searchValue;
                                break;
                            case "작성자":
                                sendMsg = "group=" + group + "&usernm=" + searchValue;
                                break;
                        }

                    }else{
                        sendMsg = "group=" + group;
                    }


                    try {
                        URL url = new URL(Url.Main+Url.JooungoList);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);
                        conn.connect();
                        OutputStream osw = conn.getOutputStream();

                        osw.write(sendMsg.getBytes("UTF-8"));
                        osw.flush();
                        osw.close();

                        if (conn.getResponseCode() == conn.HTTP_OK) {

                            InputStream inputStream = conn.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            String Jsontext;
                            StringBuilder stringBuilder = new StringBuilder();
                            while ((Jsontext = bufferedReader.readLine()) != null) {

                                stringBuilder.append(Jsontext);

                            }
                            bufferedReader.close();
                            inputStream.close();
                            conn.disconnect();
                            result = stringBuilder.toString().trim();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    return result;
                } // doInBackground finish

                @Override
                public void onPostExecute(String result){
                    jooungoNoticeList.clear();
                    try{
                        JSONArray jsonArray = new JSONArray(result);
                        JSONObject json;
                        int count = 0;
                        String bd_code,username,title,date;
                        while( count < jsonArray.length()){

                        json = jsonArray.getJSONObject(count);
                            bd_code = json.getString("게시판코드");
                            username = json.getString("작성자");
                            title = json.getString("제목");
                            date = json.getString("날짜");
                            jooungoNoticeList.add(new JooungoNotice(bd_code,username,title,date));
                        count++;
                        }

                        adapter = new JooungoAdapter(getApplicationContext() , jooungoNoticeList);
                        jooungo_listview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }   //onPostExecute finish

    }   //class selectlist finish
}
