package com.apple.iphonestorespringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apple.iphonestorespringboot.model.Address;

public interface AddressRepository extends JpaRepository<Address,Long>{
    
}
