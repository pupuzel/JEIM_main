package com.example.jock.jeim_main.Food;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.jock.jeim_main.R;
import com.example.jock.jeim_main.Url;

import java.util.ArrayList;
import java.util.List;


public class FoodAdapter extends BaseAdapter {

    private TextView title;
    private TextView adress;
    private TextView group;
    private ImageView img;
    private int count = 0;
    private Context context;
    private List<FoodNotice> noticeList;
    private List<Bitmap> bitmapList = new ArrayList<Bitmap>();
    private View v = null;

    public FoodAdapter(Context context, List<FoodNotice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
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

        v = View.inflate(context, R.layout.food_notice, null);

        title = (TextView) v.findViewById(R.id.community_food_listview_txt_title);
        adress = (TextView) v.findViewById(R.id.community_food_listview_txt_adress);
        group = (TextView) v.findViewById(R.id.community_food_listview_txt_group);
        img = (ImageView) v.findViewById(R.id.community_food_listview_imgview);

        title.setText(noticeList.get(position).getTitle());
        adress.setText(noticeList.get(position).getAdress());
        group.setText(noticeList.get(position).getGroup());
        String imgname = noticeList.get(position).getImg();

            Glide.with(context)
                    .load(Url.Main+Url.FoodTake+imgname)
                    .error(R.drawable.ic_clear)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .thumbnail(0.1f)
                    .into(img);

        count++;
        return v;
    }
}

