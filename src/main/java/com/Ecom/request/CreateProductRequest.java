package com.Ecom.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    private String title;
    private String description;
    private int mrpPrice;
    private int SellingPrice;
    private String color;
    private List<String> images;
    private String category;
    private String category2;
    private String category3;

    private String sizes;
}
