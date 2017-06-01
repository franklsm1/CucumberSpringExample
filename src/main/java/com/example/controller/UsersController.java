package com.example.controller;


import com.example.model.User;
import com.example.model.UserRequest;
import com.example.model.UserResponse;
import com.example.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController extends ExceptionController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    @ApiOperation(value = "Return all users.", response = User.class, responseContainer = "List")
    public Iterable<User> getAllUsers(){
        return this.userService.findAll();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable long userId) {
        User user = userService.findOne(userId);
        return new UserResponse(user, HttpStatus.OK);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("")
    public UserResponse postUser(@Valid @RequestBody UserRequest user) {
        User newUser = this.userService.save(user);
        return new UserResponse(newUser, HttpStatus.CREATED);
    }
}
