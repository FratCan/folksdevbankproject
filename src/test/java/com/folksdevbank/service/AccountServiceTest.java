package com.folksdevbank.service;

import com.folksdevbank.dto.*;
import com.folksdevbank.model.Account;
import com.folksdevbank.model.City;
import com.folksdevbank.model.Currency;
import com.folksdevbank.model.Customer;
import com.folksdevbank.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    private AccountService accountService;
    private  AccountRepository accountRepository;
    private  CustomerService customerService;
    private  AccountDtoConverter accountDtoConverter;


    //Test seneryoları calismadan once before calisir gerekli ayarlamalar setupda yapilir.
    @Before
    public void setUp() throws Exception {
        //Her senaryoda ortak kullanılan seyler yazilir.
        //Hangi class kullanılacaksa onun objesi yaratılır.
        //account repo nun objesini oluşturamam çünkü interface oluştursam bile onunda parametreleri var ve bu yöntem uzun sürer.
        //bunun yerine MOCKİTO kullanırım bu yalancı service yaratmamı saglar.
        accountRepository= Mockito.mock(AccountRepository.class);
        customerService=Mockito.mock(CustomerService.class);
        accountDtoConverter=Mockito.mock(AccountDtoConverter.class);

        accountService=new AccountService(accountRepository,customerService,accountDtoConverter);
    }

    @Test
    public void whenCreateAccountCallWithValidRequest_itShouldReturnValidAccountDto(){

        // Hangi requesti kullancagımı test seneryomda yazmam lazım.
        CreateAccountRequest createAccountRequest=new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("123");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.EURO);


        Customer customer=Customer.builder()
                .id("123")
                .address("üniversite")
                .dateOfBirth(2001)
                .name("Fırat Can")
                .city(City.EDIRNE)
                .build();

        Account account=Account.builder()
                .id(createAccountRequest.getId())
                .balance(createAccountRequest.getBalance())
                .customerId(createAccountRequest.getCustomerId())
                .currency(createAccountRequest.getCurrency())
                .city(createAccountRequest.getCity()).build();


        AccountDto accountDto=AccountDto.builder()
                .id("1234")
                .customerId("123")
                .balance(100.0)
                .currency(CurrencyDto.EURO)
                .build();

        when(customerService.getCustomerById("123")).thenReturn(customer);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountDtoConverter.convert(account)).thenReturn(accountDto);

        AccountDto result=accountService.createAccount(createAccountRequest);

        //KARSİLASTİRMA YAPİCAZ

        Assert.assertEquals(result,accountDto);

        //3 metoduda cagirip cagirmadigimi dogrulamam lazım
        Mockito.verify(customerService).getCustomerById("123");
        Mockito.verify(accountRepository).save(account);
        Mockito.verify(accountDtoConverter).convert(account);

    }
    @Test(expected = RuntimeException.class)    //ÖNCEDEN EXPECTED VERMEME GEREK YOKTU AMA RUNTİMEEXCEPTİON TESTİNİDE EKLEDİĞİM İÇİN EKLİYİCEM
    public void whenCreateAccountCallWithNonExistCustomer_itShouldReturnEmptyAccountDto(){

        CreateAccountRequest createAccountRequest=new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("123");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.EURO);

        when(customerService.getCustomerById("123")).thenReturn(Customer.builder().build());

        AccountDto expectedAccountDto=AccountDto.builder().build();

        AccountDto result=accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result,expectedAccountDto);

        //accountrepository'e hiç gidilmedi
        //accountdtoconverter'a hiç gidilmedi
        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }

    //customer veritabanına yazılmış ama id si yok.
    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCallWithCustomerWithOutId_itShouldReturnEmptyAccountDto(){
        CreateAccountRequest createAccountRequest=new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("123");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.EURO);

        when(customerService.getCustomerById("123")).thenReturn(Customer.builder()
                .id(" ")
                .build());

        AccountDto expectedAccountDto=AccountDto.builder().build();

        AccountDto result=accountService.createAccount(createAccountRequest);

        Assert.assertEquals(result,expectedAccountDto);

        //accountrepository'e hiç gidilmedi
        //accountdtoconverter'a hiç gidilmedi

        Mockito.verifyNoInteractions(accountRepository);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }


    @Test(expected = RuntimeException.class)
    public void whenCreateAccountCalledAndRepositoryThrewException_itShouldThrowException(){

        // Hangi requesti kullancagımı test seneryomda yazmam lazım.
        CreateAccountRequest createAccountRequest=new CreateAccountRequest("1234");
        createAccountRequest.setCustomerId("123");
        createAccountRequest.setBalance(100.0);
        createAccountRequest.setCity(City.ISTANBUL);
        createAccountRequest.setCurrency(Currency.EURO);


        Customer customer=Customer.builder()
                .id("123")
                .address("üniversite")
                .dateOfBirth(2001)
                .name("Fırat Can")
                .city(City.EDIRNE)
                .build();

        Account account=Account.builder()
                .id(createAccountRequest.getId())
                .balance(createAccountRequest.getBalance())
                .customerId(createAccountRequest.getCustomerId())
                .currency(createAccountRequest.getCurrency())
                .city(createAccountRequest.getCity()).build();



        when(customerService.getCustomerById("123")).thenReturn(customer);
        when(accountRepository.save(account)).thenThrow(new RuntimeException("BLA BLA"));
        //Mockito.when(accountDtoConverter.convert(account)).thenReturn(accountDto);
        //Bunun çalışmayacağının garantisini aşağıda veriyorum

        accountService.createAccount(createAccountRequest);


        Mockito.verify(customerService).getCustomerById("123");
        Mockito.verify(accountRepository).save(account);
        Mockito.verifyNoInteractions(accountDtoConverter);
    }


/*
    @Test
    public void WhenTestUpdateAccountCallWithValidRequest_itShouldReturnValidAccountDto(){

        UpdateAccountRequest updateAccountRequest=new UpdateAccountRequest();
        updateAccountRequest.setCustomerId("123");
        updateAccountRequest.setBalance(100.0);
        updateAccountRequest.setCity(City.EDIRNE);
        updateAccountRequest.setCurrency(Currency.EURO);

        Customer customer=Customer.builder()
                .id("123")
                .address("üniversite")
                .dateOfBirth(2001)
                .name("Fırat Can")
                .city(City.EDIRNE)
                .build();



        Optional<Account> accountOptional= accountRepository.findById("1234");



        Account account=Account.builder()
                .balance(updateAccountRequest.getBalance())
                .customerId(updateAccountRequest.getCustomerId())
                .currency(updateAccountRequest.getCurrency())
                .city(updateAccountRequest.getCity()).build();


        AccountDto accountDto=AccountDto.builder()
                .id("1234")
                .customerId("123")
                .balance(100.0)
                .currency(CurrencyDto.EURO)
                .build();


        when(customerService.getCustomerById("123")).thenReturn(customer);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountDtoConverter.convert(account)).thenReturn(accountDto);
        when(accountRepository.findById("1234")).thenReturn(accountOptional);

        AccountDto result=accountService.updateAccount("1234",updateAccountRequest);
        Assert.assertEquals(result,accountDto);


        Mockito.verify(customerService).getCustomerById("123");
        Mockito.verify(accountRepository).save(account);
        Mockito.verify(accountDtoConverter).convert(account);
        Mockito.verify(accountRepository).findById("1234");
    }
*/
    @Test
    public void WhenTestUpdateAccountWhenCustomerNotFound() {
        // Mock bir UpdateAccountRequest oluşturun
        UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest();
        updateAccountRequest.setCustomerId("123");
        updateAccountRequest.setBalance(100.0);
        updateAccountRequest.setCity(City.EDIRNE);
        updateAccountRequest.setCurrency(Currency.EURO);

        // customerService.getCustomerById metodunu bir mock sonucu ile ayarlayın (müşteri bulunamadı)
        when(customerService.getCustomerById(updateAccountRequest.getCustomerId())).thenReturn(new Customer());

        // updateAccount metodu çağrılsın
        AccountDto result = accountService.updateAccount("1234", updateAccountRequest);

        // Beklenen sonucun oluşturulması
        AccountDto expected = new AccountDto();

        // Sonuç ile beklenen sonucu karşılaştırın
        assertEquals(expected, result);




//---------------------------------------------------------------------------------
        // Seneryo 2 Geçerli bir müşteri bulunduğunda
        Customer validCustomer = new Customer();
        validCustomer.setId("123");
        when(customerService.getCustomerById(updateAccountRequest.getCustomerId())).thenReturn(validCustomer);

        // AccountRepository'den bir mock hesap döndürün
        Account mockAccount = new Account();
        when(accountRepository.findById("1234")).thenReturn(Optional.of(mockAccount));

        // AccountDtoConverter'dan bir mock AccountDto döndürün
        AccountDto mockAccountDto = new AccountDto();
        when(accountDtoConverter.convert(mockAccount)).thenReturn(mockAccountDto);

        // updateAccount metodu çağrılsın
         result = accountService.updateAccount("1234", updateAccountRequest);

        // Beklenen sonucun oluşturulması
        expected = mockAccountDto;

        // Sonuç ile beklenen sonucu karşılaştırın
        assertEquals(expected, result);




//-------------------------------------------------------------------------------------
        // Senaryo 3: Hesap bulunamadığında
        when(accountRepository.findById("1234")).thenReturn(Optional.empty());

        // updateAccount metodu çağrılsın
        result = accountService.updateAccount("1234", updateAccountRequest);

        // Beklenen sonucun oluşturulması
        expected = new AccountDto();

        // Sonuç ile beklenen sonucu karşılaştırın
        assertEquals(expected, result);

    }

    /*
    @Test
    public void WhenGetAllAccounts(){
        List<Account> accountList=new ArrayList<>();

        when(accountRepository.findAll()).thenReturn(accountList);





    }
    */

    @Test
    public void testGetAllAccounts() {
        // Mock verileri oluştur
        List<Account> mockAccountList = new ArrayList<>();
        Account account1 = new Account(/* ... */);
        Account account2 = new Account(/* ... */);
        mockAccountList.add(account1);
        mockAccountList.add(account2);

        List<AccountDto> mockAccountDtoList = new ArrayList<>();
        AccountDto accountDto1 = new AccountDto(/* ... */);
        AccountDto accountDto2 = new AccountDto(/* ... */);
        mockAccountDtoList.add(accountDto1);
        mockAccountDtoList.add(accountDto2);

        // Mock işlemleri ayarla
        when(accountRepository.findAll()).thenReturn(mockAccountList);
        when(accountDtoConverter.convert(account1)).thenReturn(accountDto1);
        when(accountDtoConverter.convert(account2)).thenReturn(accountDto2);

        // Testi çağır
        List<AccountDto> result = accountService.getAllAccounts();

        // Sonuçları doğrula
        assertEquals(mockAccountDtoList.size(), result.size());
        assertEquals(mockAccountDtoList.get(0), result.get(0));
        assertEquals(mockAccountDtoList.get(1), result.get(1));

        // Mock metodların çağrılma sayısını kontrol et
        Mockito.verify(accountRepository, times(1)).findAll();
        Mockito.verify(accountDtoConverter, times(2)).convert(any(Account.class)); // 2 kez çağrıldığını doğrula
    }
}