package com.apple.iphonestorespringboot.service;

import java.util.List;

import com.apple.iphonestorespringboot.exception.ProductException;
import com.apple.iphonestorespringboot.model.Review;
import com.apple.iphonestorespringboot.model.User;
import com.apple.iphonestorespringboot.request.ReviewRequest;

public interface ReviewService {
    
    public Review createReview(ReviewRequest req,User user) throws ProductException;
    public List<Review>getAllReviewByProductId(Long productId);

}
