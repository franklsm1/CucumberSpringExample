package com.example.controller;


import com.example.model.User;
import com.example.model.UserResponse;
import com.example.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController  extends ExceptionController{

    private final UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("")
    public Iterable<User> findAllUsers(){
        return this.usersRepository.findAll();
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("")
    public UserResponse createUser(@Valid @RequestBody User user){
//        if (errors.hasErrors()){
//            UserResponse response = new UserResponse();
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//            response.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }

        this.usersRepository.save(user);
        return new UserResponse(user, HttpStatus.CREATED);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable long userId) {
        User user =  usersRepository.findOne(userId);
        return new UserResponse(user, HttpStatus.OK);
    }
}
