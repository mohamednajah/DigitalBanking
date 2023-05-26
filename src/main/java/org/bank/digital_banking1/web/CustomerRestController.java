package org.bank.digital_banking1.web;

import org.bank.digital_banking1.dtos.CustomerDto;
import org.bank.digital_banking1.exceptions.customerNotFoundException;
import org.bank.digital_banking1.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
public class CustomerRestController {
    @Autowired
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDto> listcustomers(){
        List<CustomerDto> listCustomers = bankAccountService.listCustomers();
        return listCustomers;
    }

    @GetMapping("/customers/{id}")
    public CustomerDto getCustomer(@PathVariable(name = "id") Long CustomerId) throws customerNotFoundException {
        CustomerDto customer = bankAccountService.getCustomer(CustomerId);
        return customer;
    }
    @PostMapping("/customers")
    public CustomerDto saveCustomerDto(@RequestBody CustomerDto customerDto){
      return   bankAccountService.SaveCostumerDto(customerDto);
    }
    @PutMapping("/customers/{id}")
    public CustomerDto updateCustomerDto(@PathVariable(name = "id")Long id,@RequestBody CustomerDto customerDto){
        customerDto.setId(id);
        return bankAccountService.updateCustomer(customerDto);

    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomerDao(@PathVariable Long id){
        bankAccountService.DeleteCustomerDto(id);
    }

    @GetMapping("/customers/search")
    public List<CustomerDto> search(@RequestParam(name = "keyword",defaultValue = "") String keyword){

        return  bankAccountService.search(keyword);

    }
}
