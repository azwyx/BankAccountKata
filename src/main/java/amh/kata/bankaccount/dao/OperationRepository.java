package amh.kata.bankaccount.dao;

import amh.kata.bankaccount.entities.Operation;
import amh.kata.bankaccount.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query("select t from Operation t where TYPE(t) = :typeOp and (t.account = :code or t.toAccount = :code)")
    List<Transfer> findByTypeAndAccount(@Param("typeOp") String typeOp,@Param("code") String accountCode);
}
