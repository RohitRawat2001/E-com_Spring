package com.Ecom.service;

import com.Ecom.modals.Product;
import com.Ecom.modals.Review;
import com.Ecom.modals.User;
import com.Ecom.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {

 Review createReview(CreateReviewRequest req, User user, Product product);
 List<Review> getReviewByProductId(Long productId);
 Review updateReview(Long reviewId,String reviewText,double rating,Long userId) throws Exception;
 void deleteReview(Long reviewId,Long userId) throws Exception;
 Review getReviewById(Long reviewId) throws Exception;

}
