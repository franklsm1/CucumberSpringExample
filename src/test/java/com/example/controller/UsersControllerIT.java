package com.example.controller;


import com.example.domain.User;
import com.example.domain.UserRequest;
import com.example.repository.UsersRepository;
import com.example.service.UserService;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerIT {

    @Autowired
    UserService userService;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    MockMvc mockMvc;

    @After
    public void cleanUp(){
        userService.deleteAll();
    }

    @Test
    @Transactional(readOnly = true)
    public void testGetEmptyAllUsers() throws Exception {
        MockHttpServletRequestBuilder request = get("/users");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", equalTo(0)));
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateUser() throws Exception {
        JSONObject mockUser = new JSONObject();
        mockUser.put("firstName", "Kim");
        mockUser.put("lastName", "Jones");
        mockUser.put("birthYear", "1990");

        MockHttpServletRequestBuilder request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mockUser.toString());

        this.mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(mockUser.get("firstName"))))
                .andExpect(jsonPath("$.lastName", equalTo(mockUser.get("lastName"))))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @Transactional
    @Rollback
    public void testGetOneUser() throws Exception {
        String lastName = "Moon";
        String firstName = "Sun";
        String birthday = "1984";

        UserRequest user = new UserRequest(firstName, lastName, birthday);

        User newUser = userService.save(user);

        MockHttpServletRequestBuilder request = get("/users/" + newUser.getId());

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(newUser.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(newUser.getLastName())))
                .andExpect(jsonPath("$.birthYear", equalTo(newUser.getBirthYear())));
    }
}
