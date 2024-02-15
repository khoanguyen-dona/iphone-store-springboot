package com.apple.iphonestorespringboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.apple.iphonestorespringboot.exception.OrderException;
import com.apple.iphonestorespringboot.model.Address;
import com.apple.iphonestorespringboot.model.Order;
import com.apple.iphonestorespringboot.model.User;
import com.apple.iphonestorespringboot.repository.CartRepository;

@Service
public class OrderServiceImplementation implements OrderService{

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;

    public OrderServiceImplementation(CartRepository cartRepository,CartItemService cartItemService,ProductService productService){
        this.cartItemService=cartItemService;
        this.cartRepository=cartRepository;
        this.productService=productService;
    }

    @Override
    public Order createOrder(User user, Address shippingAddress) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createOrder'");
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOrderById'");
    }

    @Override
    public List<Order> usersOrderHistory(long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'usersOrderHistory'");
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'placedOrder'");
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'confirmedOrder'");
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shippedOrder'");
    }

    @Override
    public Order deliveredOrder(Long OrderId) throws OrderException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deliveredOrder'");
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canceledOrder'");
    }

    @Override
    public List<Order> getAllOrders() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllOrders'");
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteOrder'");
    }
    
}
