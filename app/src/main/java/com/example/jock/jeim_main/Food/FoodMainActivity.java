package com.example.jock.jeim_main.Food;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.Main.MainActivity;
import com.example.jock.jeim_main.R;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FoodMainActivity extends AppCompatActivity implements View.OnClickListener,
                                                               AdapterView.OnItemClickListener,
                                                               AdapterView.OnItemSelectedListener,CompoundButton.OnCheckedChangeListener{

    private TextView order,cancel,check,txt_back;
    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout bottom;
    private Button btn_foodmap;
    private ListView foodlistView;
    private CheckBox checkBox_delivery,checkBox_displace,checkBox_review;

    private List<FoodMainNotice> foodNoticeList = new ArrayList<FoodMainNotice>();
    private FoodMainAdapter foodAdapter;
    private ArrayAdapter spinneradapter;
    public static Context context;

    private Spinner spinner;

    private String type,delivery,ordervalue;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_main);

        context = this;
        type = delivery = ordervalue = "All";

        order = (TextView) findViewById(R.id.community_food_order);
        cancel = (TextView) findViewById(R.id.community_food_order_cancel);
        check = (TextView) findViewById(R.id.community_food_order_check);
        txt_back = (TextView) findViewById(R.id.community_food_txt_back);
        bottom = (LinearLayout) findViewById(R.id.bottom_sheet);
        btn_foodmap = (Button) findViewById(R.id.community_btn_food_map);
        foodlistView = (ListView) findViewById(R.id.community_food_listview);
        checkBox_delivery = (CheckBox) findViewById(R.id.community_food_order_box_delivery);
        checkBox_displace = (CheckBox) findViewById(R.id.community_food_order_box_distance);
        checkBox_review = (CheckBox) findViewById(R.id.community_food_order_box_review);

        spinner = (Spinner)findViewById(R.id.spinner);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        order.setOnClickListener(this);
        cancel.setOnClickListener(this);
        check.setOnClickListener(this);
        txt_back.setOnClickListener(this);
        btn_foodmap.setOnClickListener(this);

        foodlistView.setOnItemClickListener(this);

        spinner.setOnItemSelectedListener(this);

        checkBox_delivery.setOnCheckedChangeListener(this);
        checkBox_displace.setOnCheckedChangeListener(this);
        checkBox_review.setOnCheckedChangeListener(this);

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
                setData();
                break;

            case R.id.community_food_txt_back :
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;

            case R.id.community_btn_food_map :   // 지도 버튼
                Intent intent = new Intent(this,FoodMapActivity.class);
                intent.putExtra("마커",getMapLocation());
                startActivity(intent);
                break;
        }

    }

    // 스피너 아이템 선택이 되면 콜백
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         type = spinner.getSelectedItem().toString();
        if(type.equals("전체")){  type = "All";  }
        setData();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    // 리스트뷰 아이템 클릭시
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FoodMainNotice item = (FoodMainNotice) parent.getItemAtPosition(position); //몇번째 아이템을 클릭했는지 그리고 그 아이템에 Model인 JooungoNotice에 연결
        String str = item.getCode(); // 클릭한 해당 아이템 게시판코드값 가져오기

        Intent intent = new Intent(getApplicationContext(),FoodDetailActivity.class);
        intent.putExtra("코드",str);
        startActivity(intent);
    }

    /* 체크 박스 버튼 리스너 */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.community_food_order_box_delivery :
                if(isChecked){   delivery = "Y"; Toast.makeText(getApplicationContext(),delivery,Toast.LENGTH_SHORT).show();  }else{  delivery = "All"; }
                break;
            case R.id.community_food_order_box_distance :
                if(isChecked){ ordervalue = "displace"; checkBox_review.setChecked(false);}else{
                    if(checkBox_review.isChecked()){
                        ordervalue = "review";
                    }else{
                        ordervalue = "All";
                    }
                }
                break;
            case R.id.community_food_order_box_review :
                if(isChecked){ ordervalue = "review"; checkBox_displace.setChecked(false);}else{
                    if(checkBox_displace.isChecked()){
                        ordervalue = "displace";
                    }else{
                        ordervalue = "All";
                    }
                }
                break;
        }
    }

    /* 데이터를 가져와서 뷰에 뿌려주는 메소드*/
    public void setData(){
        try{
            String result = new FoodMainTask(context).execute(type,delivery,ordervalue).get();
            new getList().execute(result);
        }catch (Exception e){ e.printStackTrace(); }
    }

    /* 위치 정보를 가져오는 메소드 */
    public String getMapLocation(){
        JSONObject item;
        JSONArray items = new JSONArray();
        try{

            for(int i = 0;i<foodNoticeList.size();i++){
                String title = foodNoticeList.get(i).getTitle();
                String lat = foodNoticeList.get(i).getLat();
                String logn = foodNoticeList.get(i).getLogn();
                item = new JSONObject();
                item.put("이름",title);
                item.put("위도",lat);
                item.put("경도",logn);
                items.put(item);
            }
        }catch (Exception e) { e.printStackTrace();  }
        return items.toString();
    }


    // 맛집 리스트 UI 처리 쓰레드
   private class getList extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... json) {
            return json[0];
        }
        @Override
        public void onPostExecute(String result){
            foodNoticeList.clear();
            try{
                JSONArray jsonArray = new JSONArray(result);
                JSONObject json;
                int count = 0;
                String code,group,title,img,adress,lat,logn;
                while( count < jsonArray.length()){

                    json = jsonArray.getJSONObject(count);
                    code = json.getString("코드");
                    group = json.getString("그룹");
                    title = json.getString("제목");
                    img = json.getString("이미지");
                    adress = json.getString("주소");
                    lat = json.getString("위도");
                    logn = json.getString("경도");
                    foodNoticeList.add(new FoodMainNotice(code,title,adress,group,img,lat,logn));
                    count++;
                }
                foodAdapter = new FoodMainAdapter(getApplicationContext(),foodNoticeList);
                foodlistView.setAdapter(foodAdapter);
                foodAdapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
        }   //onPostExecute finish
    }   //class finish
}