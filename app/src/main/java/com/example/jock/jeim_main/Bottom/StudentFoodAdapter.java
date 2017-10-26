package com.example.jock.jeim_main.Bottom;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jock.jeim_main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jock on 2017-10-20.
 */

public class StudentFoodAdapter extends BaseAdapter{

    private Context context;
    private List<StudentFoodNotice> NoticeList  = new ArrayList<StudentFoodNotice>();

    public StudentFoodAdapter(Context context, List<StudentFoodNotice> noticeList) {
        this.context = context;
        NoticeList = noticeList;
    }

    @Override
    public int getCount() {
        return NoticeList.size();
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

        View v = View.inflate(context, R.layout.bottom_studentfood_notice, null);

        TextView date = (TextView) v.findViewById(R.id.studentfood_notice_txt_date);
        TextView gamamenu = (TextView) v.findViewById(R.id.studentfood_notice_txt_gama_menu);
        TextView intermenu = (TextView) v.findViewById(R.id.studentfood_notice_txt_inturn_menu);
        TextView chammenu = (TextView) v.findViewById(R.id.studentfood_notice_txt_cham_menu);

        date.setText(NoticeList.get(position).getDate());
        gamamenu.setText(NoticeList.get(position).getGama());
        intermenu.setText(NoticeList.get(position).getInter());
        chammenu.setText(NoticeList.get(position).getCham());
        return v;
    }
}
