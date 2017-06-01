package com.example.controller;


import com.example.model.User;
import com.example.model.UserRequest;
import com.example.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.util.Objects.isNull;

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
    public Iterable<User> getAllUsers() {
        return this.userService.findAll();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable long userId) {

        User user = userService.findOne(userId);
        if (isNull(user)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("")
    public User postUser(@Valid @RequestBody UserRequest user) {
        return this.userService.save(user);
    }
}
