package com.Ecom.service;

import com.Ecom.modals.Cart;
import com.Ecom.modals.CartItem;
import com.Ecom.modals.Product;
import com.Ecom.modals.User;

public interface CartService {

    CartItem addCartItem(User user, Product product,String size,int quantity);
    Cart findUserCart(User user);
}
