package com.example.jock.jeim_main.Main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.Another.CntDate;
import com.example.jock.jeim_main.Another.Pref;
import com.example.jock.jeim_main.Bottom.GongjiActivity;
import com.example.jock.jeim_main.Bottom.StudentfoodActivity;
import com.example.jock.jeim_main.Bottom.TimetableActivity;
import com.example.jock.jeim_main.Bottom.TotalserviceActivity;
import com.example.jock.jeim_main.Bus.BusActivity;
import com.example.jock.jeim_main.FAQ.FAQActivity;
import com.example.jock.jeim_main.Fiction.FictionActivity;
import com.example.jock.jeim_main.Food.FoodMainActivity;
import com.example.jock.jeim_main.Jooungo.JooungoActivity;
import com.example.jock.jeim_main.Library.LibraryActivity;
import com.example.jock.jeim_main.Major.MajorActivity;
import com.example.jock.jeim_main.R;
import com.example.jock.jeim_main.Another.CntTask;
import com.example.jock.jeim_main.ViewPager.AutoScrollAdapter;

import java.util.ArrayList;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class MainActivity extends AppCompatActivity implements
        OnClickListener,
        NavigationView.OnNavigationItemSelectedListener{
    private Menu drawer_menu;
    private MenuItem drawer_tools,drawer_mycontents;
    private Button btn_depart, btn_jooungo, btn_food, btn_bus ,btn_game ,btn_library;
    private TextView btnlist,drawer_login,drawer_join,drawer_logout,drawer_username;
    private AutoScrollViewPager autoViewPager;
    private ImageView drawericon;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private View headerview;
    private String prefUsername,prefUserID;

    private final int cnt = 1;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                /*이미지 슬라이드 이미지 추가*/
        ArrayList<String> data = new ArrayList<>(); //이미지 url를 저장하는 arraylist
        data.add("http://pupuzel.cafe24.com/img/main/activity.jpg");
        data.add("http://pupuzel.cafe24.com/img/main/comjung.jpg");
        data.add("http://pupuzel.cafe24.com/img/main/soft.jpg");
        data.add("http://pupuzel.cafe24.com/img/main/teacher.jpg");
        /*이미지 슬라이드*/
        autoViewPager = (AutoScrollViewPager)findViewById(R.id.autoViewPager);
        AutoScrollAdapter scrollAdapter = new AutoScrollAdapter(this, data);
        autoViewPager.setAdapter(scrollAdapter); //Auto Viewpager에 Adapter 장착
        autoViewPager.setInterval(3000); // 페이지 넘어갈 시간 간격 설정
        autoViewPager.startAutoScroll(); //Auto Scroll 시작

        /* 메인메뉴 버튼 find  */
        btn_depart = (Button) findViewById(R.id.main_btn_depart);
        btn_jooungo = (Button) findViewById(R.id.main_btn_jooungo);
        btn_food = (Button) findViewById(R.id.main_btn_food);
        btn_bus = (Button) findViewById(R.id.main_btn_map);
        btn_game = (Button) findViewById(R.id.main_btn_game);
        btn_library = (Button) findViewById(R.id.main_btn_library);

        /* 툴바 find */
        toolbar = (Toolbar) findViewById(R.id.includetoolbar);
        btnlist = (TextView) toolbar.findViewById(R.id.btnlist);

        /* Drawable 레이아웃 find */
        navigationView = (NavigationView) findViewById(R.id.nav_View);
        headerview = navigationView.getHeaderView(0);
        drawer_login = (TextView) headerview.findViewById(R.id.drawer_login);
        drawer_join  = (TextView) headerview.findViewById(R.id.drawer_Join);
        drawer_logout = (TextView) headerview.findViewById(R.id.drawer_logout);
        drawer_username = (TextView) headerview.findViewById(R.id.drawer_username);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer_menu = navigationView.getMenu();
        drawer_tools = drawer_menu.getItem(2);
        drawer_mycontents = drawer_menu.getItem(3);

        /* 액션바 툴바로 대체 */
        toolbar.setTitle("재능마당");
        setSupportActionBar(toolbar);

        /* 로그인 체크 */
        Pref.Login = getSharedPreferences("Login", Activity.MODE_PRIVATE);
        prefUsername = Pref.Login.getString("회원이름",null);
        prefUserID = Pref.Login.getString("회원아이디",null);
        LoginCheck(prefUsername);

        /* 방문자수 올리기 */
        CntCheck();

        /* 이벤트 리스너 연결 */
        btn_depart.setOnClickListener(this);
        btn_jooungo.setOnClickListener(this);
        btn_food.setOnClickListener(this);
        btn_bus.setOnClickListener(this);
        btn_game.setOnClickListener(this);
        btn_library.setOnClickListener(this);
        btnlist.setOnClickListener(this);
        drawer_login.setOnClickListener(this);
        drawer_logout.setOnClickListener(this);
        drawer_join.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.main_btn_depart :
                startActivity(new Intent(getApplicationContext(), MajorActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.main_btn_jooungo :
                startActivity(new Intent(getApplicationContext() , JooungoActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.main_btn_food :
                startActivity(new Intent(getApplicationContext() , FoodMainActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.main_btn_map :
                startActivity(new Intent(getApplicationContext() , BusActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.main_btn_game :
                startActivity(new Intent(getApplicationContext() , FictionActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.main_btn_library :
                startActivity(new Intent(getApplicationContext() , LibraryActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

         /* 로그인,회원가입,로그아웃 버튼 클릭 이벤트 */
        if(v == btnlist){
            drawerLayout.openDrawer(GravityCompat.END);
        }else if(v == drawer_login){
            startActivity(new Intent(getApplicationContext() , LoginActivity.class));
        }else if(v == drawer_logout){
            SharedPreferences.Editor editor = Pref.Login.edit();
            editor.clear();
            editor.commit();
            Toast.makeText(getApplicationContext(),"로그아웃 되었습니다",Toast.LENGTH_SHORT).show();
            Intent Mainintent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(Mainintent);
            finish();
        }else if(v == drawer_join){
            startActivity(new Intent(getApplicationContext() , JoinActivity.class));
        }
    }


    /* 드로어 네비게이션 아이템 버튼 클릭 이벤트*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.drawer_faq:
                Intent faq = new Intent(getApplicationContext(),FAQActivity.class);
                startActivity(faq);
                break;
            case R.id.drawer_tool:
                Intent tool = new Intent(getApplicationContext(),PwfindActivity.class);
                startActivity(tool);
                break;
            case R.id.drawer_mycontent :
                Intent mycontent = new Intent(getApplicationContext(),PwchangeActivity.class);
                mycontent.putExtra("회원아이디",prefUserID);
                startActivity(mycontent);
                break;
            case R.id.drawer_intro :
                Intent intro = new Intent(getApplicationContext(),IntroActivity.class);
                startActivity(intro);
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            long tempTime = System.currentTimeMillis();
            long intervalTime = tempTime - backPressedTime;
            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
            {
                super.onBackPressed();
                moveTaskToBack(true);

            }
            else
            {
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(), "한번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* 로그인 체크 메소드 */
    public void LoginCheck(String prefValue){

        if((prefValue == null) == true){
            drawer_login.setVisibility(View.VISIBLE);
            drawer_join.setVisibility(View.VISIBLE);
            drawer_username.setText("");
            drawer_logout.setVisibility(View.GONE);
            drawer_tools.setVisible(true);
            drawer_mycontents.setVisible(false);
        }else{
            drawer_login.setVisibility(View.GONE);
            drawer_join.setVisibility(View.GONE);
            drawer_username.setText(prefValue+" 님");
            drawer_username.setVisibility(View.VISIBLE);
            drawer_logout.setVisibility(View.VISIBLE);
            drawer_tools.setVisible(false);
            drawer_mycontents.setVisible(true);
        }
    }

    public void CntCheck(){
        Pref.Cnt = getSharedPreferences("CntCheck", Activity.MODE_PRIVATE);
        boolean isCheckCnted = Pref.Cnt.getBoolean("방문체크",false);
        CntDate cntDate = new CntDate();

        if(isCheckCnted == false){   //  오늘 첫방문 이라면

            cntDate.setNextDate(); // 다음 날짜 초기화해주기
            String Date = cntDate.getCntDate();  //DB에 저장해줄 날짜 가져오기
            String Cnt = String.valueOf(cnt);   // 방문자 횟수 1 가져오기
            new CntTask().execute(Date,Cnt);  // DB에 쏴주기

            SharedPreferences.Editor editor = Pref.Cnt.edit();
            editor.putBoolean("방문체크",true); // 방문 체크 true 변경
            editor.putString("초기화날짜",cntDate.getNextDate()); // 초기화 날짜 저장
            editor.commit();

            Toast.makeText(getApplicationContext(),"재능마당 첫 방문",Toast.LENGTH_SHORT).show();

        }else{   // 오늘 첫방문이 아니라면

            String NextDate = Pref.Cnt.getString("초기화날짜",null); // 초기화 날짜 가져오기
            long diifValue = cntDate.doDiffOfDate(NextDate);  // 현재 날짜와 비교한 값 가져오기

            if(diifValue < 0){  // 초기화 날짜를 앞지르면 초기화 해주기

                cntDate.setNextDate(); // 다음 날짜 초기화해주기
                String Date = cntDate.getCntDate();  //DB에 저장해줄 날짜 가져오기
                String Cnt = String.valueOf(cnt);   // 방문자 횟수 1 가져오기
                new CntTask().execute(Date,Cnt);  // DB에 쏴주기

                SharedPreferences.Editor editor = Pref.Cnt.edit();
                editor.putString("초기화날짜",cntDate.getNextDate()); // 초기화 날짜 저장
                editor.commit();

                Toast.makeText(getApplicationContext(),"오늘 첫 방문",Toast.LENGTH_SHORT).show();

            }else{
                //String msg = getResetTime(diifValue);
                //Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
            }
        }
    }

/*    public String getResetTime(long cnttime){
        String result;

        long hour = cnttime/3600;   // 남은 시간
        long remain = cnttime -(hour*3600); // hour 제외한 남은 초
        long minute = remain/60;  // 남은 분
        long second = remain -(minute*60); // 남은 초
        result = "다음 투데이 "+String.valueOf(hour)+":"+String.valueOf(minute)+":"+String.valueOf(second)+" 남음";

        return result;
    }*/

    /* 바텀바 컨트롤 메소드 */
    public void Bottom(View v){
        switch (v.getId()){
            case R.id.bottom_gongji :
                Intent intent = new Intent(getApplicationContext(),GongjiActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.bottom_food :
                Intent intent4 = new Intent(getApplicationContext(),StudentfoodActivity.class);
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

} // finish class
