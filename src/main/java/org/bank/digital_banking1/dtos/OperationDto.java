package org.bank.digital_banking1.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bank.digital_banking1.entities.BankAccount;
import org.bank.digital_banking1.enums.type;

import java.util.Date;

@Data
public class OperationDto {
    private Long id;
    private Date date;
    private double amount;
    private String description;
    private type optype;

}
