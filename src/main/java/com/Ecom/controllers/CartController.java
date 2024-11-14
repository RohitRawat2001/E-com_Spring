package com.Ecom.controllers;

import com.Ecom.modals.Cart;
import com.Ecom.modals.CartItem;
import com.Ecom.modals.Product;
import com.Ecom.modals.User;
import com.Ecom.request.AddItemRequest;
import com.Ecom.response.ApiResponse;
import com.Ecom.service.CartItemService;
import com.Ecom.service.CartService;
import com.Ecom.service.ProductService;
import com.Ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findUserCart(user);
        System.out.println("cart - "+cart.getUser().getEmail());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cardItem/{id}")
    public ResponseEntity<CartItem> findCartItemById(@PathVariable Long id) throws Exception {
//        User user = userService.findUserByJwtToken(jwt);
        CartItem cartItemById = cartItemService.findCartItemById(id);
        return new ResponseEntity<>(cartItemById,HttpStatus.OK);
    }


    @PutMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddItemRequest req, @RequestHeader("Authorization") String jwt)throws Exception{
        User user = userService.findUserByJwtToken(jwt);

        Product product = productService.findProductById(req.getProductId());

        CartItem cartItem = cartService.addCartItem(user,product,req.getSize(),req.getQuantity());

        ApiResponse response = new ApiResponse();
        response.setMessage("Item Added to Cart Successfully");

        return new ResponseEntity<>(cartItem,HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/item/{cartItemId}")
    public  ResponseEntity<ApiResponse> deleteCartHandler(@PathVariable Long cartItemId,@RequestHeader ("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        cartItemService.removeCartItem(user.getId(),cartItemId);

        ApiResponse response = new ApiResponse();
        response.setMessage("Item deleted successfully");

        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItem> updateCartHandler(@PathVariable Long cartItemId,@RequestBody CartItem cartItem,@RequestHeader ("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        CartItem updatedCartItem = null;
        if(cartItem.getQuantity() > 0){
            updatedCartItem = cartItemService.updateCartItem(user.getId(),cartItemId,cartItem);
        }
        return new ResponseEntity<>(updatedCartItem,HttpStatus.ACCEPTED);
    }


}
