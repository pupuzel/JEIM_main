package com.example.jock.jeim_main.Jooungo;


import android.content.Context;

public class JooungoDetailNotice {

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImg1() {
        return img1;
    }

    public void setImg1(byte[] img1) {
        this.img1 = img1;
    }

    public byte[] getImg2() {
        return img2;
    }

    public void setImg2(byte[] img2) {
        this.img2 = img2;
    }

    public byte[] getImg3() {
        return img3;
    }

    public void setImg3(byte[] img3) {
        this.img3 = img3;
    }

    public byte[] getimg(int a){
        byte[] returnByte = null ;
        switch (a){
            case 0: returnByte = getImg1(); break;
            case 1: returnByte = getImg2(); break;
            case 2: returnByte = getImg3(); break;
        }
        return returnByte;
    }

    public int size(){
        int a = 0;
        if(img1 != null) {
            a = 1;
        }
        if(img2 != null) {
            a = 2;
        }
        if (img3 != null) {
            a = 3;
        }
        return  a;
    }

    public String getBoardcode() {
        return boardcode;
    }

    public void setBoardcode(String boardcode) {
        this.boardcode = boardcode;
    }

    private String boardcode;
    private String userid;
    private String price;
    private String title;
    private String content;
    private String group;
    private byte[] img1,img2,img3;
    private Context context;


}
