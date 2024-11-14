package com.Ecom.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewRequest {
    private String reviewText;
    private double reviewRating;
    private List<String> productImages;
}
