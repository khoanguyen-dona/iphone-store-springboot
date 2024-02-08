package com.apple.iphonestorespringboot.service;

import com.apple.iphonestorespringboot.exception.UserException;
import com.apple.iphonestorespringboot.model.User;

public interface UserService {
    
    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
