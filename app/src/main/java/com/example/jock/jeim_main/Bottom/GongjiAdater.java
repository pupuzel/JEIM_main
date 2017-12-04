package com.example.jock.jeim_main.Bottom;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jock.jeim_main.R;

import java.util.List;


public class GongjiAdater extends BaseAdapter {

    private Context context;
    private List<GongjiNotice> noticelist;

    public GongjiAdater(Context context, List<GongjiNotice> noticelist) {
        this.context = context;
        this.noticelist = noticelist;
    }

    @Override
    public int getCount() {
        return noticelist.size();
    }

    @Override
    public Object getItem(int position) {
        return noticelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null){
           v =  View.inflate(context, R.layout.bottom_gongji_notice, null);
        }

        TextView title = (TextView) v.findViewById(R.id.bottom_gongji_title);
        TextView name = (TextView) v.findViewById(R.id.bottom_gongji_name);
        TextView date = (TextView) v.findViewById(R.id.bottom_gongji_date);

        title.setText(noticelist.get(position).getTitle());
        name.setText(noticelist.get(position).getUsername());
        date.setText(noticelist.get(position).getDate());

        if(position%2 ==1){
            v.setBackgroundColor(Color.rgb(246,246,246));
        }
        v.setTag(noticelist.get(position).getTitle());
        return v;
    }

}
