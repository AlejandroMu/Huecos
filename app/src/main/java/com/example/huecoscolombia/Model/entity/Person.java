package com.example.huecoscolombia.Model.entity;

import com.example.huecoscolombia.Model.driver.Colum;
import com.example.huecoscolombia.Model.driver.Id;
import com.example.huecoscolombia.Model.driver.Table;

import java.io.Serializable;

@Table(name = "PERSON")
public class Person implements Serializable {
    public static final String BRANCH="persons";


    @Id
    private String email;
    @Colum(name = "name")
    private String name;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
