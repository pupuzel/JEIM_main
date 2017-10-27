package com.example.jock.jeim_main.Jooungo;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.Bottom.GongjiActivity;
import com.example.jock.jeim_main.Main.LoginActivity;
import com.example.jock.jeim_main.Main.MainActivity;
import com.example.jock.jeim_main.R;
import com.example.jock.jeim_main.Another.Url;

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

    private SharedPreferences pref; // 웹에서 Session과 비슷한 기능을 가지는 프리퍼런스 변수선언
    private selectlist task;    //  게시판 정보를 가져오기 위한 중첩클래스 변수 선언
    private ListView jooungo_listview; // 게시판 정보를 리스트 형식으로 가져오는 리스트뷰 변수 선언
    private JooungoAdapter adapter;   // 실제 View에 Model에 있는 데이터를 연동하기 위한 어뎁터 설정
    private List<JooungoNotice> jooungoNoticeList = new ArrayList<JooungoNotice>();  // 각 게시판을 리스트 배열에 담기 위한 변수 선언

    private Animation animation;

    // 각종 텍스트뷰 버튼 스피너 등등 필요한 위젯 선언
    private TextView btn_jooungo_newboard,jooungo_backhome,btn_jooungo_search;
    private EditText edit_jooungo_search;
    private Button btnsell,btnbuy,btnspinner;
    private ProgressDialog mProgress;

    int clickColor,clickedColor;  // 클릭했을때 컬러변경을위해 컬러값을 받기위한 변수선언
    private int btnCheckValue = 1; // 중고 게시판 그룹 팝니다 =1 삽니다 =2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jooungo_main);

        // 각각 위젯들 find 연결시켜주기
        btn_jooungo_newboard = (TextView)findViewById(R.id.btn_jooungo_newboard);
        jooungo_backhome = (TextView) findViewById(R.id.jooungo_homeback);
        btn_jooungo_search = (TextView) findViewById(R.id.btn_jooungo_search);
        edit_jooungo_search = (EditText) findViewById(R.id.edit_jooungo_search);
        btnspinner = (Button) findViewById(R.id.Jooungo_btn_spinner);
        btnsell = (Button) findViewById(R.id.btn_sell);
        btnbuy = (Button) findViewById(R.id.btn_buy);

        jooungo_listview = (ListView) findViewById(R.id.jooungo_listview_sell);

        // 컬러값을 정수형으로 가져와서 변수에 저장시키기
        clickedColor= getResources().getColor(R.color.btncliked);
        clickColor = getResources().getColor(R.color.btnclike);

        //처음 실행시 팝니다가 먼저 보여지게 실행
        task = new selectlist(); // 중첩클래스 인스턴스스
        task.execute("1",null,null); // 팝니다 = 1 삽니다 = 2 매개값으로 던져주기

        // 각각 온클릭 이벤트 리스너 연결시켜주기
        btn_jooungo_newboard.setOnClickListener(this);
        btnsell.setOnClickListener(this);
        btnbuy.setOnClickListener(this);
        btnspinner.setOnClickListener(this);
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
                animation = new AlphaAnimation(0.5f, 1.2f);
                animation.setDuration(2500);
                view.startAnimation(animation);

                JooungoNotice item = (JooungoNotice) parent.getItemAtPosition(position); //몇번째 아이템을 클릭했는지 그리고 그 아이템에 Model인 JooungoNotice에 연결
                String str = item.getBoardcode();  // 클릭한 해당 아이템 게시판코드값 가져오기
                Intent popup = new Intent(getApplicationContext(), JooungoDetailActivity.class);  // 게시판 자세히 보기 액티비티로 이동
                popup.putExtra("게시판코드",str); //게시판코드를 가지고 자세히 보기 액티비티로 이동
                startActivity(popup);  // 이동
            }
        });


    } // onCreate finish

    public void search() {   // 검색 메소드
        String searchValue = edit_jooungo_search.getText().toString();  // 검색 내용을 String 변수에 저장
        String itemgroup = btnspinner.getText().toString();   // 검색 분류(작성자,제목 등등...) 내용을 String 변수에 저장
        String groupValue = String.valueOf(btnCheckValue);        //팝니다 인지 삽니다 인지 구분하여 String 변수에 저장
        switch (itemgroup.toString()){   // 검색 분류에 따른 swich 구문 실행
            case "제목":
                task = new selectlist();   // 검색 내용을 서버에 보내주기위한 클래스 생성
                task.execute(groupValue,searchValue,itemgroup);  // 검생 내용을 서버로 전송
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

    public void showChoiceDialog(){
        final String[] arraylist = getResources().getStringArray(R.array.Jooungo);
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
                      btnspinner.setText(selectedValue);
                  }
              })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false).create().show();

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_jooungo_newboard:  //글 작성 버튼 클릭시
                pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);  //회원 세션값 가져오기
                String prefUsername = pref.getString("회원이름",null);  // 회원 이름값 가져오기
                if((prefUsername == null) == true){  // 로그인이 안되어있을때
                    Toast.makeText(getApplicationContext(),"로그인을 해야만 게시글을 작성할수 있습니다.",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }else{  //로그인이 되어있다면 글작성 액티비티로 이동
                    Intent newboard = new Intent(getApplicationContext(), JooungoNewboardActivity.class);
                    startActivity(newboard);
                }
                break;

            case R.id.btn_sell : // 팝니다 클릭시
                task = new selectlist();
                task.execute("1",null,null);  // 팝니다 코드인 1을 매개값으로 던져줌
                btnbuy.setBackgroundColor(clickColor);  //UI 처리
                btnsell.setBackgroundColor(clickedColor); //UI 처리
                btnCheckValue = 1; // 팝니다 버튼 클릭 체크값 설정
                break;

            case R.id.btn_buy : // 삽니다 클릭시
                task = new selectlist();
                task.execute("2",null,null);  // 삽니다 코드인 2을 매개값으로 던져줌
                btnbuy.setBackgroundColor(clickedColor);  //UI 처리
                btnsell.setBackgroundColor(clickColor);  //UI 처리
                btnCheckValue = 2; // 삽니다 버튼 클릭 체크값 설정
                break;

            case R.id.btn_jooungo_search : // 검색 버튼 클릭시
                InputMethodManager immhide = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);  // 키패드 입력창 설정
                immhide.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);  // 키패드 입력창 보여주기 or 숨겨주기
                search();  // 검색하기 메소드 실행
                break;

            case R.id.Jooungo_btn_spinner : // 스피너 버튼 클릭시
                showChoiceDialog();
                break;

            case R.id.jooungo_homeback : // 뒤로가기 버튼 클릭시 메인화면 이동
                Intent backhome = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backhome);
                finish();
                break;

        };

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
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

        // 필요한 값들을 받기위한 스트링 변수 선언
        String result,sendMsg;
        String searchValue;
        String itemgroup;
        String group;

        @Override
        protected void onPreExecute() {   // 제일 먼저 실행되는 메소드
            super.onPreExecute();
            mProgress = new ProgressDialog(JooungoActivity.this);
            mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgress.setMessage("잠시만 기다려주세요 로딩중입니다.");
            mProgress.setCancelable(false);
            mProgress.show();
        }

        // excute( 매개값) 에서 매개값들이 여기서 strings 배열 값으로 들어가면서  doInBackground() 가 실행이된다.
        @Override
        protected String doInBackground(String... strings) {
            group = strings[0];
            if(strings[2] != null) {
                itemgroup = strings[2].toString();
                searchValue = strings[1].toString();

                switch (itemgroup){  // 각각 itemgroup에 따른 POST 방식으로 보내줄 값들을 지정
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
                URL url = new URL(Url.Main+Url.JooungoList);  // JSP 서버 URL 선언
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();  // url 연결하기
                conn.setRequestMethod("POST");  // POST 방식으로 전송
                conn.setDoInput(true);  // 입력값 허용
                conn.setDoOutput(true); // 출력값 허용
                conn.connect();  // 연결
                OutputStream osw = conn.getOutputStream();

                Log.i("로그",sendMsg);
                osw.write(sendMsg.getBytes("UTF-8"));  // POST 방식에 문자열값을 바이트로 변환하여 보내주기
                osw.flush();  // 초기화
                osw.close();  // 닫기

                if (conn.getResponseCode() == conn.HTTP_OK) {  // 만약 서버와 연결이 성공한다면

                    InputStream inputStream = conn.getInputStream(); // 스트림을 통해 리턴값 가져오기
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // 스트림Reader를 BufferedReader 생성자 매개값으로 주기
                    String Jsontext;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((Jsontext = bufferedReader.readLine()) != null) { // 버퍼리더값이 NULL 될때까지 읽어서 스트링Builder에 저장하기

                        stringBuilder.append(Jsontext);

                    }
                    bufferedReader.close();  // bufferedReader닫기
                    inputStream.close();   // inputStream 닫기
                    conn.disconnect();   // HttpURLConnection 닫기
                    result = stringBuilder.toString().trim(); // 리턴값으로 줄 스트링 변수에 값 저장하기
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        } // doInBackground finish

        @Override
        public void onPostExecute(String result){ // doInBackground() 리턴값을 스트링 값으로 받아오는 메소드 UI처리를 할수있는 메소드이다
            jooungoNoticeList.clear();   // 리스트 초기화해주기
            try{
                JSONArray jsonArray = new JSONArray(result); // 리턴값을 제이손 배열로 읽어오기
                JSONObject json;  // 제이손 오브젝 선언
                int count = 0;  // 반복분을 위한 인트 변수 선언
                String bd_code,username,title,date;  // 값을 받아오기 위한 스트링 변수 선언
                while( count < jsonArray.length()){  // 제이손배열 length 값보다 작을때까지 반복

                    json = jsonArray.getJSONObject(count); // 제이손배열 각각 값을 제이손 오브젝에 저장

                    // 제이손에서 값들을 파싱하여 String 변수에 저장
                    bd_code = json.getString("게시판코드");
                    username = json.getString("작성자");
                    title = json.getString("제목");
                    date = json.getString("날짜").substring(0,16);

                    // 각각 게시판 정보들이 담긴 Model 클래스를 리스트배열에 저장하기
                    jooungoNoticeList.add(new JooungoNotice(bd_code,username,title,date));
                    count++;
                }

                adapter = new JooungoAdapter(getApplicationContext() , jooungoNoticeList); // 어뎁터 생성
                jooungo_listview.setAdapter(adapter);  // 리스트뷰에 어뎁터 설정하기
                adapter.notifyDataSetChanged();  // 어뎁터 값이 변경되면 알려주는 메소드
            }catch (Exception e){
                e.printStackTrace();
            }

            mProgress.dismiss();  // "잠시만 기다려 주세요" 로딩창 닫기
        }   //onPostExecute finish

    }   //class selectlist finish
}
