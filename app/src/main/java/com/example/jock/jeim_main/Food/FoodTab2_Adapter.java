package com.example.jock.jeim_main.Food;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jock.jeim_main.R;

import java.util.List;


public class FoodTab2_Adapter extends BaseAdapter {

    private Context context;
    private List<FoodTab2_Notice> noticeList;
    private View v;
    private TextView name,price;
    private LayoutInflater inflater;

    public FoodTab2_Adapter(Context context, List<FoodTab2_Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            v = inflater.inflate(R.layout.food_detail_tab2_notice, parent,false);
        }else {
            v = convertView;
        }
        LinearLayout layout = (LinearLayout)v;
        name = (TextView) layout.findViewById(R.id.community_food_menu_listview_txt_name);
        price = (TextView) layout.findViewById(R.id.community_food_menu_listview_txt_price);

        name.setText(noticeList.get(position).getName());
        price.setText(noticeList.get(position).getPrice());

        return layout;
    }
}
