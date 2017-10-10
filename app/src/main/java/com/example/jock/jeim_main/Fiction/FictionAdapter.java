package com.example.jock.jeim_main.Fiction;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jock.jeim_main.Jooungo.JooungoNotice;
import com.example.jock.jeim_main.R;

import java.util.List;


public class FictionAdapter extends BaseAdapter {

    private Context context;
    private List<FictionNotice> noticeList;

    public FictionAdapter(Context context, List<FictionNotice> noticelist) {
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
        View v = View.inflate(context , R.layout.activity_fiction_notice ,null);

        TextView name = (TextView) v.findViewById(R.id.fiction_txt_name);
        TextView content = (TextView) v.findViewById(R.id.fiction_txt_content);
        TextView date = (TextView) v.findViewById(R.id.fiction_txt_date);

        name.setText(noticeList.get(position).getUsername());
        content.setText(noticeList.get(position).getContent());
        date.setText(noticeList.get(position).getDate());

        if(position%2 ==1){
            v.setBackgroundColor(Color.rgb(246,246,246));
        }

        v.setTag(noticeList.get(position).getContent());
        return v;
    }
}
