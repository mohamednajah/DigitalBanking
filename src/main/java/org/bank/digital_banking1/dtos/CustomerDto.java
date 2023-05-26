package org.bank.digital_banking1.dtos;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bank.digital_banking1.entities.BankAccount;

import java.util.List;


@Data
public class CustomerDto {
    private Long id;
    private String email;
    private String name;
}
