package com.example.jock.jeim_main.Fiction;

/**
 * Created by Jock on 2017-09-21.
 */

public class FictionNotice {


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public FictionNotice(String username, String content, String date) {
        this.username = username;
        this.content = content;
        this.date = date;
    }

    private String username;
    private String content;
    private String date;
}
