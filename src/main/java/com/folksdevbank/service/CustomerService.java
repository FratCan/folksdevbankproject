package com.folksdevbank.service;



import com.folksdevbank.dto.CreateCustomerRequest;
import com.folksdevbank.dto.CustomerDto;
import com.folksdevbank.dto.CustomerDtoConverter;
import com.folksdevbank.dto.UpdateCustomerRequest;
import com.folksdevbank.model.City;
import com.folksdevbank.model.Customer;
import com.folksdevbank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;
    public CustomerDto createCustomer(CreateCustomerRequest createCustomerRequest){
        Customer customer=new Customer();
        customer.setId(createCustomerRequest.getId());
        customer.setName(createCustomerRequest.getName());
        customer.setAddress(s);
        customer.setDateOfBirth(createCustomerRequest.getDateOfBirth());
        customer.setCity(City.valueOf(createCustomerRequest.getCity().name()));

        customerRepository.save(customer);
/*
        CustomerDto customerDto=new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setAddress(customer.getAddress());
        customerDto.setDateOfBirth(customer.getDateOfBirth());
        customerDto.setCity(CityDto.valueOf(customer.getCity().name()));

        return customerDto;

 */
        return customerDtoConverter.convert(customer);
    }

    public List<CustomerDto> getAllAccounts(){
        List<Customer> customerList=customerRepository.findAll();

        List<CustomerDto> customerDtoList=new ArrayList<>();

        for (Customer customer:customerList){
            customerDtoList.add(customerDtoConverter.convert(customer));
        }
        return customerDtoList;
    }

    public CustomerDto getCustomerDtoById(String id) {
        Optional<Customer> customerOptional=customerRepository.findById(id);
        //Yukarıdaki işlemde customeroptional'ın içerisindeki customer'ı dışarı çıkarttım
         return customerOptional.map(customerDtoConverter::convert).orElse(new CustomerDto());
        //Yukarda customerdto'ya çeviririm.
    }

    public void deleteCustomerById(String id){

        customerRepository.deleteById(id);
    }

    public CustomerDto updateCustomer(String id, UpdateCustomerRequest updateCustomerRequest) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        //Yukarıdaki işlemde customeroptional'ın içerisindeki customer'ı dışarı çıkarttım.

                customerOptional.ifPresent(customer -> {
                customer.setName(updateCustomerRequest.getName());
                customer.setCity(City.valueOf(updateCustomerRequest.getCity().name()));
                customer.setAddress(updateCustomerRequest.getAddress());
                customer.setDateOfBirth(updateCustomerRequest.getDateOfBirth());

                customerRepository.save(customer);
                });

        return customerOptional.map(customerDtoConverter::convert).orElse(new CustomerDto());
        //Yukarda customerdto'ya çeviririm.
    }


    //BU METODU AccountService de kullanıcam.
    //AccountService erişsin diye protected yaptım.
    protected Customer getCustomerById(String id){

        return customerRepository.findById(id).orElse(new Customer());
    }

}
