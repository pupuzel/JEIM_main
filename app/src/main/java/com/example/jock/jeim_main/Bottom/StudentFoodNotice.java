package com.example.jock.jeim_main.Bottom;


public class StudentFoodNotice {

    private String date,gama,inter,cham;

    public StudentFoodNotice(String date, String gama, String inter, String cham) {
        this.date = date;
        this.gama = gama;
        this.inter = inter;
        this.cham = cham;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGama() {
        return gama;
    }

    public void setGama(String gama) {
        this.gama = gama;
    }

    public String getInter() {
        return inter;
    }

    public void setInter(String inter) {
        this.inter = inter;
    }

    public String getCham() {
        return cham;
    }

    public void setCham(String cham) {
        this.cham = cham;
    }

}
