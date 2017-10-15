package com.example.jock.jeim_main.Library;



public class LibraryNotice {

    private String code,title,name,publish,year;

    public LibraryNotice(String code, String title, String name, String publish, String year) {
        this.code = code;
        this.title = title;
        this.name = name;
        this.publish = publish;
        this.year = year;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
