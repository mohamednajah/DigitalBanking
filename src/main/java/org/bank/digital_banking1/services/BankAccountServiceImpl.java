package org.bank.digital_banking1.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.digital_banking1.dtos.*;
import org.bank.digital_banking1.entities.*;
import org.bank.digital_banking1.enums.type;
import org.bank.digital_banking1.exceptions.BalanceNotsufficientException;
import org.bank.digital_banking1.exceptions.BankAccountNotFoundException;
import org.bank.digital_banking1.exceptions.customerNotFoundException;
import org.bank.digital_banking1.mappers.BankAccountMapperImp;
import org.bank.digital_banking1.repositories.BankAccountRepository;
import org.bank.digital_banking1.repositories.CustomerRepository;
import org.bank.digital_banking1.repositories.OperationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class BankAccountServiceImpl implements BankAccountService {
    BankAccountRepository bankAccountRepository;
    CustomerRepository customerRepository;
    OperationRepository operationRepository;
    BankAccountMapperImp bankAccountMapperImp;
    //Logger log= (Logger) LoggerFactory.getLogger(this.getClass().getName());
  /*  @Override
    public Customer SaveCostumer(Customer customer) {
        log.info("saving customer");

        Customer savedcustomer = customerRepository.save(customer);
        return savedcustomer;
    }*/
    @Override
    public List<BankAccountDto> bankAccountList(){
        List<BankAccount> bankAccountlist = bankAccountRepository.findAll();
        List<BankAccountDto> bankaccountlistdto = bankAccountlist.stream().map(bankAccount -> {
            if (bankAccount instanceof CurrentAccount) {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return bankAccountMapperImp.fromCurrentAccount(currentAccount);
            } else {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return bankAccountMapperImp.fromSavingAccount(savingAccount);
            }

        }).collect(Collectors.toList());
      return bankaccountlistdto;
    }
    @Override
    public CustomerDto SaveCostumerDto(CustomerDto customerDto) {
        log.info("saving customer");
        Customer customer=bankAccountMapperImp.FromCustomerDto(customerDto);
        Customer savedcustomer = customerRepository.save(customer);
        return bankAccountMapperImp.FromCustomer(savedcustomer);
    }

    @Override
    public CurrentBankAccountDto SaveCurrentBankAccount(double InitialBalance, double OverDraft, Long CustomerId) throws customerNotFoundException {
        Customer customer=customerRepository.findById(CustomerId).orElseThrow();
        if(customer==null)
            throw new customerNotFoundException("customer not found");
        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setDate_creation(new Date());
        currentAccount.setBalance(InitialBalance);
        currentAccount.setOverDraft(OverDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedcurrentAccount = bankAccountRepository.save(currentAccount);
        return bankAccountMapperImp.fromCurrentAccount(savedcurrentAccount);
    }

    @Override
    public SavingBankAccountDto SaveSavingBankAccount(double InitialBalance, double InterestRate, Long CustomerId) throws customerNotFoundException {
        Customer customer=customerRepository.findById(CustomerId).orElseThrow();
        if(customer==null)
            throw new customerNotFoundException("customer not found");
        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setDate_creation(new Date());
        savingAccount.setBalance(InitialBalance);
        savingAccount.setInterestRate(InterestRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedsavingAccount = bankAccountRepository.save(savingAccount);
        return bankAccountMapperImp.fromSavingAccount(savingAccount);
    }
    @Override
    public List<CustomerDto> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDto = customers.stream().map(customer -> bankAccountMapperImp.FromCustomer(customer)).collect(Collectors.toList());
        return  customerDto;
    }

    @Override
    public BankAccountDto getBankAccount(String AccountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(AccountId).orElseThrow(() -> new BankAccountNotFoundException("bank account not found"));
        if (bankAccount instanceof CurrentAccount)
        {
            CurrentAccount currentAccount= (CurrentAccount) bankAccount;
            return bankAccountMapperImp.fromCurrentAccount(currentAccount);

        } else  {
            SavingAccount savingAccount= (SavingAccount) bankAccount;
            return bankAccountMapperImp.fromSavingAccount(savingAccount);

        }

    }

    @Override
    public void debit(String AccountId, double Amount, String description) throws BankAccountNotFoundException, BalanceNotsufficientException {
        BankAccount bankAccount = bankAccountRepository.findById(AccountId).orElseThrow(() -> new BankAccountNotFoundException("bank account not found"));
    if(bankAccount.getBalance()<Amount)
        throw new BalanceNotsufficientException("balance not sufficient");
        Operation operation=new Operation();
        operation.setDate(new Date());
        operation.setDescription(description);
        operation.setOptype(type.DEBIT);
        operation.setAmount(Amount);
        operation.setBankAccount(bankAccount);
        operationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance()-Amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String AccountId, double Amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(AccountId).orElseThrow(() -> new BankAccountNotFoundException("bank account not found"));
        Operation operation=new Operation();
        operation.setDate(new Date());
        operation.setOptype(type.CREDIT);
        operation.setAmount(Amount);
        operation.setDescription(description);
        operation.setBankAccount(bankAccount);
        operationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance()+Amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String Idsource, String Iddestination, double amount) throws BankAccountNotFoundException, BalanceNotsufficientException {
     debit(Idsource,amount,"transfer to : "+Iddestination);
     credit(Iddestination,amount,"transfer from : "+Idsource);
    }
    @Override
    public CustomerDto getCustomer(Long CustomerId) throws customerNotFoundException {
        Customer customer = customerRepository.findById(CustomerId).orElseThrow(() -> new customerNotFoundException("customer not found"));
        return bankAccountMapperImp.FromCustomer(customer);
    }



    @Override
    public CustomerDto updateCustomer(CustomerDto customerDTO) {
        log.info("Saving new Customer");
        Customer customer=bankAccountMapperImp.FromCustomerDto(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return bankAccountMapperImp.FromCustomer(savedCustomer);
    }
    @Override
    public void DeleteCustomerDto(Long CustomerId){
        customerRepository.deleteById(CustomerId);
    }
    @Override
    public List<OperationDto> operationsList(String id){
        List<Operation> listoperations = operationRepository.findByBankAccountId(id);
        return listoperations.stream().map(operation ->bankAccountMapperImp.FromOperation(operation)).collect(Collectors.toList());

    }

    @Override
    public PageHistoryDto getAccountHistory(String accountid, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountid).orElseThrow(()->new BankAccountNotFoundException("bank account not found"));
        Page<Operation> PageOperations=operationRepository.findByBankAccountId(accountid, PageRequest.of(page,size));
        List<OperationDto> listoperationsdto = PageOperations.getContent().stream().map(operation -> bankAccountMapperImp.FromOperation(operation)).collect(Collectors.toList());
        PageHistoryDto pageHistoryDto=new PageHistoryDto();
        pageHistoryDto.setOperationslist(listoperationsdto);
        pageHistoryDto.setCurrentPage(page);
        pageHistoryDto.setTotalPages(PageOperations.getTotalPages());
        pageHistoryDto.setSize(size);
        pageHistoryDto.setId(bankAccount.getId());
        pageHistoryDto.setBalance(bankAccount.getBalance());
        return pageHistoryDto;
    }

    @Override
    public List<CustomerDto> search(String keyword) {
        List<Customer> customers = customerRepository.findCustomerByNameContains(keyword);
        List<CustomerDto> collect = customers.stream().map(customer -> bankAccountMapperImp.FromCustomer(customer)).collect(Collectors.toList());
         return collect;
    }

}

