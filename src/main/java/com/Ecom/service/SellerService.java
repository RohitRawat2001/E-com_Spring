package com.Ecom.service;

import com.Ecom.Exception.SellerException;
import com.Ecom.domain.AccountStatus;
import com.Ecom.modals.Seller;

import java.util.List;

public interface SellerService {

    Seller getSellerProfile(String jwt) throws Exception;
    Seller createSeller(Seller seller) throws Exception;
    Seller getSellerById(Long id) throws Exception;
    Seller getSellerByEmail(String email) throws Exception;
    List<Seller> getAllSellers(AccountStatus accountStatus);
    Seller updateSeller(Long id,Seller seller) throws Exception;
    void deleteSeller(Long id) throws Exception;
    Seller verifyEmail(String email,String otp) throws Exception;
    Seller updateSellerAccountStatus(Long sellerId,AccountStatus status) throws Exception;

}
