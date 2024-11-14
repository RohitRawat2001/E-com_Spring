package com.Ecom.controllers;

import com.Ecom.domain.USER_ROLE;
import com.Ecom.repository.UserRepository;
import com.Ecom.request.LoginOtpRequest;
import com.Ecom.request.LoginRequest;
import com.Ecom.response.ApiResponse;
import com.Ecom.response.AuthResponse;
import com.Ecom.response.SignUpRequest;
import com.Ecom.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>  createUserHandler(@RequestBody SignUpRequest req) throws Exception {

        String jwt = authService.createUser(req);

        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setMessage("Customer Register Success");
        response.setRole(USER_ROLE.ROLE_CUSTOMER);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send/login-signup-otp")
    public ResponseEntity<ApiResponse> sendOtpHandler(@RequestBody LoginOtpRequest req) throws Exception {

         authService.sendLoginOtp(req.getEmail(),req.getRole());

        ApiResponse response = new ApiResponse();

        response.setMessage("otp send successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> sendOtpHandler(@RequestBody LoginRequest req) throws Exception {

        AuthResponse authResponse = authService.signIn(req);

        return ResponseEntity.ok(authResponse);
    }


}
