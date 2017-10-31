package com.example.jock.jeim_main.Jooungo;

import android.content.Context;
import android.graphics.Color;
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

        View v = View.inflate(context, R.layout.jooungo_notice, null);

        TextView title = (TextView) v.findViewById(R.id.boardtitle);
        TextView name = (TextView) v.findViewById(R.id.boardusername);
        TextView cheak = (TextView) v.findViewById(R.id.jooungo_boardcheck);
        TextView date = (TextView) v.findViewById(R.id.boarddate);
        TextView code = (TextView) v.findViewById(R.id.boardcode);

        title.setText(noticelist.get(position).getTitle());
        name.setText(noticelist.get(position).getUsername());
        date.setText(noticelist.get(position).getDate());

       if( noticelist.get(position).getCheak().equals("1") ){

            cheak.setVisibility(View.INVISIBLE);

        }else{

            if(noticelist.get(position).getGroup().equals("1")){
                cheak.setText("판매 완료");
            }else {
                cheak.setText("구매 완료");
            }
            cheak.setVisibility(View.VISIBLE);
        }

        if(position%2 ==1){
            v.setBackgroundColor(Color.rgb(246,246,246));
        }

        v.setTag(noticelist.get(position).getTitle());
        return v;
    }
}
