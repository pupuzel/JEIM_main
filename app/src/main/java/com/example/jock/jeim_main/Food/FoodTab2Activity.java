package com.example.jock.jeim_main.Food;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jock.jeim_main.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FoodTab2Activity extends Fragment {

    private Bundle bundle;
    private String code;
    private View v;
    private ListView listView;
    private List<FoodTab2_Notice> NoticeList = new ArrayList<FoodTab2_Notice>();

    private FoodTab2_Adapter adapter;
    private Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        code = bundle.getString("code");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.food_detail_tab2,container,false); //텝호스트 레이아웃 가리킴
        context = v.getContext();

        listView = (ListView) v.findViewById(R.id.community_food_menu_listview);
        adapter = new FoodTab2_Adapter(v.getContext(),NoticeList);
        listView.setAdapter(adapter);

        getTask();
        return v;
    }

    public void getTask(){
        try {
            String result = new FoodTab2_Task(context).execute(code).get();
            additem(result);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void additem(String jsonarray){
        int count = 0;
        String name,price;
        try {
            JSONArray jsonArray = new JSONArray(jsonarray);
            JSONObject jsonObject;

            while (count < jsonArray.length()) {
                jsonObject = jsonArray.getJSONObject(count);
                name = jsonObject.getString("메뉴이름");
                price = jsonObject.getString("메뉴가격");
                NoticeList.add(new FoodTab2_Notice(name,price));
                count++;
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
