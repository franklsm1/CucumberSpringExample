package com.allstate.model;

import org.springframework.http.HttpStatus;

public class UserResponse  extends AbstractResponse {
    private final User user;

    public UserResponse(User user, HttpStatus httpStatus) {
        this.user = user;
        this.setStatus(httpStatus.value());
        this.setMessage(httpStatus.getReasonPhrase());
    }

    public User getUser() {
        return user;
    }
}
