package org.bank.digital_banking1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.bank.digital_banking1.dtos.BankAccountDto;
import org.bank.digital_banking1.dtos.CurrentBankAccountDto;
import org.bank.digital_banking1.dtos.CustomerDto;
import org.bank.digital_banking1.dtos.SavingBankAccountDto;
import org.bank.digital_banking1.entities.*;
import org.bank.digital_banking1.enums.AccStatus;
import org.bank.digital_banking1.enums.type;
import org.bank.digital_banking1.exceptions.BalanceNotsufficientException;
import org.bank.digital_banking1.exceptions.BankAccountNotFoundException;
import org.bank.digital_banking1.exceptions.customerNotFoundException;
import org.bank.digital_banking1.repositories.BankAccountRepository;
import org.bank.digital_banking1.repositories.CustomerRepository;
import org.bank.digital_banking1.repositories.OperationRepository;
import org.bank.digital_banking1.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBanking1Application {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBanking1Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args -> {
            Stream.of("Hassan", "Imane", "Mohamed").forEach(name -> {
                CustomerDto customer = new CustomerDto();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                bankAccountService.SaveCostumerDto(customer);
            });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.SaveCurrentBankAccount(Math.random() * 90000, 9000, customer.getId());
                    bankAccountService.SaveSavingBankAccount(Math.random() * 120000, 5.5, customer.getId());



                } catch (customerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDto> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDto bankAccount:bankAccounts) {
                for (int i = 0; i <10 ; i++) {
                    String AccountId;
                    if (bankAccount instanceof CurrentBankAccountDto) {
                        AccountId = ((CurrentBankAccountDto) bankAccount).getId();
                    }else {
                        AccountId = ((SavingBankAccountDto) bankAccount).getId();
                    }
                    bankAccountService.credit(AccountId,10000+Math.random()*120000,"Credit");
                    bankAccountService.debit(AccountId,1000+Math.random()*9000,"Debit");
                }
            }
        };
    }
    //@Bean
    CommandLineRunner commandLineRunner(BankAccountRepository bankAccountRepository){
        return args -> {
            BankAccount bankAccount=bankAccountRepository.findById("2046e12c-a8e6-48d9-a37c-64bd39ffa319").orElseThrow();
            System.out.println("************ bank info ******************");
            System.out.println(bankAccount.getId() +"/t "+"/t"+bankAccount.getDate_creation());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getName());
            if(bankAccount instanceof CurrentAccount){
                System.out.println(((CurrentAccount) bankAccount).getOverDraft());
            }
            else if (bankAccount instanceof SavingAccount){
                System.out.println(((SavingAccount) bankAccount).getInterestRate());
            }
            bankAccount.getOperations().forEach(operation -> {
                System.out.println(operation.getAmount());
                System.out.println(operation.getDate());
                System.out.println(operation.getOptype());
            });
        };
    }
//@Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
                                        BankAccountRepository bankAccountRepository,
                                        OperationRepository operationRepository){
        return args -> {
            Stream.of("hamid","rachid","walid").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"gmail.com");
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(customer -> {
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*10000);
                currentAccount.setCustomer(customer);
                currentAccount.setStatus(AccStatus.ACTIVATED);
                currentAccount.setDate_creation(new Date());
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);


                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*10000);
                savingAccount.setStatus(AccStatus.ACTIVATED);
                savingAccount.setCustomer(customer);
                savingAccount.setDate_creation(new Date());
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);

            });
            bankAccountRepository.findAll().forEach(bankAccount -> {
                for (int i=0;i<10;i++){
                    Operation operation=new Operation();
                    operation.setAmount(Math.random()*10000);
                    operation.setDate(new Date());
                    operation.setOptype(Math.random()>0.5? type.CREDIT:type.DEBIT);
                    operation.setBankAccount(bankAccount);
                    operationRepository.save(operation);
                }

            });


        };
}
}
