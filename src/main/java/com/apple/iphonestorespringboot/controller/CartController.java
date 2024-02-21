package com.apple.iphonestorespringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apple.iphonestorespringboot.exception.CartItemException;
import com.apple.iphonestorespringboot.exception.ProductException;
import com.apple.iphonestorespringboot.exception.UserException;
import com.apple.iphonestorespringboot.model.Cart;
import com.apple.iphonestorespringboot.model.User;
import com.apple.iphonestorespringboot.request.AddItemRequest;
import com.apple.iphonestorespringboot.response.ApiResponse;
import com.apple.iphonestorespringboot.service.CartService;
import com.apple.iphonestorespringboot.service.UserService;


@RestController
@RequestMapping("/api/cart")
// @Tag(name="Cart Management",description="find user cart,add item to cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    // "find cart by user id")
    @GetMapping("/")
    public ResponseEntity<Cart>findUserCart(@RequestHeader("Authorization")String jwt) throws UserException{
        User user=userService.findUserProfileByJwt(jwt);
        Cart cart=cartService.findUserCart(user.getId());
        return new ResponseEntity<Cart>(cart,HttpStatus.OK);
    }

    @PutMapping("/add")
    // @"add item to cart")
    public ResponseEntity<ApiResponse>addItemToCart(@RequestBody AddItemRequest req
        ,@RequestHeader("Authorization")String jwt) throws UserException,CartItemException, ProductException{
            User user=userService.findUserProfileByJwt(jwt);
            cartService.addCartItem(user.getId(),req);
            ApiResponse res=new ApiResponse();
            res.setMessage("item added to cart");
            res.setStatus(true);
            return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);
        }

//     @DeleteMapping("/cart_items/{cartItemId}")
//     public ResponseEntity<ApiResponse>removeItemFromCart(@RequestBody RemoveItemRequest req
//         ,@RequestHeader("Authorization")String jwt ) throws UserException,CartItemException{
            
//         }
}
