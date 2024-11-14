package com.Ecom.service;

import com.Ecom.modals.Order;
import com.Ecom.modals.Seller;
import com.Ecom.modals.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Order order);
    List<Transaction> getTransactionsBySellerId(Seller seller);
    List<Transaction> getAllTransactions();

}
