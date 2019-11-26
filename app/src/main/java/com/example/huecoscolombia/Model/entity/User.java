package com.example.huecoscolombia.Model.entity;

import com.example.huecoscolombia.Model.driver.Colum;
import com.example.huecoscolombia.Model.driver.Foreign;
import com.example.huecoscolombia.Model.driver.Id;
import com.example.huecoscolombia.Model.driver.Table;

import java.util.List;

@Table(name = "USER")
public class User {
    public static final String BRANCH="users";


    @Id(name = "username")
    private String username;
    @Colum(name = "password")
    private String password;
    @Foreign(name = "person",reference = "cc")
    private Person person;
    @Foreign(name = "state",reference = "id")
    private String state;
    private List<Role> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
