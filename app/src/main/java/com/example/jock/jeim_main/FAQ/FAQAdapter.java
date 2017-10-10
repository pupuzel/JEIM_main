package com.example.jock.jeim_main.FAQ;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jock.jeim_main.R;

import java.util.List;

/**
 * Created by Jock on 2017-06-23.
 */

public class FAQAdapter extends BaseAdapter {

    private Context context;
    private List<FAQNotice> noticelist;

    public FAQAdapter(Context context, List<FAQNotice> noticelist) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView.inflate(context, R.layout.activity_faq_notice,null);

        TextView faq_title = (TextView)v.findViewById(R.id.faq_title);
        TextView faq_content = (TextView)v.findViewById(R.id.faq_content);

        faq_title.setText(noticelist.get(position).getFaq_title());
        faq_content.setText(noticelist.get(position).getFaq_content());

        v.setTag(noticelist.get(position).getFaq_title());
        return v;
    }
}
