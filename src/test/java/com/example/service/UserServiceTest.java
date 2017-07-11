package com.example.service;

import com.example.domain.User;
import com.example.domain.UserRequest;
import com.example.repository.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UsersRepository usersRepository;

    @Transactional
    @Rollback
    @Test
    public void testCreateUser(){
        String firstName = "Sean";
        String lastName = "Franklin";
        String birthYear = "1991";
        UserRequest userRequest = new UserRequest(firstName, lastName, birthYear);

        UserService userService = new UserService(usersRepository);
        User newUser = userService.save(userRequest);

        assertEquals(firstName, newUser.getFirstName());
        assertTrue(newUser.getId() != null);
    }
}
