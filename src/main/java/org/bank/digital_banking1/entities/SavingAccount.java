package org.bank.digital_banking1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@DiscriminatorValue("SA")
public class SavingAccount extends BankAccount{
    private Double interestRate;
}
