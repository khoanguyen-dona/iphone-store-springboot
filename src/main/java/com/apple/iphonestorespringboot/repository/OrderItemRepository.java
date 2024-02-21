package com.apple.iphonestorespringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apple.iphonestorespringboot.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{

}