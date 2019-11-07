package com.example.huecoscolombia.Model.entity;

import com.example.huecoscolombia.Model.driver.Colum;
import com.example.huecoscolombia.Model.driver.Foreign;
import com.example.huecoscolombia.Model.driver.Id;
import com.example.huecoscolombia.Model.driver.Table;

import java.util.List;

@Table(name = "USER")
public class User {

    @Id(name = "username")
    private String username;
    @Colum(name = "password")
    private String password;
    @Foreign(name = "person",reference = "cc")
    private Person person;
    @Foreign(name = "state",reference = "id")
    private State state;
    private List<Role> roles;

}
