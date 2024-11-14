package com.Ecom.repository;

import com.Ecom.modals.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    // Find cart by user ID
    Cart findByUserId(Long userId);
}
