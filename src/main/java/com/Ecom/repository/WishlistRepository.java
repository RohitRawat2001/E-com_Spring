package com.Ecom.repository;

import com.Ecom.modals.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<WishList, Long> {

    WishList findByUserId(Long userId);

}
