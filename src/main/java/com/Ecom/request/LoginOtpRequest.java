package com.Ecom.request;

import com.Ecom.domain.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginOtpRequest {
    private String email;
    private String otp;
    private USER_ROLE role;
}
