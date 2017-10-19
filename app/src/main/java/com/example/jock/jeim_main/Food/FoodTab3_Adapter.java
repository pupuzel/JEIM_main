package com.example.jock.jeim_main.Food;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jock.jeim_main.Fiction.FictionNotice;
import com.example.jock.jeim_main.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Jock on 2017-10-19.
 */

public class FoodTab3_Adapter extends BaseAdapter  {

    private Context context;
    private List<FoodTab3_Notice> noticeList;

    public FoodTab3_Adapter(Context context, List<FoodTab3_Notice> noticelist) {
        this.context = context;
        this.noticeList = noticelist;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int position) {
        return noticeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context , R.layout.food_detail_tab3_notice,null);

        TextView name = (TextView) v.findViewById(R.id.food_review_txt_name);
        TextView content = (TextView) v.findViewById(R.id.food_review_txt_content);
        TextView date = (TextView) v.findViewById(R.id.food_review_txt_date);

        name.setText(noticeList.get(position).getUsername());
        content.setText(noticeList.get(position).getContent());
        date.setText(noticeList.get(position).getDate());

        return v;
    }
}
