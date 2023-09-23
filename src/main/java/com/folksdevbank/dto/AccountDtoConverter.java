package com.folksdevbank.dto;

import com.folksdevbank.model.Account;
import org.springframework.stereotype.Component;


@Component
public class AccountDtoConverter {

    public AccountDto convert(Account account){

        AccountDto accountDto=new AccountDto();

        accountDto.setId(account.getId());
        accountDto.setBalance(account.getBalance());
        accountDto.setCurrency(CurrencyDto.valueOf(account.getCurrency().name()));
        accountDto.setCustomerId(account.getCustomerId());

        return accountDto;
    }


}
