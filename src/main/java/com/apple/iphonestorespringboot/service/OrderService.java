package com.apple.iphonestorespringboot.service;

import java.util.List;

import com.apple.iphonestorespringboot.exception.OrderException;
import com.apple.iphonestorespringboot.model.Address;
import com.apple.iphonestorespringboot.model.Order;
import com.apple.iphonestorespringboot.model.User;

public interface OrderService {
    
    public Order createOrder(User user,Address shippingAddress);
    public Order findOrderById(Long orderId) throws OrderException;
    public List<Order>usersOrderHistory(long userId);
    public Order placedOrder(Long orderId) throws OrderException;
    public Order confirmedOrder(Long orderId) throws OrderException;
    public Order shippedOrder(Long orderId) throws OrderException;
    public Order deliveredOrder(Long OrderId) throws OrderException;
    public Order canceledOrder(Long orderId) throws OrderException;
    public List<Order>getAllOrders();
    public void deleteOrder(Long orderId) throws OrderException;

}
