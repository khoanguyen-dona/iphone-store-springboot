package com.apple.iphonestorespringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apple.iphonestorespringboot.exception.CartItemException;
import com.apple.iphonestorespringboot.exception.UserException;
import com.apple.iphonestorespringboot.model.User;
import com.apple.iphonestorespringboot.response.ApiResponse;
import com.apple.iphonestorespringboot.service.CartItemService;

import com.apple.iphonestorespringboot.service.UserService;


@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {
    
// @Tag(name="Cart Management",description="find user cart,add item to cart")

@Autowired
private CartItemService cartItemService;

@Autowired
private UserService userService;

@DeleteMapping("/{cartItemId}")
public ResponseEntity<ApiResponse>deleteCartItem(@PathVariable Long cartItemId
    ,@RequestHeader("Authorization") String jwt ) throws UserException,CartItemException{
        
        User user=userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(),cartItemId);

        ApiResponse res=new ApiResponse();
        res.setMessage("item removed to cart");
        res.setStatus(true);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }




}
