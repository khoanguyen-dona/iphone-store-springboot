package com.apple.iphonestorespringboot.service;

import com.apple.iphonestorespringboot.exception.ProductException;
import com.apple.iphonestorespringboot.model.Cart;
import com.apple.iphonestorespringboot.model.User;
import com.apple.iphonestorespringboot.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);
    public String addCartItem(Long userId,AddItemRequest req) throws ProductException;
    public Cart findUserCart(Long userId);
}
