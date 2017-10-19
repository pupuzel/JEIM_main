package com.example.jock.jeim_main.Food;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jock.jeim_main.R;

import java.util.ArrayList;
import java.util.List;


public class FoodTab2 extends Fragment {

    private Bundle bundle;
    private String foodjson;
    private View v;
    private ListView listView;
    private List<FoodTab2_Notice> NoticeList = new ArrayList<FoodTab2_Notice>();

    private FoodTab2_Adapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bundle = getArguments();
        //foodjson = bundle.getString("foodjson");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.food_detail_tab2,container,false); //텝호스트 레이아웃 가리킴

        listView = (ListView) v.findViewById(R.id.community_food_menu_listview);
        adapter = new FoodTab2_Adapter(v.getContext(),NoticeList);
        listView.setAdapter(adapter);

        NoticeList.add(new FoodTab2_Notice("떡볶잉","3,000원"));
        NoticeList.add(new FoodTab2_Notice("계란밥","5,000원"));
        NoticeList.add(new FoodTab2_Notice("치즈부대찌개","5,500원"));
        NoticeList.add(new FoodTab2_Notice("순대국밥","6,000원"));
        NoticeList.add(new FoodTab2_Notice("싸이버거","5,400원"));

        adapter.notifyDataSetChanged();

        return v;
    }
}
