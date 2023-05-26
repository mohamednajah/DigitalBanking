package org.bank.digital_banking1.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bank.digital_banking1.entities.Customer;
import org.bank.digital_banking1.entities.Operation;
import org.bank.digital_banking1.enums.AccStatus;

import java.util.Date;
import java.util.List;


@Data
public class SavingBankAccountDto extends BankAccountDto {

    private String id;
    private Date date_creation;
    private double balance;
    private AccStatus status;
    private CustomerDto customerDto;
    private double InterestRate;

}
