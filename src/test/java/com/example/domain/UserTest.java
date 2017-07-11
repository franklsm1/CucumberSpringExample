package com.example.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @Test
    public void testCreateUser() {
        String firstName = "Sean";
        String lastName = "Franklin";
        String birthYear = "1984";

        User newUser = new User(firstName, lastName, birthYear);

        assertThat(newUser.getFirstName()).isEqualTo(firstName);
        assertThat(newUser.getLastName()).isEqualTo(lastName);
        assertThat(newUser.getBirthYear()).isEqualTo(birthYear);

    }
}
