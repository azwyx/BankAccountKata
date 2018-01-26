package com.kata.bankaccount.services;

import com.kata.bankaccount.entities.Deposit;
import com.kata.bankaccount.entities.Transfer;
import com.kata.bankaccount.entities.Withdrawal;
import com.kata.bankaccount.tools.OperationRequest;

import java.util.List;

public interface IOperationService {
    public Deposit deposit(OperationRequest operationRequest);
    public Withdrawal withdrawal(OperationRequest operationRequest);
    public Transfer transfer(OperationRequest operationRequest);
    public List<Transfer> transferHistory(String accountCode);
}
