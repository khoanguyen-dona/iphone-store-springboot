package com.apple.iphonestorespringboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apple.iphonestorespringboot.exception.ProductException;
import com.apple.iphonestorespringboot.exception.UserException;
import com.apple.iphonestorespringboot.model.Review;
import com.apple.iphonestorespringboot.model.User;
import com.apple.iphonestorespringboot.request.ReviewRequest;
import com.apple.iphonestorespringboot.service.ReviewService;
import com.apple.iphonestorespringboot.service.UserService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Review>createReview(@RequestBody ReviewRequest req
        ,@RequestHeader("Authorization")String jwt) throws UserException,ProductException{
           
            User user=userService.findUserProfileByJwt(jwt);
            Review review=reviewService.createReview(req,user);
            return new ResponseEntity<>(review,HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>>getProductReview(@PathVariable Long productId) throws UserException,ProductException{

        List<Review>reviews=reviewService.getAllReviewByProductId(productId);
        return new ResponseEntity<>(reviews,HttpStatus.ACCEPTED);
    }
}
