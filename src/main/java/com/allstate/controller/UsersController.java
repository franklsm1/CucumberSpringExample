package com.allstate.controller;


import com.allstate.model.User;
import com.allstate.repository.UsersRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("")
    public Iterable<User> findAllUsers(){
        return this.usersRepository.findAll();
    }

    @PostMapping("")
    public User createUser(@RequestBody User user){
        this.usersRepository.save(user);
        return user;
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable long userId) {
        return usersRepository.findOne(userId);
    }
}
