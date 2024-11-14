package com.Ecom.service;

import com.Ecom.modals.Product;
import com.Ecom.modals.User;
import com.Ecom.modals.WishList;

public interface WishlistService {

    WishList createWishlist(User user);
    WishList getWishlistByUserId(User user);
    WishList addProductToWishlist(User user, Product product);

}
