package com.folksdevbank.controller;


import com.folksdevbank.dto.AccountDto;
import com.folksdevbank.dto.CreateAccountRequest;
import com.folksdevbank.dto.UpdateAccountRequest;
import com.folksdevbank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody CreateAccountRequest createAccountRequest){
        AccountDto accountDto=accountService.createAccount(createAccountRequest);

        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable("id") String id,@RequestBody UpdateAccountRequest updateAccountRequest){
        AccountDto accountDto=accountService.updateAccount(id,updateAccountRequest);

        return ResponseEntity.ok(accountDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AccountDto>>getAllAccounts(){

        List<AccountDto> accountDtoList=accountService.getAllAccounts();
        return ResponseEntity.ok(accountDtoList);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("id") String id){
        return  ResponseEntity.ok(accountService.getAccountDtoById(id));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable String id){
        accountService.deleteAccountById(id);
        return ResponseEntity.ok().build();
    }

    //PatchMappingi belirli bir kısmı güncellemek için kullandım.
    @PatchMapping("/withdraw/{id}/{amount}")
    public ResponseEntity<AccountDto> withdrawMoney(@PathVariable String id,@PathVariable double amount){

       return ResponseEntity.ok(accountService.withdrawMoney(id,amount));
    }

    @PatchMapping("addMoney/{id}/{amount}")
    public ResponseEntity<AccountDto> addMoney(@PathVariable String id,@PathVariable double amount){
        return ResponseEntity.ok(accountService.addMoney(id,amount));
    }


    //YUKARIDA İKİ TANE PATCHMAPPİNG İŞLEMİ YAPTIM VE AYNILAR.BU YÜZDEN AYRIŞTIRMA İŞLEMİ YAPMAK ZORUNDAYIM.


}
