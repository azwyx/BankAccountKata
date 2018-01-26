package com.kata.bankaccount.dao;

import com.kata.bankaccount.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long>{
    Client findByIdClient(Long idClient);
}
