package com.example.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRequest {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Pattern(regexp="[0-9][0-9][0-9][0-9]")
    private String birthYear;

    public UserRequest(){
        //Needed for Jackson
    }

    public UserRequest(String firstName, String lastName, String birthYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
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

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }
}
