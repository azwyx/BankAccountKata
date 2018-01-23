package amh.kata.bankaccount.Services;

import amh.kata.bankaccount.entities.Operation;
import amh.kata.bankaccount.entities.Transfer;
import amh.kata.bankaccount.tools.OperationRequest;

import java.util.List;

public interface IOperationService {
    public Operation deposit(OperationRequest operationRequest);
    public Operation withdrawal(OperationRequest operationRequest);
    public Transfer transfer(OperationRequest operationRequest);
    public List<Transfer> transferHistory(String accountCode);
}
