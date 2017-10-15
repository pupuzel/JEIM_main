package com.example.jock.jeim_main.Jooungo;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jock.jeim_main.R;

import java.util.List;


public class JooungoAdapter extends BaseAdapter {

    private Context context;
    private List<JooungoNotice> noticelist;

    public JooungoAdapter(Context context, List<JooungoNotice> noticelist) {
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

        Log.i("실행 횟수","즐");
        View v = View.inflate(context, R.layout.jooungo_notice, null);

        TextView title = (TextView) v.findViewById(R.id.boardtitle);
        TextView name = (TextView) v.findViewById(R.id.boardusername);
        TextView date = (TextView) v.findViewById(R.id.boarddate);
        TextView code = (TextView) v.findViewById(R.id.boardcode);

        title.setText(noticelist.get(position).getTitle());
        name.setText(noticelist.get(position).getUsername());
        date.setText(noticelist.get(position).getDate());
        //code.setText(noticelist.get(position).getBoardcode());

        v.setTag(noticelist.get(position).getTitle());
        return v;
    }
}
