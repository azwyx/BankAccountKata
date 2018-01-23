package amh.kata.bankaccount.Services;

import amh.kata.bankaccount.dao.OperationRepository;
import amh.kata.bankaccount.entities.Account;
import amh.kata.bankaccount.entities.Deposit;
import amh.kata.bankaccount.entities.Operation;
import amh.kata.bankaccount.entities.Transfer;
import amh.kata.bankaccount.entities.exceptions.AccountNotFoundException;
import amh.kata.bankaccount.entities.exceptions.AmountLowerThanBalanceException;
import amh.kata.bankaccount.entities.exceptions.AmountMinMaxValueException;
import amh.kata.bankaccount.tools.OperationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class OperationService {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private OperationRepository operationRepository;

    private static final Logger logger = Logger.getLogger("MyLog");
    private static final double MIN_VALUE = 0;
    private static final double MAX_VALUE = 999999999;
    private static final String TYPE = "T";

    public Operation deposit(OperationRequest operationRequest) {
        Account account = accountService.getAccount(operationRequest.getAccountCode());
        double amount = operationRequest.getAmount();
        if (amount <= MIN_VALUE || amount > MAX_VALUE)
            throw new AmountMinMaxValueException("Please enter a valid amount");

        // if the amount is valid, let's do the operation
        Operation operation = new Deposit();
        account.setBalance(account.getBalance() + amount);

        operation.setAmount(amount);
        operation.setDateOperation(new Date());
        operation.setAccount(account);

        logger.info("deposit : " + account.toString() + " | amount : " + amount);

        return operationRepository.save(operation);
    }

    public Operation withdrawal(OperationRequest operationRequest) {
        Account account = accountService.getAccount(operationRequest.getAccountCode());
        double amount = operationRequest.getAmount();

        // the amount should be lower than account's balance
        if (amount > account.getBalance())
            throw new AmountLowerThanBalanceException("Please introduce an amount lower than your balance : <"  + account.getBalance());

        // the amount should be positive
        if (amount <= MIN_VALUE || amount > MAX_VALUE)
            throw new AmountMinMaxValueException(
                    "Please enter a valid amount");

        // if the amount is valid, let's do the operation
        Operation operation = new Operation();
        account.setBalance(account.getBalance() - amount);

        operation.setAmount(amount);
        operation.setDateOperation(new Date());
        operation.setAccount(account);

        // save operation in account history
        logger.info("withdraw : " + account.toString() + " | amount : " + amount);
        return operationRepository.save(operation);
    }

    public Transfer transfer(OperationRequest operationRequest) {
        Account account = accountService.getAccount(operationRequest.getAccountCode());
        double amount = operationRequest.getAmount();
        Account toAccount = accountService.getAccount(operationRequest.getToAccountCode());

        // the amount should be lower than account's balance
        if (amount > account.getBalance())
            throw new AmountLowerThanBalanceException("Please introduce an amount lower than your balance : <"  + account.getBalance());

        // the amount should be positive
        if (amount <= MIN_VALUE || amount > MAX_VALUE)
            throw new AmountMinMaxValueException(
                    "Please enter a valid amount");

        // if the amount is valid, let's do the operation
        Transfer optransfer = new Transfer();
        account.setBalance(account.getBalance() - amount);
        toAccount.setBalance(account.getBalance() + amount);

        optransfer.setAmount(amount);
        optransfer.setDateOperation(new Date());
        optransfer.setAccount(account);
        optransfer.setToAccount(toAccount);

        // save operation in account history
        logger.info("transfer of : " + amount + "euros from this account : " + account.getAccountCode()
                            + " to this account "+toAccount.getAccountCode());
        return operationRepository.save(optransfer);
    }

    public List<Transfer> transferHistory(String accountCode) {
        return operationRepository.findByTypeAndAccount(TYPE, accountCode);
    }
}
