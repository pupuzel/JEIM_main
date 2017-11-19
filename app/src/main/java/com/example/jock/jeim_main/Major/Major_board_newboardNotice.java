package com.example.jock.jeim_main.Major;

import java.util.List;



public class Major_board_newboardNotice {

    private String userid;
    private String content;
    private List<byte[]> imgbytelist;

    public Major_board_newboardNotice(){

    }

    public Major_board_newboardNotice(String userid, String content, List<byte[]> imgbytelist) {
        this.userid = userid;
        this.content = content;
        this.imgbytelist = imgbytelist;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<byte[]> getImgbytelist() {
        return imgbytelist;
    }

    public void setImgbytelist(List<byte[]> imgbytelist) {
        this.imgbytelist = imgbytelist;
    }

}
