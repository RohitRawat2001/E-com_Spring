package com.Ecom.service.Impl;

import com.Ecom.config.JwtProvider;
import com.Ecom.domain.USER_ROLE;
import com.Ecom.modals.Cart;
import com.Ecom.modals.Seller;
import com.Ecom.modals.User;
import com.Ecom.modals.VerificationCode;
import com.Ecom.repository.CartRepository;
import com.Ecom.repository.SellerRepository;
import com.Ecom.repository.UserRepository;
import com.Ecom.repository.VerificationCodeRepository;
import com.Ecom.request.LoginRequest;
import com.Ecom.response.AuthResponse;
import com.Ecom.response.SignUpRequest;
import com.Ecom.service.AuthService;
import com.Ecom.service.EmailService;
import com.Ecom.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final CartRepository cartRepository;

    private final JwtProvider jwtProvider;

    private final VerificationCodeRepository verificationCodeRepository;

    private final EmailService emailService;

    private final CustomUserServiceImpl customUserService;

    private final SellerRepository sellerRepository;

    @Override
    public void sendLoginOtp(String email,USER_ROLE role) throws Exception {
        String SIGNING_PREFIX = "signin_";

        if(email.startsWith(SIGNING_PREFIX)){
            email = email.substring(SIGNING_PREFIX.length());

            if(role.equals(USER_ROLE.ROLE_SELLER)){
                Seller seller = sellerRepository.findByEmail(email);
                if(seller == null){
                    throw new Exception("Seller not exist with provided email");
                }

            }else {
                User user = userRepository.findByEmail(email);
                if(user ==  null){
                    throw  new Exception("User not exist with provided email");
                }
            }


        }

        VerificationCode isExist = verificationCodeRepository.findByEmail(email);

        if(isExist != null){
            verificationCodeRepository.delete(isExist);
        }

        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        verificationCodeRepository.save(verificationCode);

        String subject = "E-commerce Account Login/Signup OTP Verification";
        String text = "Dear User,\n\nThank you for choosing our e-commerce platform. To proceed with your login or signup, please use the following One-Time Password (OTP): " + otp + "\n\nThis OTP is valid for a limited time and should not be shared with anyone.\n\nIf you did not request this code, please ignore this message.\n\nBest regards,\nThe E-commerce Team";


        emailService.sendVerificationOtpEmail(email,otp,subject,text);
    }

    @Override
    public String createUser(SignUpRequest req) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());

        if(verificationCode ==  null || !verificationCode.getOtp().equals(req.getOtp())){
            throw new Exception("wrong verification OTP...");
        }

        User user = userRepository.findByEmail(req.getEmail());

        if(user == null){
            User createdUser = new User();
            createdUser.setEmail(req.getEmail());
            createdUser.setFullName(req.getFullName());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobile("1234567890");
            createdUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user = userRepository.save(createdUser);

            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(),null,authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }

    @Override
    public AuthResponse signIn(LoginRequest req) {
        String username = req.getEmail();
        String otp = req.getOtp();

        Authentication authentication = authenticate(username,otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login success");

        Collection<? extends  GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        authResponse.setRole(USER_ROLE.valueOf(roleName));

        return authResponse;
    }

    private Authentication authenticate(String username, String otp) {
        UserDetails userDetails = customUserService.loadUserByUsername(username);

        String SELLER_PREFIX = "seller_";
        if(username.startsWith(SELLER_PREFIX)){
           username = username.substring(SELLER_PREFIX.length());

        }

        if(userDetails == null){
            throw new BadCredentialsException("invalid username");
        }

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);

        if(verificationCode == null || !verificationCode.getOtp().equals(otp)){
            throw new BadCredentialsException("wrong otp");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}