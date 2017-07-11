package com.example.service;

import com.example.domain.User;
import com.example.domain.UserRequest;
import com.example.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User save(UserRequest userRequest) {
        User newUser = new User(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getBirthYear());

        usersRepository.save(newUser);
        return newUser;
    }

    public Iterable<User> findAll() {
        return usersRepository.findAll();
    }

    public User findOne(long userId) {
        return usersRepository.findOne(userId);
    }

    public void deleteAll() {
        usersRepository.deleteAll();
    }
}
