package com.allstate.model;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    public void testCreateUser() {
        String lastName = "Un";
        String firstName = "Sun";
        int birthday = 1984;

        User newUser = new User(firstName, lastName, birthday);

        assertThat(newUser.getFirstName()).isEqualTo(firstName);
        assertThat(newUser.getLastName()).isEqualTo(lastName);
        assertThat(newUser.getBirthday()).isEqualTo(birthday);

    }

    @Test
    public void testCreateUserRadNickName() {
        String lastName = "Un";
        String firstName = "Sun";
        int birthday = 1984;

        User newUser = new User(firstName, lastName, birthday);

        assertThat(newUser.getNickname()).isEqualTo("Rad " + firstName);
    }

    @Test
    public void testCreateUserDopeNickName() {
        String lastName = "Un";
        String firstName = "Sun";
        int birthday = 2001;

        User newUser = new User(firstName, lastName, birthday);

        assertThat(newUser.getNickname()).isEqualTo("Dope " + firstName);
    }




}
