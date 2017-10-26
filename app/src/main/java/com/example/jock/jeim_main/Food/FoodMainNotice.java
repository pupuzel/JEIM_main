package com.example.jock.jeim_main.Food;

import android.graphics.Bitmap;



public class FoodMainNotice {

    private String code;
    private String title;
    private String adress;
    private String group;
    private String img;
    private Bitmap bitmap;
    private String lat;
    private String logn;

    public FoodMainNotice(String code, String title, String adress, String group, String img, String lat, String logn) {
        this.code = code;
        this.title = title;
        this.adress = adress;
        this.group = group;
        this.img = img;
        this.lat = lat;
        this.logn = logn;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLogn() {
        return logn;
    }

    public void setLogn(String logn) {
        this.logn = logn;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
