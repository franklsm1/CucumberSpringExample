package com.allstate.controller;


import com.allstate.model.User;
import com.allstate.repository.UsersRepository;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private UsersRepository usersRepository;

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

//    public UsersController(UsersRepository usersRepository) {
//        this.usersRepository = usersRepository;
//    }

//    @GetMapping("")
//    public Iterable<User> findAllUsers(){
//        return this.usersRepository.findAll();
//    }
//
//    @PostMapping("")
//    public User createUser(@RequestBody User user){
//        this.usersRepository.save(user);
//        return user;
//    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable long userId) {
        return usersRepository.findOne(userId);
    }
}
