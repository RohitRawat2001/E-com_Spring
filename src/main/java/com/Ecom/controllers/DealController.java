package com.Ecom.controllers;

import com.Ecom.modals.Deal;
import com.Ecom.response.ApiResponse;
import com.Ecom.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/deals")
public class DealController {
    private final DealService dealService;

    @PostMapping
    public ResponseEntity<Deal> createDeals(@RequestBody Deal deals){
       Deal createdDeals = dealService.createDeal(deals);
       return new ResponseEntity<>(createdDeals, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Deal>> getDeals(@RequestBody Deal deals){
        List<Deal> createdDeals = dealService.getDeals();
        return new ResponseEntity<>(createdDeals, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Deal> updateDeal(@PathVariable Long id,@RequestBody Deal deal) throws Exception {
        Deal updatedDeal = dealService.updateDeal(deal,id);
        return new ResponseEntity<>(updatedDeal, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDeal(@PathVariable Long id) throws Exception {
        dealService.deleteDeal(id);

        ApiResponse response = new ApiResponse();
        response.setMessage("Deal deleted successfully");

        return new ResponseEntity<ApiResponse>(response,HttpStatus.ACCEPTED);
    }
}
