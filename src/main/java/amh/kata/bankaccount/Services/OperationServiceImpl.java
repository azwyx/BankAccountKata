package amh.kata.bankaccount.Services;

import amh.kata.bankaccount.dao.OperationRepository;
import amh.kata.bankaccount.entities.*;
import amh.kata.bankaccount.entities.exceptions.AmountLowerThanBalanceException;
import amh.kata.bankaccount.entities.exceptions.AmountMinMaxValueException;
import amh.kata.bankaccount.tools.OperationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
@Transactional
public class OperationServiceImpl implements IOperationService{

    @Autowired
    private IAccountService accountService;

    @Autowired
    private OperationRepository operationRepository;

    private static final Logger logger = Logger.getLogger("MyLog");
    private static final double MIN_VALUE = 0;
    private static final double MAX_VALUE = 999999999;
    private static final String TRANSFER_TYPE = "T";

    @Override
    public Deposit deposit(OperationRequest operationRequest) {
        Account account = accountService.getAccount(operationRequest.getAccountCode());
        double amount = operationRequest.getAmount();
        if (amount <= MIN_VALUE || amount >= MAX_VALUE)
            throw new AmountMinMaxValueException("Please enter a valid amount");

        // if the amount is valid, let's do the operation
        Deposit operation = new Deposit();
        account.setBalance(account.getBalance() + amount);

        operation.setAmount(amount);
        operation.setDateOperation(new Date());
        operation.setAccount(account);

        logger.info("deposit : account " + account.getAccountCode() + " | amount : " + amount);

        return operationRepository.save(operation);
    }

    @Override
    public Withdrawal withdrawal(OperationRequest operationRequest) {
        Account account = accountService.getAccount(operationRequest.getAccountCode());
        double amount = operationRequest.getAmount();

        // the amount should be positive
        if (amount <= MIN_VALUE || amount >= MAX_VALUE)
            throw new AmountMinMaxValueException(
                    "Please enter a valid amount");

        // the amount should be lower than account's balance
        if (amount > account.getBalance())
            throw new AmountLowerThanBalanceException("Please introduce an amount lower than your balance : <"  + account.getBalance());

        // if the amount is valid, let's do the operation
        Withdrawal operation = new Withdrawal();
        account.setBalance(account.getBalance() - amount);

        operation.setAmount(amount);
        operation.setDateOperation(new Date());
        operation.setAccount(account);

        // save operation in account history
        logger.info("withdraw : account " + account.getAccountCode() + " | amount : " + amount);
        return operationRepository.save(operation);
    }

    @Override
    public Transfer transfer(OperationRequest operationRequest) {
        Account account = accountService.getAccount(operationRequest.getAccountCode());
        double amount = operationRequest.getAmount();
        Account toAccount = accountService.getAccount(operationRequest.getToAccountCode());

        // the amount should be positive
        if (amount <= MIN_VALUE || amount >= MAX_VALUE)
            throw new AmountMinMaxValueException(
                    "Please enter a valid amount");

        // the amount should be lower than account's balance
        if (amount > account.getBalance())
            throw new AmountLowerThanBalanceException("Please introduce an amount lower than your balance : <"  + account.getBalance());

        // if the amount is valid, let's do the operation
        Transfer optransfer = new Transfer();
        account.setBalance(account.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        optransfer.setAmount(amount);
        optransfer.setDateOperation(new Date());
        optransfer.setAccount(account);
        optransfer.setToAccount(toAccount);

        // save operation in account history
        logger.info("transfer of : " + amount + "euros from this account : " + account.getAccountCode()
                            + " to this account "+toAccount.getAccountCode());
        return operationRepository.save(optransfer);
    }

    @Override
    public List<Transfer> transferHistory(String accountCode) {
        return operationRepository.findByTypeAndAccount(TRANSFER_TYPE,
                accountService.getAccount(accountCode).getAccountCode());
    }
}
