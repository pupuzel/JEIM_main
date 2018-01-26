package com.example.jock.jeim_main.Major;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.jock.jeim_main.Another.Url;
import com.example.jock.jeim_main.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Jock on 2017-11-17.
 */

public class Major_boardAdapter extends BaseAdapter {

    private Context context;
    private List<Major_boardNotice> noticeList;
    private List<String> imglist = new ArrayList<String>();
    private int height,width;
    private ViewHolder holder;

    public Major_boardAdapter(Context context, List<Major_boardNotice> noticelist, int height, int width) {
        this.context = context;
        this.noticeList = noticelist;
        this.height = height;
        this.width = width;
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
    public View getView(int position, View v, ViewGroup parent) {

        if(v == null){
            v = LayoutInflater.from(context).inflate(R.layout.major_board_notice,null);
            holder = new ViewHolder();

            /* find */
            holder.txt_usernm = (TextView) v.findViewById(R.id.major_board_usernm);
            holder.txt_date = (TextView) v.findViewById(R.id.major_board_date);
            holder.txt_content = (TextView) v.findViewById(R.id.major_board_content);
            holder.txt_review = (TextView) v.findViewById(R.id.major_board__txt_review);
            holder.txt_newreview = (TextView) v.findViewById(R.id.major_board__txt_newreview);
            holder.imglayout = (LinearLayout) v.findViewById(R.id.major_board_imglayout);
            v.setTag(holder);
        }else{
            holder = (ViewHolder)v.getTag();
        }

        /* settext */
        holder.txt_usernm .setText(noticeList.get(position).getUsernm());
        holder.txt_date.setText(noticeList.get(position).getDate());
        holder.txt_content.setText(noticeList.get(position).getContent());
        holder.txt_review.setText("댓글 "+noticeList.get(position).getReviewcount()+"개");
        holder.code = noticeList.get(position).getCode();

        /* EventHandle */
        holder.txt_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Major_board_review.class);
                intent.putExtra("게시판코드",holder.code);
                context.startActivity(intent);
            }
        });
        holder.txt_newreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Major_board_review.class);
                intent.putExtra("게시판코드",holder.code);
                context.startActivity(intent);
            }
        });

        //* IMG *//*
        imglist = noticeList.get(position).getImglist();
/*        if(imglist.size() > 0 && holder.imglayout.getChildCount() == 0) {
            Log.i("이미지",String.valueOf(holder.imglayout.getChildCount()));
            for(int i =0; i<imglist.size(); i++){

                final ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setBackgroundDrawable(ContextCompat.getDrawable(context,R.color.gray));
                LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parm.setMargins(5,0,5,5);
                parm.gravity = Gravity.CENTER;
                imageView.setLayoutParams(parm);

                Glide.with(context)
                        .load(Url.ImgTake+imglist.get(i))
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .skipMemoryCache(true)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                imageView.setImageBitmap(resource);
                                
                                double persent = (double) resource.getWidth()/resource.getHeight();
                                LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams(width, (int)(width/persent));
                                imageView.setLayoutParams(parm);
                            }
                        });
                holder.imglayout.addView(imageView);
            }
        }*/

        return v;
    }

    static class ViewHolder {
        TextView txt_usernm;
        TextView txt_date;
        TextView txt_content;
        TextView txt_review;
        TextView txt_newreview;
        LinearLayout imglayout;
        String code;
    }
}