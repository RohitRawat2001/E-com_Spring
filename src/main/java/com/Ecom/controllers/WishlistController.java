package com.Ecom.controllers;

import com.Ecom.Exception.ProductException;
import com.Ecom.modals.Product;
import com.Ecom.modals.User;
import com.Ecom.modals.WishList;
import com.Ecom.service.ProductService;
import com.Ecom.service.UserService;
import com.Ecom.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<WishList> getWishlistByUserId(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        WishList wishList = wishlistService.getWishlistByUserId(user);
        return new ResponseEntity<>(wishList, HttpStatus.OK);
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<WishList> addProductToWishList(@PathVariable Long productId,
                                                         @RequestHeader("Authorization") String jwt) throws Exception {
        Product product = productService.findProductById(productId);
        User user = userService.findUserByJwtToken(jwt);
        WishList updatedWishList = wishlistService.addProductToWishlist(user,product);
        return new ResponseEntity<>(updatedWishList,HttpStatus.OK);
    }
}
