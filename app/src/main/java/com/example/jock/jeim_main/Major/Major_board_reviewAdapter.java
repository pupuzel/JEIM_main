package com.example.jock.jeim_main.Major;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jock.jeim_main.R;

import java.util.List;

/**
 * Created by Jock on 2017-11-22.
 */

public class Major_board_reviewAdapter extends BaseAdapter {
    private Context context;
    private List<Major_board_reviewNotice> noticeList;

    public Major_board_reviewAdapter(Context context, List<Major_board_reviewNotice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
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

        View v = View.inflate(context, R.layout.major_board_review_notice,null);

        TextView name = (TextView) v.findViewById(R.id.major_board_review_name);
        TextView content = (TextView) v.findViewById(R.id.major_board_review_content);
        TextView date = (TextView) v.findViewById(R.id.major_board_review_date);

        name.setText(noticeList.get(position).getUsername());
        content.setText(noticeList.get(position).getContent());
        date.setText(noticeList.get(position).getDate());

        return v;
    }
}
