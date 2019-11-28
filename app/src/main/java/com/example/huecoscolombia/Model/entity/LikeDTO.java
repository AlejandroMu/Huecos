package com.example.huecoscolombia.Model.entity;

public class LikeDTO {

    private String user;
    private String pub;

    public LikeDTO(String user, String pub) {
        this.user = user;
        this.pub = pub;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }
}
