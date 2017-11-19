package com.example.jock.jeim_main.Major;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.jock.jeim_main.Another.Url;
import com.example.jock.jeim_main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jock on 2017-11-17.
 */

public class Major_boardAdapter extends BaseAdapter {

    private Context context;
    private List<Major_boardNotice> noticeList;
    private List<String> imglist = new ArrayList<String>();
    public Major_boardAdapter(Context context, List<Major_boardNotice> noticelist) {
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
        View v = View.inflate(context, R.layout.major_board_notice, null);

        TextView txt_usernm = (TextView) v.findViewById(R.id.major_board_usernm);
        TextView txt_date = (TextView) v.findViewById(R.id.major_board_date);
        TextView txt_content = (TextView) v.findViewById(R.id.major_board_content);
   final LinearLayout imglayout = (LinearLayout) v.findViewById(R.id.major_board_imglayout);

        txt_usernm.setText(noticeList.get(position).getUsernm());
        txt_date.setText(noticeList.get(position).getDate());
        txt_content.setText(noticeList.get(position).getContent());
        imglist = noticeList.get(position).getImglist();

            if(imglist.size() > 0) {
                for(int i =0; i<imglist.size(); i++){
                    Glide.with(context)
                            .load(Url.ImgTake+imglist.get(i))
                            .asBitmap()
                            .error(R.drawable.ic_clear)
                            .placeholder(R.drawable.research)
                            //.diskCacheStrategy(DiskCacheStrategy.RESULT)
                            .thumbnail(0.1f)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    ImageView imageView = new ImageView(context);
                                    imageView.setImageBitmap(resource);
                                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                    LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                    parm.setMargins(5,0,5,5);
                                    imageView.setLayoutParams(parm);
                                    imglayout.addView(imageView);
                                }
                            });

                }
            }


        return v;
    }
}