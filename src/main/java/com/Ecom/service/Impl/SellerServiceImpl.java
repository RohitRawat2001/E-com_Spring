package com.Ecom.service.Impl;

import com.Ecom.config.JwtProvider;
import com.Ecom.domain.AccountStatus;
import com.Ecom.domain.USER_ROLE;
import com.Ecom.modals.Address;
import com.Ecom.modals.Seller;
import com.Ecom.repository.AddressRepository;
import com.Ecom.repository.SellerRepository;
import com.Ecom.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    @Override
    public Seller getSellerProfile(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);

        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws Exception {
        Seller sellerExist = sellerRepository.findByEmail(seller.getEmail());
        if(sellerExist != null){
            throw new Exception("Seller already exists,with this Email, Use Another Email Address");
        }
        Address savedAddress = addressRepository.save(seller.getPickupAddress());

        Seller newSeller = new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setPickupAddress(savedAddress);
        newSeller.setGSTIN(seller.getGSTIN());
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setMobile(seller.getMobile());
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetails(seller.getBusinessDetails());

         return sellerRepository.save(newSeller) ;
    }

    @Override
    public Seller getSellerById(Long id) throws Exception {
        return sellerRepository.findById(id)
                .orElseThrow(()-> new Exception("Seller not found with id " + id));
    }

    @Override
    public Seller getSellerByEmail(String email) throws Exception {
        Seller seller = sellerRepository.findByEmail(email);
        if(seller == null){
            throw new Exception("Seller is not found...");
        }
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus accountStatus) {
        return sellerRepository.findByAccountStatus(accountStatus);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {
        Seller existingSeller = getSellerById(id);

        if(seller.getSellerName() != null){
            existingSeller.setSellerName(seller.getSellerName());
        }
        if(seller.getMobile() != null){
            existingSeller.setMobile(seller.getMobile());
        }
        if(seller.getEmail() != null){
            existingSeller.setEmail(seller.getEmail());
        }
        if(seller.getBusinessDetails() != null && seller.getBusinessDetails().getBusinessName() != null){
            existingSeller.getBusinessDetails().setBusinessName(seller.getBusinessDetails().getBusinessName());
        }
        if(seller.getBankDetails() != null && seller.getBankDetails().getAccountHolderName() != null
        && seller.getBankDetails().getIfscCode() != null && seller.getBankDetails().getAccountNumber() != null){
            existingSeller.getBankDetails().setAccountHolderName(seller.getBankDetails().getAccountHolderName());
            existingSeller.getBankDetails().setAccountNumber(seller.getBankDetails().getAccountNumber());
            existingSeller.getBankDetails().setIfscCode(seller.getBankDetails().getIfscCode());
        }

        if(seller.getPickupAddress() != null && seller.getPickupAddress().getAddress() != null
        && seller.getPickupAddress().getMobile() !=null
        && seller.getPickupAddress().getCity() != null
        && seller.getPickupAddress().getState() != null){
            existingSeller.getPickupAddress().setAddress(seller.getPickupAddress().getAddress());
            existingSeller.getPickupAddress().setCity(seller.getPickupAddress().getCity());
            existingSeller.getPickupAddress().setState(seller.getPickupAddress().getState());
            existingSeller.getPickupAddress().setMobile(seller.getPickupAddress().getMobile());
            existingSeller.getPickupAddress().setPinCode(seller.getPickupAddress().getPinCode());
        }

        if(seller.getGSTIN() != null){
            existingSeller.setGSTIN(seller.getGSTIN());
        }

        return sellerRepository.save(existingSeller);
    }

    @Override
    public void deleteSeller(Long id) throws Exception {
      Seller seller = getSellerById(id);
      sellerRepository.delete(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) throws Exception {
        Seller sel = getSellerByEmail(email);
        sel.setEmailVerified(true);
        return sellerRepository.save(sel);
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception {
        Seller sel = getSellerById(sellerId);
        sel.setAccountStatus(status);
        return sellerRepository.save(sel);
    }
}
