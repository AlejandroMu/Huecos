package com.example.huecoscolombia.Model.entity;

import com.example.huecoscolombia.Model.driver.Colum;
import com.example.huecoscolombia.Model.driver.Foreign;
import com.example.huecoscolombia.Model.driver.Id;

import java.util.Date;

public class Message {
    public static final String URL="https://us-central1-huecos-colombia-114c0.cloudfunctions.net/functions/messages";
    public static final String BRANCH="messages";

    @Id
    private String id;
    private String writer;
    @Colum(name = "message")
    private String message;
    private long date;

    public Message(){

    }

    public Message(String id, String writer, String message, long date) {
        this.id = id;
        this.writer = writer;
        this.message = message;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
