package com.apple.iphonestorespringboot.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.apple.iphonestorespringboot.model.OrderItem;
import com.apple.iphonestorespringboot.repository.OrderItemRepository;


@Service
public class OrderItemServiceImplementation  implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderItemServiceImplementation(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
    
}
