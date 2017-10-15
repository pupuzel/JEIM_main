package com.example.jock.jeim_main.Library;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jock.jeim_main.R;

import java.util.List;

/**
 * Created by Jock on 2017-10-14.
 */

public class LibraryAdapter extends BaseAdapter {

    public LibraryAdapter(Context context, List<LibraryNotice> list) {
        this.context = context;
        this.list = list;
    }

    private Context context;
    private List<LibraryNotice> list;


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.library_notice, null);

        TextView title = (TextView) v.findViewById(R.id.Library_txt_title);
        TextView name = (TextView) v.findViewById(R.id.Library_txt_name);
        TextView publish = (TextView) v.findViewById(R.id.Library_txt_publish);
        TextView year = (TextView) v.findViewById(R.id.Library_txt_year);

        title.setText(list.get(position).getTitle());
        name.setText(list.get(position).getName());
        publish.setText(list.get(position).getPublish());
        year.setText(list.get(position).getYear());

        v.setTag(list.get(position).getTitle());
        return v;
    }
}
