package com.Ecom.repository;

import com.Ecom.modals.Category;
import com.Ecom.request.CreateProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByCategoryId(String categoryId);
}
