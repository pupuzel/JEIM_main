package com.example.jock.jeim_main.Food;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jock.jeim_main.R;


public class FoodTab1Activity extends Fragment{

    private Bundle bundle;
    private String title,group,address,phone,delivery;
    private View v;
    private TextView txt_title,txt_group,txt_address,txt_phone,txt_delivery;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getArguments();
        title = bundle.getString("title");
        group = bundle.getString("group");
        address = bundle.getString("address");
        phone = bundle.getString("phone");
        if(bundle.getString("delivery").equals("Y")){
            delivery = "배달 가능";
        }else{
            delivery = "배달 안함";
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.food_detail_tab1,container,false);
        txt_title = (TextView) v.findViewById(R.id.community_food_detail_info_name);
        txt_group = (TextView) v.findViewById(R.id.community_food_detail_info_group);
        txt_address = (TextView) v.findViewById(R.id.community_food_detail_info_adress);
        txt_phone = (TextView) v.findViewById(R.id.community_food_detail_info_callnumber);
        txt_delivery = (TextView) v.findViewById(R.id.community_food_detail_info_delivery);

        txt_title.setText(title);
        txt_group.setText(group);
        txt_address.setText(address);
        txt_phone.setText(phone);
        txt_delivery.setText(delivery);
        return v;
    }
}
