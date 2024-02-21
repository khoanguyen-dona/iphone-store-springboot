package com.apple.iphonestorespringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apple.iphonestorespringboot.exception.ProductException;
import com.apple.iphonestorespringboot.model.Cart;
import com.apple.iphonestorespringboot.model.CartItem;
import com.apple.iphonestorespringboot.model.Product;
import com.apple.iphonestorespringboot.model.User;
import com.apple.iphonestorespringboot.repository.CartItemRepository;
import com.apple.iphonestorespringboot.repository.CartRepository;
import com.apple.iphonestorespringboot.request.AddItemRequest;

@Service
public class CartServiceImplementation implements CartService{

    @Autowired
    private CartRepository cartRepository;

   
    
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductService productService;

    public CartServiceImplementation(CartRepository cartRepository, CartItemService cartItemService,ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
        
    }

    @Override
    public Cart createCart(User user){
        Cart cart=new Cart();
        cart.setUser(user);
        
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException{

        Cart cart=cartRepository.findByUserId(userId);
        Product product=productService.findProductById(req.getProductId());
        CartItem isPresent=cartItemService.isCartItemExist(cart,product,req.getSize(),userId);
        if(isPresent==null){
            CartItem cartItem=new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);

            // int price=req.getQuantity()*product.getDiscountedPrice();
            // cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

            CartItem createdCartItem=cartItemService.createCartItem(cartItem,req);
            cart.getCartItems().add(createdCartItem);
            int totalItem=cart.getTotalItem()+req.getQuantity();
            double totalPrice=cart.getTotalPrice()+(req.getPrice()*req.getQuantity());
            double totalDiscountedPrice=cart.getTotalDiscountedPrice()+(req.getDiscountedPrice()*req.getQuantity());
            
            cart.setTotalDiscountedPrice(totalDiscountedPrice);
            cart.setTotalPrice(totalPrice);
            cart.setTotalItem(totalItem);

            
            cartRepository.save(cart);
        }
        return "Item added to cart";
    }

    @Override
    public Cart findUserCart(Long userId){
        Cart cart=cartRepository.findByUserId(userId);

        double totalPrice=0;
        double totalDiscountedPrice=0;
        int totalItem=0;

        for(CartItem cartItem :cart.getCartItems()){
            totalPrice=totalPrice+cartItem.getPrice();
            totalDiscountedPrice=totalDiscountedPrice+cartItem.getDiscountedPrice();
            totalItem=totalItem+cartItem.getQuantity();
        }
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
        cart.setDiscount(totalPrice-totalDiscountedPrice);
        return cartRepository.save(cart);
    }


}
