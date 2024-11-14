package com.Ecom.repository;

import com.Ecom.modals.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findBySellerId(Long id);

    @Query("SELECT p FROM Product p WHERE (:query IS NULL OR " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.category.name) LIKE LOWER(CONCAT('%', :query, '%'))))")
    List<Product> searchProduct(@Param("query") String query);

}