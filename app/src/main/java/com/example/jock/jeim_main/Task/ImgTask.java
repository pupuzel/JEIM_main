package com.example.jock.jeim_main.Task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.jock.jeim_main.Another.Url;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImgTask extends AsyncTask<String,Integer,Bitmap> {

    private Bitmap bitimg;

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(Url.Main+Url.ImgTake+params[0]);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            bitimg = BitmapFactory.decodeStream(is);


        }catch (Exception e){
            e.printStackTrace();
        }
        return bitimg;
    }
}
