package com.example.huecoscolombia.Model.entity;

import com.example.huecoscolombia.Model.driver.Colum;
import com.example.huecoscolombia.Model.driver.Id;
import com.example.huecoscolombia.Model.driver.Table;

import java.util.List;

@Table(name = "PERSON")
public class Person {

    @Id
    private String cc;
    private User user;
    @Colum()
    private String name;
    private String email;
    private List<Publication> publicationsDone;

}
