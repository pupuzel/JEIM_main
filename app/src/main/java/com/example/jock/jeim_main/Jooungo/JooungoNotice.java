package com.example.jock.jeim_main.Jooungo;

/**
 * Created by Jock on 2017-06-07.
 */

public class JooungoNotice {

    String boardcode;
    String username;
    String title;
    String cheak;
    String date;
    String group;

    public JooungoNotice(String boardcode, String username, String title, String cheak,String group, String date) {
        this.boardcode = boardcode;
        this.username = username;
        this.title = title;
        this.cheak = cheak;
        this.date = date;
        this.group = group;
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

    public String getCheak() {
        return cheak;
    }

    public void setCheak(String cheak) {
        this.cheak = cheak;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
