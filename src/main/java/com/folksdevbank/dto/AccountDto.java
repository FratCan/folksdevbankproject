package com.folksdevbank.dto;

import com.folksdevbank.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private String id;
    private String customerId;
    private double balance;
    private CurrencyDto currency;

}
