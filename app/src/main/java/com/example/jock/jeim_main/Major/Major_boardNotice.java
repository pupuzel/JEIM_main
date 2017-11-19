package com.example.jock.jeim_main.Major;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Major_boardNotice {

    private String userid;
    private String content;
    private String date;
    private List<String> imglist = new ArrayList<String>();

    public Major_boardNotice(String userid, String usernm, String content, String date, List<String> imglist) {
        this.userid = userid;
        this.usernm = usernm;
        this.content = content;
        this.date = date;
        this.imglist = imglist;

    }

    private String usernm;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsernm() {
        return usernm;
    }

    public void setUsernm(String usernm) {
        this.usernm = usernm;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getImglist() {
        return imglist;
    }

    public void setImglist(List<String> imglist) {
        this.imglist = imglist;
    }
}
