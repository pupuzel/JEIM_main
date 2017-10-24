package com.example.jock.jeim_main.Bottom;

/**
 * Created by Jock on 2017-10-20.
 */

public class StudentFoodNotice {

    public StudentFoodNotice(String group, String date, String foodname) {
        this.group = group;
        this.date = date;
        this.foodname = foodname;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    private String group,date,foodname;
}
