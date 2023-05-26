package org.bank.digital_banking1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bank.digital_banking1.enums.type;

import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Operation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private double amount;
    private String description;
    @Enumerated(EnumType.STRING)
    private type optype;
    @ManyToOne
    private BankAccount bankAccount;
}
