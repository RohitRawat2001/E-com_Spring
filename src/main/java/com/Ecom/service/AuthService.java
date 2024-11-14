package com.Ecom.service;

import com.Ecom.domain.USER_ROLE;
import com.Ecom.request.LoginRequest;
import com.Ecom.response.AuthResponse;
import com.Ecom.response.SignUpRequest;

public interface AuthService {

    void sendLoginOtp(String email, USER_ROLE role) throws Exception;

    String createUser(SignUpRequest req) throws Exception;

    AuthResponse signIn(LoginRequest req);
}
