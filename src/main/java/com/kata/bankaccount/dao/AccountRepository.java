package com.kata.bankaccount.dao;

import com.kata.bankaccount.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findByClient(Long idClient);
}
