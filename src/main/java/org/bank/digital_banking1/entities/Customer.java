package org.bank.digital_banking1.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<BankAccount> bankAccounts;
}
