package com.example.controller;


import com.example.model.User;
import com.example.repository.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @MockBean
    UsersRepository usersRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetUsers() throws Exception {
        User users = new User();
        when(this.usersRepository.findAll()).thenReturn(Collections.singletonList(users));
    }


}
