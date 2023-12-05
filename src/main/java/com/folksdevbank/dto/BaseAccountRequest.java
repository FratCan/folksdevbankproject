package com.folksdevbank.dto;


import com.folksdevbank.model.City;
import com.folksdevbank.model.Currency;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseAccountRequest {

    private String customerId;
    private double balance;
    private City city;
    private Currency currency;

}
