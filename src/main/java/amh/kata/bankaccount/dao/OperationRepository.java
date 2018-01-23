package amh.kata.bankaccount.dao;

import amh.kata.bankaccount.entities.Operation;
import amh.kata.bankaccount.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query("select t from operation t where type = :type and (code_cpte = :accountCode or to_code_cpte = :accountCode)")
    List<Transfer> findByTypeAndAccount(String type, String accountCode);
}
