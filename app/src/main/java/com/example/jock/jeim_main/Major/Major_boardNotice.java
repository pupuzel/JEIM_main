package com.example.jock.jeim_main.Major;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Major_boardNotice {

    private String code;
    private String reviewcount;
    private String userid;
    private String usernm;
    private String content;
    private String date;
    private List<String> imglist = new ArrayList<String>();

    public Major_boardNotice(String code, String userid, String usernm, String content, String date, List<String> imglist, String reviewcount) {
        this.code = code;
        this.userid = userid;
        this.usernm = usernm;
        this.content = content;
        this.date = date;
        this.imglist = imglist;
        this.reviewcount = reviewcount;

    }
    public String getReviewcount() {
        return reviewcount;
    }

    public void setReviewcount(String reviewcount) {
        this.reviewcount = reviewcount;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
