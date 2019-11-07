package com.example.huecoscolombia.Model.entity;

import com.example.huecoscolombia.Model.driver.Colum;
import com.example.huecoscolombia.Model.driver.Foreign;
import com.example.huecoscolombia.Model.driver.Id;

import java.util.Date;

public class Message {

    @Id
    private String id;
    @Foreign(name = "witter",reference = "username")
    private User writer;
    @Colum(name = "message")
    private String message;
    @Colum(name = "date_message",type = Colum.Type.INTEGER)
    private Date date;
}
