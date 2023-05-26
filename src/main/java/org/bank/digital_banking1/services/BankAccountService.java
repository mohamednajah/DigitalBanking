package org.bank.digital_banking1.services;

import org.bank.digital_banking1.dtos.*;
import org.bank.digital_banking1.entities.BankAccount;
import org.bank.digital_banking1.entities.CurrentAccount;
import org.bank.digital_banking1.entities.Customer;
import org.bank.digital_banking1.entities.SavingAccount;
import org.bank.digital_banking1.exceptions.BalanceNotsufficientException;
import org.bank.digital_banking1.exceptions.BankAccountNotFoundException;
import org.bank.digital_banking1.exceptions.customerNotFoundException;

import java.util.List;

public interface BankAccountService {

    //Logger log= (Logger) LoggerFactory.getLogger(this.getClass().getName());
  /*  @Override
    public Customer SaveCostumer(Customer customer) {
        log.info("saving customer");

        Customer savedcustomer = customerRepository.save(customer);
        return savedcustomer;
    }*/
    List<BankAccountDto> bankAccountList();

    CustomerDto SaveCostumerDto(CustomerDto customerDto);

    CurrentBankAccountDto SaveCurrentBankAccount(double InitialBalance, double OverDraft, Long CustomerId) throws customerNotFoundException;
    SavingBankAccountDto SaveSavingBankAccount(double InitialBalance, double InterestRate, Long CustomerId) throws customerNotFoundException;
    List<CustomerDto> listCustomers();
    BankAccountDto getBankAccount(String AccountId) throws BankAccountNotFoundException;
    void debit(String AccountId,double Amount,String description) throws BankAccountNotFoundException, BalanceNotsufficientException;
    void credit(String AccountId,double Amount,String desription) throws BankAccountNotFoundException;
    void transfer(String Idsource,String Iddestination,double amount) throws BankAccountNotFoundException, BalanceNotsufficientException;

    CustomerDto getCustomer(Long CustomerId) throws customerNotFoundException;

    CustomerDto updateCustomer(CustomerDto customerDTO);

    void DeleteCustomerDto(Long CustomerId);

    List<OperationDto> operationsList(String id);

    PageHistoryDto getAccountHistory(String accountid, int page, int size) throws BankAccountNotFoundException;
    List<CustomerDto> search(String keyword);
}
