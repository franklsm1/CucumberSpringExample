package com.example.controller;


import com.example.domain.User;
import com.example.repository.UsersRepository;
import com.example.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
public class UsersControllerMockTest {

    @MockBean
    UsersRepository usersRepository;

    @MockBean
    UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void mockGetUser() throws Exception {
        User user = new User("Sean", "Franklin", "1991");
        user.setId(1L);

        when(this.userService.findOne(user.getId())).thenReturn(user);

        MockHttpServletRequestBuilder request = get("/users/" + user.getId());
        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(user.getLastName())));
    }
}
