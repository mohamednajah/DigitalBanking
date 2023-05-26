package org.bank.digital_banking1.repositories;

import org.bank.digital_banking1.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation,Long> {
    public List<Operation> findByBankAccountId(String id);
    public Page<Operation> findByBankAccountId(String id, Pageable pageable);
}
