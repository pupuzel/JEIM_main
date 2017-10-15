package com.example.jock.jeim_main.Food;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.R;
import com.example.jock.jeim_main.Task.FoodTask;
import com.example.jock.jeim_main.Url;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class FoodActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener{

    private TextView order,cancel,check;
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout bottom;
    private Button btn_foodmap;
    private ListView foodlistView;
    private ProgressDialog mProgress;

    private List<FoodNotice> foodNoticeList = new ArrayList<FoodNotice>();
    private FoodAdapter foodAdapter;
    private ArrayAdapter spinneradapter;
    public static Context context;

    private Spinner spinner;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_main);
        context = this;
        order = (TextView) findViewById(R.id.community_food_order);
        cancel = (TextView) findViewById(R.id.community_food_order_cancel);
        check = (TextView) findViewById(R.id.community_food_order_check);
        bottom = (LinearLayout) findViewById(R.id.bottom_sheet);
        btn_foodmap = (Button) findViewById(R.id.community_btn_food_map);
        foodlistView = (ListView) findViewById(R.id.community_food_listview);
        spinner = (Spinner)findViewById(R.id.spinner);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        order.setOnClickListener(this);
        cancel.setOnClickListener(this);
        check.setOnClickListener(this);
        btn_foodmap.setOnClickListener(this);
        foodlistView.setOnItemClickListener(this);
        spinner.setOnItemSelectedListener(this);



        spinneradapter = ArrayAdapter.createFromResource(this,R.array.Food,android.R.layout.simple_spinner_item);
        spinneradapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinneradapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.community_food_order :  // 정렬 버튼 클릭
                if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
                break;

            case R.id.community_food_order_cancel:  // 바텀시트 취소버튼
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;

            case R.id.community_food_order_check:  // 바턴시트 확인버튼
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;

            case R.id.community_btn_food_map :   // 지도 버튼
                startActivity(new Intent(getApplicationContext(),FoodMap.class));
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String Selectgroup = spinner.getSelectedItem().toString();
        FoodTask foodTask = new FoodTask(context);
        try{
            foodTask.execute();
        }catch (Exception e){ e.printStackTrace(); }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FoodNotice item = (FoodNotice) parent.getItemAtPosition(position); //몇번째 아이템을 클릭했는지 그리고 그 아이템에 Model인 JooungoNotice에 연결
        String str = item.getCode(); // 클릭한 해당 아이템 게시판코드값 가져오기
        Intent intent = new Intent(getApplicationContext(),FoodDetail.class);
        intent.putExtra("코드",str);
        startActivity(intent);
    }

    // 맛집 목록을 불러오는 클래스
    class Foodlist extends AsyncTask<Void,Void,String> {
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = ProgressDialog.show(FoodActivity.this, "목록을 불러오고있습니다", "잠시만 기다려 주세요.");
            mProgress.setCancelable(false);
        }

        @Override
        protected String doInBackground(Void... params) {
            try{
                URL url = new URL(Url.Main+Url.Food);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //해당 url에 접속할수 있도록
                InputStream inputStream = httpURLConnection.getInputStream();        //넘어오는 결과값들을 그대로 받아올수있도록
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));    // inputStream에 값을 읽을수 있도록
                String Jsontext;
                StringBuilder stringBuilder = new StringBuilder();
                while ((Jsontext = bufferedReader.readLine()) != null){

                    stringBuilder.append(Jsontext);

                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                result = stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();
            }


            return result;
        } // doInBackground finish

        @Override
        public void onPostExecute(String result){
            foodNoticeList.clear();
            try{
                JSONArray jsonArray = new JSONArray(result);
                JSONObject json;
                int count = 0;
                String code,group,title,img,adress;
                while( count < jsonArray.length()){

                    json = jsonArray.getJSONObject(count);
                    code = json.getString("코드");
                    group = json.getString("그룹");
                    title = json.getString("제목");
                    img = json.getString("이미지");
                    adress = json.getString("주소");
                    foodNoticeList.add(new FoodNotice(code,title,adress,group,img));
                    count++;
                }
                foodAdapter = new FoodAdapter(getApplicationContext(),foodNoticeList);
                foodlistView.setAdapter(foodAdapter);
                foodAdapter.notifyDataSetChanged();
                mProgress.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
        }   //onPostExecute finish

    }   //class finish
}