package com.example.huecoscolombia.Model.entity;

import com.example.huecoscolombia.Model.driver.Colum;
import com.example.huecoscolombia.Model.driver.Foreign;
import com.example.huecoscolombia.Model.driver.Id;
import com.example.huecoscolombia.Model.driver.Table;

import java.util.ArrayList;
import java.util.List;

@Table(name = "USER")
public class User {
    public static final String BRANCH="users";


    @Id(name = "username")
    private String username;
    @Colum(name = "password")
    private String password;
    private String email;
    private String state;
    private List<Role> roles;

    public User(){
        roles=new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getPerson() {
        return email;
    }

    public void setPerson(String email) {
        this.email = email;
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

    public void addRole(Role role){
        roles.add(role);
    }
}
