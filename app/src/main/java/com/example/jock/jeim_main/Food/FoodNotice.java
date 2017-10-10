package com.example.jock.jeim_main.Food;

/**
 * Created by Jock on 2017-09-28.
 */

public class FoodNotice {

    private String code;
    private String title;
    private String adress;
    private String group;
    private String img;

    public FoodNotice(String code, String title, String adress, String group, String img) {
        this.code = code;
        this.title = title;
        this.adress = adress;
        this.group = group;
        this.img = img;
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
