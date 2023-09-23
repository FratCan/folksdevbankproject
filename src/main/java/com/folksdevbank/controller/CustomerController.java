package com.folksdevbank.controller;


import com.folksdevbank.dto.CreateCustomerRequest;
import com.folksdevbank.dto.CustomerDto;
import com.folksdevbank.dto.UpdateCustomerRequest;
import com.folksdevbank.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //restcontroller json tipinde döner
@RequestMapping("/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerservice;

    @PostMapping("/create")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest){
        CustomerDto customerDto=customerservice.createCustomer(createCustomerRequest);
        return ResponseEntity.ok(customerDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){

        return ResponseEntity.ok(customerservice.getAllAccounts());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("id") String id){
        return ResponseEntity.ok(customerservice.getCustomerDtoById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable("id") String id){
            customerservice.deleteCustomerById(id);
            return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") String id , @RequestBody UpdateCustomerRequest updateCustomerRequest){
        return ResponseEntity.ok(customerservice.updateCustomer(id,updateCustomerRequest));
    }
}
