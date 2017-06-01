package com.example.controller;


import com.example.model.User;
import com.example.model.UserRequest;
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

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("")
    @ApiOperation(value = "Return all users.", response = User.class, responseContainer = "List")
    public Iterable<User> getAllUsers(){
        return this.userService.findAll();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{userId}")
    public User getUser(@PathVariable long userId) {
        return userService.findOne(userId);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("")
    public User postUser(@Valid @RequestBody UserRequest user) {
        return this.userService.save(user);
    }
}
