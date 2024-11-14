package com.Ecom.controllers;

import com.Ecom.modals.Product;
import com.Ecom.modals.Review;
import com.Ecom.modals.User;
import com.Ecom.request.CreateReviewRequest;
import com.Ecom.response.ApiResponse;
import com.Ecom.service.ProductService;
import com.Ecom.service.ReviewService;
import com.Ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable Long productId){
    List<Review> reviews = reviewService.getReviewByProductId(productId);
    return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<Review> writeReview(@RequestBody CreateReviewRequest req,
                                              @PathVariable Long productId,
                                              @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Product product = productService.findProductById(productId);

        Review review = reviewService.createReview(req,user,product);
        return new ResponseEntity<>(review,HttpStatus.OK);
    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> updateReview(@RequestBody CreateReviewRequest req,
                                               @PathVariable Long reviewId,
                                               @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Review review = reviewService.updateReview(reviewId,req.getReviewText(),req.getReviewRating(),user.getId());
        return new ResponseEntity<>(review,HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long reviewId,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        reviewService.deleteReview(reviewId,user.getId());
        ApiResponse res = new ApiResponse();
        res.setMessage("Review deleted successfully");

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

}