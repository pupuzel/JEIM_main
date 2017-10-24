package com.example.jock.jeim_main.Bottom;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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

    /*    View v = View.inflate(context, R.layout.studentfood_notice, null);

        TextView group = (TextView) v.findViewById(R.id.st);
        TextView date = (TextView) v.findViewById(R.id.boardusername);
        TextView name = (TextView) v.findViewById(R.id.boarddate);*/
        return null;
    }
}
