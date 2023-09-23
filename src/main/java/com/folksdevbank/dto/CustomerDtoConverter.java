package com.folksdevbank.dto;


import com.folksdevbank.model.Customer;
import org.springframework.stereotype.Component;

@Component //Kendi içinde işini yapar başka katmanlar tarafından kullanılır.
public class CustomerDtoConverter {

    //CUSTOMER VERİCEM BANA DTO'YA DÖNÜŞTÜRECEK.

    public CustomerDto convert(Customer customer){
        CustomerDto customerDto=new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setAddress(customer.getAddress());
        customerDto.setDateOfBirth(customer.getDateOfBirth());
        customerDto.setCity(CityDto.valueOf(customer.getCity().name()));

        return customerDto;
    }

}
