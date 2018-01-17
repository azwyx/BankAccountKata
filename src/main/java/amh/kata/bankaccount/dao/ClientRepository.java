package amh.kata.bankaccount.dao;

import amh.kata.bankaccount.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long>{
    Client finByIdClient(Long idClient);
}
