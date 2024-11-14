package com.Ecom.controllers;

import com.Ecom.modals.Home;
import com.Ecom.modals.HomeCategory;
import com.Ecom.repository.HomeCategoryRepository;
import com.Ecom.service.HomeCategoryService;
import com.Ecom.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeCategoryController {

    private final HomeCategoryService homeCategoryService;
    private final HomeService homeService;

    @PostMapping("/home/categories")
    public ResponseEntity<Home> createHomeCategories(@RequestBody List<HomeCategory> homeCategories){
        List<HomeCategory> categories = homeCategoryService.createCategories(homeCategories);
        Home home = homeService.createHomePageData(categories);
        return new ResponseEntity<>(home, HttpStatus.ACCEPTED);
    }

    @GetMapping("/admin/home-categories")
    public ResponseEntity<List<HomeCategory>> getHomeCategories( ){
        List<HomeCategory> categories = homeCategoryService.getAllHomeCategories();
        return ResponseEntity.ok(categories);
    }

    @PatchMapping("/admin/home-category/{id}")
    public ResponseEntity<HomeCategory> updateHomeCategory(@PathVariable Long id,@RequestBody HomeCategory homeCategory) throws Exception {
          HomeCategory updatedCategories  = homeCategoryService.updateHomeCategory(homeCategory,id);
          return ResponseEntity.ok(updatedCategories);
    }
}
