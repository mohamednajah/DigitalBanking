package org.bank.digital_banking1.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bank.digital_banking1.enums.AccStatus;
import org.hibernate.annotations.ManyToAny;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 4,discriminatorType = DiscriminatorType.STRING)
@Data @AllArgsConstructor @NoArgsConstructor
// ABSTRACT si on utilise table per class
public class BankAccount {
    @Id
    private String id;
    private Date date_creation;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List< Operation> operations;
}
