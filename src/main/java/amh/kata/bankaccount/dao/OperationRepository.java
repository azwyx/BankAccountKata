package amh.kata.bankaccount.dao;

import amh.kata.bankaccount.entities.Operation;
import amh.kata.bankaccount.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query("select t from Operation t where TYPE = :typeOp and (t.account.accountCode = :code or t.toAccount.accountCode = :code)")
    List<Transfer> findByTypeAndAccount(@Param("typeOp") String typeOp,@Param("code") String accountCode);
}
