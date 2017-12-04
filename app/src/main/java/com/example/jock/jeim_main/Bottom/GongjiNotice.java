package com.example.jock.jeim_main.Bottom;

public class GongjiNotice {

    private String boardcode;
    private String username;
    private String title;
    private String date;

    public GongjiNotice(String boardcode, String username, String title, String date) {
        this.boardcode = boardcode;
        this.username = username;
        this.title = title;
        this.date = date;
    }
    public String getBoardcode() {
        return boardcode;
    }

    public void setBoardcode(String boardcode) {
        this.boardcode = boardcode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
