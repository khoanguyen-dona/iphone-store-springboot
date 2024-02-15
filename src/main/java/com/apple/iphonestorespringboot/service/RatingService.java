package com.apple.iphonestorespringboot.service;

import java.util.List;

import com.apple.iphonestorespringboot.exception.ProductException;
import com.apple.iphonestorespringboot.model.Rating;
import com.apple.iphonestorespringboot.model.User;
import com.apple.iphonestorespringboot.request.RatingRequest;

public interface RatingService {
    
    public Rating createRating(RatingRequest req,User user) throws ProductException;
    public List<Rating>getProductRatings(long productId);
}
