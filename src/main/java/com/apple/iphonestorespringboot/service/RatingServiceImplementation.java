package com.apple.iphonestorespringboot.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apple.iphonestorespringboot.exception.ProductException;
import com.apple.iphonestorespringboot.model.Product;
import com.apple.iphonestorespringboot.model.Rating;
import com.apple.iphonestorespringboot.model.User;
import com.apple.iphonestorespringboot.repository.RatingRepository;
import com.apple.iphonestorespringboot.request.RatingRequest;


@Service
public class RatingServiceImplementation implements RatingService{

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductService productService;

    public RatingServiceImplementation(RatingRepository ratingRepository, ProductService productService) {
        this.ratingRepository = ratingRepository;
        this.productService = productService;
    }

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        
        Product product=productService.findProductById(req.getProductId());
        Rating rating=new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductRatings(long productId) {
        return ratingRepository.getAllRatingByProductId(productId);
    }
    
}
