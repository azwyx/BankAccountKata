package amh.kata.bankaccount.dao;

import amh.kata.bankaccount.entities.Account;
import amh.kata.bankaccount.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findByClient(Long idClient);
}
