package com.example.jock.jeim_main.Food;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jock.jeim_main.R;
import com.example.jock.jeim_main.Another.Url;

import org.json.JSONArray;
import org.json.JSONObject;

public class FoodDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private String result,code;
    private String title,group,address,phone,delivery,img;
    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    private final int FRAGMENT3 = 3;

    private Button bt_tab1, bt_tab2,bt_tab3;
    private TextView txt_back;
    private ImageView Mainimg;
    private Intent intent;

    private FoodTab1_Task task;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detailboard);
        // 위젯에 대한 참조
        bt_tab1 = (Button)findViewById(R.id.community_food_detail_btn_tab1);
        bt_tab2 = (Button)findViewById(R.id.community_food_detail_btn_tab2);
        bt_tab3 = (Button)findViewById(R.id.community_food_detail_btn_tab3);
        txt_back = (TextView) findViewById(R.id.community_food_detail_txt_back);

        Mainimg = (ImageView) findViewById(R.id.community_food_detail_img);

        // 탭 버튼에 대한 리스너 연결
        bt_tab1.setOnClickListener(this);
        bt_tab2.setOnClickListener(this);
        bt_tab3.setOnClickListener(this);
        txt_back.setOnClickListener(this);

        intent = getIntent();
        code = intent.getStringExtra("코드");
        task = new FoodTab1_Task();
        try {
            result = task.execute(code).get();
            new Task().execute(result);
        } catch (Exception e) {}
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.community_food_detail_btn_tab1:
                callFragment(FRAGMENT1);
                break;

            case R.id.community_food_detail_btn_tab2:
                callFragment(FRAGMENT2);
                break;

            case R.id.community_food_detail_btn_tab3:
                callFragment(FRAGMENT3);
                break;
            case R.id.community_food_detail_txt_back:
                startActivity(new Intent(getApplicationContext(),FoodMainActivity.class));
                finish();
                break;
        }
    }

    private void callFragment(int frament_no){
        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (frament_no){
            case 1:
                FoodTab1Activity foodTab1 = new FoodTab1Activity();
                Bundle bundle = new Bundle();
                bundle.putString("title",title);
                bundle.putString("group",group);
                bundle.putString("address",address);
                bundle.putString("phone",phone);
                bundle.putString("delivery",delivery);
                foodTab1.setArguments(bundle);
                transaction.replace(R.id.fragment_container, foodTab1);
                transaction.commit();
                break;

            case 2:
                FoodTab2Activity foodTab2 = new FoodTab2Activity();
                transaction.replace(R.id.fragment_container, foodTab2);
                transaction.commit();
                break;

            case 3:
                FoodTab3Activity foodTab3 = new FoodTab3Activity();
                Bundle bundle3 = new Bundle();
                bundle3.putString("code",code);
                foodTab3.setArguments(bundle3);
                transaction.replace(R.id.fragment_container, foodTab3);
                transaction.commit();
                break;
        }
    }

    class Task extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgress = new ProgressDialog(FoodDetailActivity.this);
            mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgress.setMessage("잠시만 기다려주세요 로딩중입니다.");
            mProgress.setCancelable(false);
            mProgress.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try{
                JSONArray jsonArray = new JSONArray(params[0]);
                JSONObject json = jsonArray.getJSONObject(0);

                    title = json.getString("제목");
                    group = json.getString("그룹");
                    address = json.getString("주소");
                    phone = json.getString("전화번호");
                    delivery = json.getString("배달유무");
                    img = json.getString("이미지");

            }catch (Exception e){
                e.printStackTrace();
            }
            return img;
        }

        @Override
        protected void onPostExecute(String imgname) {
            super.onPostExecute(imgname);

            Glide.with(getApplicationContext())
                 .load(Url.Main+Url.FoodTake+imgname)
                 //.diskCacheStrategy(DiskCacheStrategy.NONE)
                 //.skipMemoryCache(true)
                 .thumbnail(0.1f)
                 .into(Mainimg);
                  callFragment(FRAGMENT1);
            mProgress.dismiss();
        }
    }
}
