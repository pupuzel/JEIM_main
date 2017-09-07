package com.example.jock.jeim_main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.FAQ.FAQActivity;
import com.example.jock.jeim_main.Jooungo.JooungoActivity;
import com.example.jock.jeim_main.Major.DepartmentActivity;

public class MainActivity extends AppCompatActivity {
    SharedPreferences pref;

    Menu drawer_menu;
    MenuItem drawer_mycontents;
    Button bottom_gongji;
    Button college,community,campus;
    Button btn_campus_bus;
    Button btn_community_jooungo;
    Button btn_subject;
    LinearLayout collegemenu,communimenu,campusmenu, bottombar_layout;
    int collegecheak,communicheak,campuscheak;
    TextView btnlist,drawer_login,drawer_join,drawer_logout,drawer_username;
    ImageView drawericon;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    View headerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 바텀 버튼 객체 ID 컨트롤*/
       bottombar_layout = (LinearLayout) findViewById(R.id.bottomebar_layout);
        bottom_gongji = (Button)findViewById(R.id.bottom_gongji);
        /* XML 메인메뉴,버튼 객체ID  컨트롤 */
        college = (Button) findViewById(R.id.collegebtn);
        community = (Button) findViewById(R.id.communibtn);
        campus = (Button) findViewById(R.id.campusbtn);
        btn_subject = (Button) findViewById(R.id.subjectbtn);
        collegemenu = (LinearLayout) findViewById(R.id.collegemenu);
        communimenu = (LinearLayout) findViewById(R.id.communitymenu);
        campusmenu = (LinearLayout) findViewById(R.id.campusmanu);

        btn_campus_bus = (Button)findViewById(R.id.btn_campus_bus);
        btn_community_jooungo = (Button)findViewById(R.id.btn_community_jooungo);
        /* XML 툴바 ID 컨트롤 */
        toolbar = (Toolbar) findViewById(R.id.includetoolbar);
        btnlist = (TextView) toolbar.findViewById(R.id.btnlist);
        /* 드로우 레이아웃 ID컨트롤 */
        navigationView = (NavigationView) findViewById(R.id.nav_View);
        headerview = navigationView.getHeaderView(0);
        drawer_login = (TextView) headerview.findViewById(R.id.drawer_login);
        drawer_join  = (TextView) headerview.findViewById(R.id.drawer_Join);
        drawer_logout = (TextView) headerview.findViewById(R.id.drawer_logout);
        drawer_username = (TextView) headerview.findViewById(R.id.drawer_username);
        drawericon = (ImageView) headerview.findViewById(R.id.drawer_icon);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer_menu = navigationView.getMenu();
        drawer_mycontents = drawer_menu.getItem(4);
        /* 액션바 툴바로 대체 */
        toolbar.setTitle("재능대학교");
        setSupportActionBar(toolbar);

        Toast.makeText(getApplicationContext(),"즐",Toast.LENGTH_SHORT).show();
        /* 드로우  클릭 이벤트*/
        btnlist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnlist: drawerLayout.openDrawer(GravityCompat.END);
                        break;
                }
            }
        });


        /* 드로어 네비게이션 아이템 버튼 클릭 이벤트*/
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.drawer_faq:
                        Intent faq = new Intent(getApplicationContext(),FAQActivity.class);
                        startActivity(faq);
                        break;
                }
                return false;
            }
        });

        /* 로그인 프리퍼런스 이벤트 */
            pref = getSharedPreferences("Login", Activity.MODE_PRIVATE);
        String prefUsername = pref.getString("회원이름",null);
        if((prefUsername == null) == true){
            drawer_login.setVisibility(View.VISIBLE);
            drawer_join.setVisibility(View.VISIBLE);
            drawericon.setVisibility(View.GONE);
            drawer_username.setText("");
            drawer_logout.setVisibility(View.GONE);
            drawer_mycontents.setVisible(false);
        }else{
            drawer_login.setVisibility(View.GONE);
            drawer_join.setVisibility(View.GONE);
            drawericon.setVisibility(View.VISIBLE);
            drawer_username.setText(prefUsername+" 님");
            drawer_logout.setVisibility(View.VISIBLE);
            drawer_mycontents.setVisible(true);

        }

        /* 로그인,회원가입,로그아웃 버튼 클릭 이벤트 */
        drawer_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginintent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginintent);
            }
        });
        drawer_join.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent joinintent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(joinintent);
            }
        });
        drawer_logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(getApplicationContext(),"로그아웃 되었습니다",Toast.LENGTH_SHORT).show();
                Intent Mainintent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(Mainintent);
            }
        });

        /* 캠퍼스 레이아웃 버튼 컨트롤 */
        btn_campus_bus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent busintent = new Intent(getApplicationContext(), BusActivity.class);
                startActivity(busintent);
            }
        });

        /* 커뮤니티 레이아웃 버튼 컨트롤 */
        btn_community_jooungo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent joounintent = new Intent(getApplicationContext(), JooungoActivity.class);
                startActivity(joounintent);
            }
        });

        /* 학과메뉴 버튼 클릭 컨트롤*/
        btn_subject.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DepartmentActivity.class);
                startActivity(intent);
            }
        });
        /* 메인메뉴 열닫 코딩 */
        college.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = Integer.toString(collegecheak);
                if(str.equals("0")){
                    collegemenu.setVisibility(View.VISIBLE);
                    collegecheak = 1;
                    campuscheak = communicheak = 0;
                }else{
                    collegemenu.setVisibility(View.GONE);
                    collegecheak = 0;
                    communicheak = 0;
                    campuscheak = 0;
                }
                communimenu.setVisibility(View.GONE);
                campusmenu.setVisibility(View.GONE);
            }
        });
        community.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = Integer.toString(communicheak);
                collegemenu.setVisibility(View.GONE);
                if(str.equals("0")){
                    communimenu.setVisibility(View.VISIBLE);
                    communicheak = 1;
                    collegecheak = campuscheak =0;
                }else{
                    communimenu.setVisibility(View.GONE);
                    collegecheak = 0;
                    communicheak = 0;
                    campuscheak = 0;
                }
                campusmenu.setVisibility(View.GONE);
            }
        });
        campus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = Integer.toString(campuscheak);
                collegemenu.setVisibility(View.GONE);
                communimenu.setVisibility(View.GONE);
                if(str.equals("0")){
                    campusmenu.setVisibility(View.VISIBLE);
                    campuscheak = 1;
                    collegecheak = communicheak=0;
                }else{
                    campusmenu.setVisibility(View.GONE);
                    collegecheak = 0;
                    communicheak = 0;
                    campuscheak = 0;
                }
            }
        });

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

}
