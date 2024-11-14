package com.Ecom.service.Impl;

import com.Ecom.modals.Seller;
import com.Ecom.modals.SellerReport;
import com.Ecom.repository.SellerReportRepository;
import com.Ecom.service.SellerReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerReportServiceImpl implements SellerReportService {

     private final SellerReportRepository sellerReportRepository;

    @Override
    public SellerReport getSellerReport(Seller seller) {

        SellerReport bySellerId = sellerReportRepository.findBySellerId(seller.getId());
        if(bySellerId == null){
            SellerReport newReport = new SellerReport();
            newReport.setSeller(seller);
            return sellerReportRepository.save(newReport);
        }

        return bySellerId;
    }

    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) {
        return sellerReportRepository.save(sellerReport);
    }
}
