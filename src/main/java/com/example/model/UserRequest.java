package com.example.model;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRequest {

    @NotNull
    @ApiModelProperty(example = "Sean")
    private String firstName;

    @NotNull
    @ApiModelProperty(example = "Franklin")
    private String lastName;

    @NotNull
    @Pattern(regexp="[0-9][0-9][0-9][0-9]")
    @ApiModelProperty(example = "1991")
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

    public String getLastName() {
        return lastName;
    }

    public String getBirthYear() {
        return birthYear;
    }

}
