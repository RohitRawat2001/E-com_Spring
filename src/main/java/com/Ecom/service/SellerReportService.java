package com.Ecom.service;

import com.Ecom.modals.Seller;
import com.Ecom.modals.SellerReport;

public interface SellerReportService {

SellerReport getSellerReport(Seller seller);
SellerReport updateSellerReport(SellerReport sellerReport);

}
