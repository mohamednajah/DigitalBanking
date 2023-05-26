package org.bank.digital_banking1.mappers;

import org.bank.digital_banking1.dtos.CurrentBankAccountDto;
import org.bank.digital_banking1.dtos.CustomerDto;
import org.bank.digital_banking1.dtos.OperationDto;
import org.bank.digital_banking1.dtos.SavingBankAccountDto;
import org.bank.digital_banking1.entities.CurrentAccount;
import org.bank.digital_banking1.entities.Customer;
import org.bank.digital_banking1.entities.Operation;
import org.bank.digital_banking1.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImp {
    public Customer FromCustomerDto(CustomerDto customerDto){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDto,customer);
        return customer;
    }

    public CustomerDto FromCustomer(Customer customer){
        CustomerDto customerDto=new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        return customerDto;
    }
    public SavingAccount fromSavingAccountDto(SavingBankAccountDto savingBankAccountDto){
       SavingAccount savingAccount=new SavingAccount();
       BeanUtils.copyProperties(savingBankAccountDto,savingAccount);
       savingAccount.setCustomer(FromCustomerDto(savingBankAccountDto.getCustomerDto()));
       return savingAccount;

    }
    public SavingBankAccountDto fromSavingAccount(SavingAccount savingAccount){
         SavingBankAccountDto savingBankAccountDto1=new SavingBankAccountDto();
        BeanUtils.copyProperties(savingAccount,savingBankAccountDto1);
        savingBankAccountDto1.setType(savingAccount.getClass().getSimpleName());
        savingBankAccountDto1.setCustomerDto(FromCustomer(savingAccount.getCustomer()));
        return savingBankAccountDto1;
    }
    public CurrentAccount fromCurrentAccountDto(CurrentBankAccountDto currentBankAccountDto){
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDto,currentAccount);
        currentAccount.setCustomer(FromCustomerDto(currentBankAccountDto.getCustomerDto()));
        return  currentAccount;
    }
    public CurrentBankAccountDto fromCurrentAccount(CurrentAccount currentAccount){
        CurrentBankAccountDto currentBankAccountDto=new CurrentBankAccountDto();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDto);
        currentBankAccountDto.setType(currentAccount.getClass().getSimpleName());
        currentBankAccountDto.setCustomerDto(FromCustomer(currentAccount.getCustomer()));
        return currentBankAccountDto;
    }
    public OperationDto FromOperation(Operation operation){
        OperationDto operationDto=new OperationDto();
        BeanUtils.copyProperties(operation,operationDto);
        return operationDto;
    }
    public Operation fromOperationDto(OperationDto operationDto){
        Operation operation=new Operation();
        BeanUtils.copyProperties(operationDto,operation);
        return operation;
    }
}
