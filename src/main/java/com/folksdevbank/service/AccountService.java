package com.folksdevbank.service;

import com.folksdevbank.dto.*;
import com.folksdevbank.model.Account;
import com.folksdevbank.model.Customer;
import com.folksdevbank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter accountDtoConverter;

    public AccountDto createAccount(CreateAccountRequest createAccountRequest){

        Customer customer=customerService.getCustomerById(createAccountRequest.getCustomerId());

        if (customer.getId()==null || customer.getId().trim().equals("")){
            throw new RuntimeException("Customer Not Found!!");
            //return AccountDto.builder().build();
            //return new AccountDto yukardakiyle aynı anlamda
        }

        Account account=Account.builder()
                .id(createAccountRequest.getId())
                .balance(createAccountRequest.getBalance())
                        .customerId(createAccountRequest.getCustomerId())
                                .currency(createAccountRequest.getCurrency())
                                        .city(createAccountRequest.getCity()).build();

        accountRepository.save(account);

        return accountDtoConverter.convert(account);
    }




    public AccountDto updateAccount(String id,UpdateAccountRequest updateAccountRequest){

        Customer customer=customerService.getCustomerById(updateAccountRequest.getCustomerId());


        if (customer.getId()==null || customer.getId().trim().equals("")){
            //throw new RuntimeException("Customer Not Updated!!");
            return new AccountDto();
        }

        Optional<Account> accountOptional= accountRepository.findById(id);

        accountOptional.ifPresent(account -> {
            account.setCurrency(updateAccountRequest.getCurrency());
            account.setBalance(updateAccountRequest.getBalance());
            account.setCity(updateAccountRequest.getCity());
            account.setCustomerId(updateAccountRequest.getCustomerId());
            accountRepository.save(account);
        });

        return accountOptional.map(accountDtoConverter::convert).orElse(new AccountDto());
    }



    public List<AccountDto> getAllAccounts(){
        List<Account> accountList=accountRepository.findAll();

        /*
        List<AccountDto> accountDtoList=new ArrayList<>();

        for (Account account:accountList){
            accountDtoList.add(accountDtoConverter.convert(account));
        }

        return accountDtoList;
        */

        return accountList.stream().map(accountDtoConverter::convert).collect(Collectors.toList());

        // YUKARIDAKİ 2 SİDE AYNI İŞLEM.
    }



    public AccountDto getAccountDtoById(String id){

        Optional<Account> accountOptional=accountRepository.findById(id);

        return accountOptional.map(accountDtoConverter::convert).orElse(new AccountDto());
    }



    public void deleteAccountById(String id){
        accountRepository.deleteById(id);
    }



    public AccountDto withdrawMoney(String id,double amount){
        Optional<Account> accountOptional= accountRepository.findById(id);


        accountOptional.ifPresent(account -> {
            if(account.getBalance()>amount){
                account.setBalance(account.getBalance()-amount);
                accountRepository.save(account);
            }else{
                System.out.println("Insufficient funds -> accountId: "+id+" balance "+account.getBalance()+" amount "+amount);
            }
        });

        return accountOptional.map(accountDtoConverter::convert).orElse(new AccountDto());
    }

    public AccountDto addMoney(String id,double amount){
        Optional<Account> accountOptional=accountRepository.findById(id);

        accountOptional.ifPresent(account -> {

            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);
        });

        return accountOptional.map(accountDtoConverter::convert).orElse(new AccountDto());
    }

}
