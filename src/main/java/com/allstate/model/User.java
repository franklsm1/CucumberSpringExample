package com.allstate.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName, lastName, nickname;
    private int birthday;

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    @ElementCollection(targetClass=String.class)
    private List<String> groups = new ArrayList<>();

    public User() {

    }

    public User(String firstName, String lastName, int birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        String name ="";
        switch(birthday) {
            case 1984: name = "Rad " + firstName;
                break;
            case 2001: name = "Dope " + firstName;
                break;
        }
        return name;
    }

//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

}
