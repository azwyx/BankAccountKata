package amh.kata.bankaccount.Services;

import amh.kata.bankaccount.entities.Account;
import amh.kata.bankaccount.entities.Deposit;
import amh.kata.bankaccount.entities.Operation;
import amh.kata.bankaccount.entities.Transfer;
import amh.kata.bankaccount.entities.exceptions.AmountMinMaxValueException;
import amh.kata.bankaccount.tools.OperationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OperationService {

    public OperationService() {
    }

    public Operation deposit(OperationRequest operationRequest) {
        return null;
    }

    public Operation withdrawal(OperationRequest operationRequest) {
        return null;
    }

    public Transfer transfer(OperationRequest operationRequest) {
        return null;
    }

    public List<Transfer> transferHistory(String accountCode) {
        return null;
    }
}
