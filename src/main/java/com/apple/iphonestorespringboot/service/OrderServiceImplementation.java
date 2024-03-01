package com.apple.iphonestorespringboot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apple.iphonestorespringboot.exception.OrderException;
import com.apple.iphonestorespringboot.model.Address;
import com.apple.iphonestorespringboot.model.Cart;
import com.apple.iphonestorespringboot.model.CartItem;
import com.apple.iphonestorespringboot.model.Order;
import com.apple.iphonestorespringboot.model.OrderItem;
import com.apple.iphonestorespringboot.model.User;
import com.apple.iphonestorespringboot.repository.CartRepository;
import com.apple.iphonestorespringboot.repository.OrderItemRepository;
import com.apple.iphonestorespringboot.repository.AddressRepository;
import com.apple.iphonestorespringboot.repository.UserRepository;
import com.apple.iphonestorespringboot.repository.OrderRepository;


@Service
public class OrderServiceImplementation implements OrderService{

    private OrderRepository orderRepository;
    private CartService cartService;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private OrderItemService orderItemService;
    private OrderItemRepository orderItemRepository;
    private CartRepository cartRepository;
   

    public OrderServiceImplementation(OrderRepository orderRepository, CartService cartService,
            AddressRepository addressRepository, UserRepository userRepository, OrderItemService orderItemService,
            OrderItemRepository orderItemRepository,CartRepository cartRepository ) {

        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.orderItemService = orderItemService;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository=cartRepository;
    }

    @Override
    public Order createOrder(User user, Address shippingAddress) {
        
        shippingAddress.setUser(user);
        Address address=addressRepository.save(shippingAddress);
        user.getAddress().add(address);
        userRepository.save(user);

        Cart cart=cartService.findUserCart(user.getId());
        List<OrderItem>orderItems=new ArrayList<>();

        for(CartItem item: cart.getCartItems()){
            OrderItem orderItem=new OrderItem();

            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setDiscountedPrice(item.getDiscountedPrice());

            OrderItem createdOrderItem=orderItemRepository.save(orderItem);
            orderItems.add(createdOrderItem);
        }

        Order createdOrder=new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscount(cart.getDiscount());   
        createdOrder.setTotalItem(cart.getTotalItem());

        createdOrder.setShippingAddress(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.getPaymentDetails().setStatus("PENDING");
        createdOrder.setCreatedAt(LocalDateTime.now());

        Order savedOrder=orderRepository.save(createdOrder);
        for(OrderItem item:orderItems){
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }
        // after createorder we refresh cart of user
        cart.setTotalPrice(0);
        cart.setTotalItem(0);
        cart.setTotalDiscountedPrice(0);
        cart.setCartItems(null);
        cart.setDiscount(0);
        cartRepository.save(cart);



        return savedOrder;
    }


    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order>opt=orderRepository.findById(orderId);
        if(opt.isPresent()){
            return opt.get();
        }throw new OrderException("order not exist with id "+orderId);
    }

    @Override
    public List<Order> usersOrderHistory(long userId) {
        List<Order> orders=orderRepository.getUserOrders(userId);
        return orders;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setStatus("COMPLETED");
        return order;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        return orderRepository.save(order);
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        order.setOrderStatus("CANCELLED");
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order=findOrderById(orderId);
        orderRepository.deleteById(orderId);    
    }

}
    

