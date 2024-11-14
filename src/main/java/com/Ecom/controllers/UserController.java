package com.Ecom.controllers;

import com.Ecom.modals.User;
import com.Ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/profile")
    public ResponseEntity<User> getUserByJwt(@RequestHeader("Authorization") String jwt) throws Exception{
        User userByJwtToken = userService.findUserByJwtToken(jwt);
        return ResponseEntity.ok(userByJwtToken);
    }
}
