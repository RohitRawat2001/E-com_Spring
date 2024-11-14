package com.Ecom.modals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {

    private String accountNumber;
    private String accountHolderName;
    private String ifscCode;
}