package com.apple.iphonestorespringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apple.iphonestorespringboot.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
    
    public User findByEmail(String email);
}
