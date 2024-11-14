package com.Ecom.controllers;

import com.Ecom.modals.Cart;
import com.Ecom.modals.Coupon;
import com.Ecom.modals.User;
import com.Ecom.service.CartService;
import com.Ecom.service.CouponService;
import com.Ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class AdminCouponController {

    private final CouponService couponService;
    private final UserService userService;
    private final CartService cartService;

    @PostMapping("/apply")
    public ResponseEntity<Cart> applyCoupon(@RequestParam String apply,
                                            @RequestParam String code,
                                            @RequestParam double orderValue,
                                            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Cart cart;
        if(apply.equals("true")){
            cart = couponService.applyCoupon(code,orderValue,user);
        }
        else{
            cart = couponService.removeCoupon(code,user);
        }
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon){
        Coupon createCoupon = couponService.createCoupon(coupon);
        return new ResponseEntity<>(createCoupon,HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long id) throws Exception {
       couponService.deleteCoupon(id);
       return new ResponseEntity<>("Coupon deleted Successfully!",HttpStatus.OK);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<Coupon>> getAllCoupons() throws Exception {
        List<Coupon> coupons = couponService.findAllCoupons();
        return  new ResponseEntity<>(coupons,HttpStatus.OK);
    }
}
