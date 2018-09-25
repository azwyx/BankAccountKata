package com.kata.bankaccount.services;

import com.kata.bankaccount.entities.Account;

import java.util.List;

public interface IAccountService {
    // methode pour créer un compte
    public Account saveAccount(Account account);
    // methode pour mettre à jour un compte
    public Account updateAccount(Account account);
    // methode pour récupérer les infos d'un compte
    public Account getAccount(String accountCode);
    // methode pour récupérer les comptes d'un client
    public List<Account> getClientAccounts(long idClient);
    // methode pour récupérer tous les comptes
    public List<Account> getAllAccounts();
    // methode pour supprimer un compte
    public void deleteAccount(String accountCode);
}
