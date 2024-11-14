package com.Ecom.controllers;

import com.Ecom.modals.*;
import com.Ecom.response.ApiResponse;
import com.Ecom.response.PaymentLinkResponse;
import com.Ecom.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final SellerService sellerService;
    private final SellerReportService sellerReportService;
    private final TransactionService transactionService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> paymentSuccessHandler(@PathVariable String paymentId, @RequestParam String paymentLinkId,
                                                             @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        PaymentLinkResponse paymentLinkResponse;

        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);

        Boolean paymentSuccess = paymentService.ProceedPaymentOrder(paymentOrder,paymentId,paymentLinkId);

        if(paymentSuccess){
       for (Order order :paymentOrder.getOrders()){
           transactionService.createTransaction(order);
           Seller seller = sellerService.getSellerById(order.getSellerId());
           SellerReport report = sellerReportService.getSellerReport(seller);
           report.setTotalOrders(report.getTotalOrders()+1);
           report.setTotalEarnings(report.getTotalEarnings()+order.getTotalSellingPrice());
           report.setTotalSales(report.getTotalSales()+order.getOrderItems().size());
           sellerReportService.updateSellerReport(report);
       }
        }
        ApiResponse response = new ApiResponse();
        response.setMessage("Payment Successful");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}