package com.allstate.controller;


import com.allstate.model.User;
import com.allstate.repository.UsersRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Date;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerIT {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    MockMvc mockMvc;

    @After
    public void cleanUp(){
        usersRepository.deleteAll();
    }

    @Test
    public void testGetEmptyAllUsers() throws Exception {
        MockHttpServletRequestBuilder request = get("/users");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", equalTo(0)));
    }

    @Test
    public void testCreateUser() throws Exception {
        JSONObject mockUser = new JSONObject();
        mockUser.put("firstName", "Kim");
        mockUser.put("lastName", "Jones");

        JSONArray groups = new JSONArray();

        mockUser.put("birthYear", "1990");

        MockHttpServletRequestBuilder request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mockUser.toString());

        this.mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.firstName", equalTo(mockUser.get("firstName"))))
                .andExpect(jsonPath("$.user.lastName", equalTo(mockUser.get("lastName"))))
                .andExpect(jsonPath("$.user.id").exists());
    }

    @Test
    public void testGetOneUser() throws Exception {
        String lastName = "Moon";
        String firstName = "Sun";
        String birthday = "1984";

        User newUser = new User(firstName, lastName, birthday);

        usersRepository.save(newUser);

        MockHttpServletRequestBuilder request = get("/users/" + newUser.getId());

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.firstName", equalTo(newUser.getFirstName())))
                .andExpect(jsonPath("$.user.lastName", equalTo(newUser.getLastName())));
    }
}
