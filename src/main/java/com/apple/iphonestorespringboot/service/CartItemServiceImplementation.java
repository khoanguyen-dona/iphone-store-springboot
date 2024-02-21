package com.apple.iphonestorespringboot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apple.iphonestorespringboot.exception.CartItemException;
import com.apple.iphonestorespringboot.exception.UserException;
import com.apple.iphonestorespringboot.model.Cart;
import com.apple.iphonestorespringboot.model.CartItem;
import com.apple.iphonestorespringboot.model.Product;
import com.apple.iphonestorespringboot.model.User;
import com.apple.iphonestorespringboot.repository.CartItemRepository;
import com.apple.iphonestorespringboot.repository.CartRepository;
import com.apple.iphonestorespringboot.request.AddItemRequest;

@Service
public class CartItemServiceImplementation implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepository;

    public CartItemServiceImplementation(CartItemRepository cartItemRepository,UserService userService,CartRepository cartRepository ){
        this.cartItemRepository=cartItemRepository;
        this.userService=userService;
        this.cartRepository=cartRepository;
        
    }

    @Override
    public CartItem createCartItem(CartItem cartItem,AddItemRequest req) {
        cartItem.setQuantity(req.getQuantity());
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());

        CartItem createdCartItem=cartItemRepository.save(cartItem);
        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, long id, CartItem cartItem) throws CartItemException, UserException {

        CartItem item=findCartItemById(id);
        User user=userService.findUserById(item.getUserId());

        if(user.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity()*item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        
        CartItem cartItem=cartItemRepository.isCartItemExist(cart,product,size,userId);
        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem=findCartItemById(cartItemId);
        Cart cart=cartRepository.findByUserId(userId);
        User user=userService.findUserById(cartItem.getUserId());
        User reqUser=userService.findUserById(userId);
        if(user.getId().equals(reqUser.getId())){
            cartItemRepository.deleteById(cartItemId);
            int totalItem=cart.getTotalItem()-cartItem.getQuantity();
            double totalDiscountedPrice=cart.getTotalDiscountedPrice()-cartItem.getDiscountedPrice();
            double totalPrice=cart.getTotalPrice()-cartItem.getPrice();
            
            cart.setTotalPrice(totalPrice);
            cart.setTotalDiscountedPrice(totalDiscountedPrice);
            cart.setTotalItem(totalItem);
            cartRepository.save(cart);
        }else{
            throw new UserException("You can't remove another users item!");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem>opt=cartItemRepository.findById(cartItemId);
        if(opt.isPresent()){
            return opt.get();
        }throw new CartItemException("cartItem not found with id : "+cartItemId);
    }
    
}
