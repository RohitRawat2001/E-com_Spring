package com.Ecom.repository;

import com.Ecom.modals.Cart;
import com.Ecom.modals.CartItem;
import com.Ecom.modals.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    CartItem findByCartAndProductAndSize(Cart cart, Product product,String size);

}
