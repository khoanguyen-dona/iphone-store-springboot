package com.apple.iphonestorespringboot.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apple.iphonestorespringboot.exception.ProductException;
import com.apple.iphonestorespringboot.model.Product;
import com.apple.iphonestorespringboot.model.Review;
import com.apple.iphonestorespringboot.model.User;
import com.apple.iphonestorespringboot.repository.ProductRepository;
import com.apple.iphonestorespringboot.repository.ReviewRepository;
import com.apple.iphonestorespringboot.request.ReviewRequest;


@Service
public class ReviewServiceImplementation implements ReviewService{
    

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    
    public ReviewServiceImplementation() {
    }

    public ReviewServiceImplementation(ReviewRepository reviewRepository,
            ProductService productService, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        
        Product product=productService.findProductById(req.getProductId());

        Review review=new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(req.getReview());
        review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReviewByProductId(Long productId) {
        
        return reviewRepository.getAllReviewByProductId(productId);
    }
    
}
