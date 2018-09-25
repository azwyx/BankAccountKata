package com.kata.bankaccount.services;

import com.kata.bankaccount.entities.Deposit;
import com.kata.bankaccount.entities.Transfer;
import com.kata.bankaccount.entities.Withdrawal;
import com.kata.bankaccount.tools.OperationRequest;

import java.util.List;

public interface IOperationService {
    // methode pour verser une somme dans le compte d'un client
    public Deposit deposit(OperationRequest operationRequest);
    // methode pour retirer une somme depuis le compte d'un client
    public Withdrawal withdrawal(OperationRequest operationRequest);
    // methode pour faire un virement  depuis un compte vers un autre
    public Transfer transfer(OperationRequest operationRequest);
    // methode pour récupérer l'historique des opérations effectuer pour un compte
    public List<Transfer> transferHistory(String accountCode);
}
