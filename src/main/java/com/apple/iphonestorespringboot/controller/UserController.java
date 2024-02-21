package com.apple.iphonestorespringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apple.iphonestorespringboot.exception.UserException;
import com.apple.iphonestorespringboot.model.User;
import com.apple.iphonestorespringboot.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User>getUserProfileHandler(@RequestHeader("Authorization") String jwt)throws UserException{

        User user =userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<User>getUserProfileHandler(@PathVariable Long userId) throws UserException{

        User user=userService.findUserById(userId);

        return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
    }
}
