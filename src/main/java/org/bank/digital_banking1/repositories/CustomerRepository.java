package org.bank.digital_banking1.repositories;

import org.bank.digital_banking1.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    public List<Customer> findCustomerByNameContains(String keyword);
}
