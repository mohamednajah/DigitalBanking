package org.bank.digital_banking1.repositories;

import org.bank.digital_banking1.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
