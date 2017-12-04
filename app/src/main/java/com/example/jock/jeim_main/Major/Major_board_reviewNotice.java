package com.example.jock.jeim_main.Major;

/**
 * Created by Jock on 2017-11-22.
 */

public class Major_board_reviewNotice {
    public Major_board_reviewNotice(String username, String content, String date) {
        this.username = username;
        this.content = content;
        this.date = date;
    }

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

    private String username;
    private String content;
    private String date;
}
