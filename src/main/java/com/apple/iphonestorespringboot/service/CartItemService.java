package com.apple.iphonestorespringboot.service;

import com.apple.iphonestorespringboot.exception.CartItemException;
import com.apple.iphonestorespringboot.exception.UserException;
import com.apple.iphonestorespringboot.model.Cart;
import com.apple.iphonestorespringboot.model.CartItem;
import com.apple.iphonestorespringboot.model.Product;
import com.apple.iphonestorespringboot.request.AddItemRequest;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem,AddItemRequest req);
    // public CartItem updateCartItem(Long userId,long id,CartItem cartItem)throws CartItemException,UserException;
    public CartItem updateCartItem(Long cartItemId,CartItem cartItem)throws CartItemException,UserException;


    public CartItem isCartItemExist(Cart cart,Product product,String size,Long userId);
    public void removeCartItem(Long userId,Long cartItemId) throws CartItemException,UserException;
    public CartItem findCartItemById(Long cartItemId)throws CartItemException;
}
