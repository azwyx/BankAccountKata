package com.kata.bankaccount.services;

import com.kata.bankaccount.dao.AccountRepository;
import com.kata.bankaccount.dao.ClientRepository;
import com.kata.bankaccount.entities.Account;
import com.kata.bankaccount.entities.exceptions.AccountAlreadyExistException;
import com.kata.bankaccount.entities.exceptions.AccountNotFoundException;
import com.kata.bankaccount.entities.exceptions.ClientNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements IAccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Account saveAccount(Account account) {
        if(accountRepository.findOne(account.getAccountCode()) != null){
            log.error("Account Already Exist");
            throw new AccountAlreadyExistException();
        }
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Account account) {
        if(accountRepository.findOne(account.getAccountCode()) == null){
            log.error("Account Not Found");
            throw new AccountNotFoundException();
        }
        Account saved = accountRepository.save(account);
        return saved;
    }

    @Override
    public Account getAccount(String accountCode) {
        Account account = accountRepository.findOne(accountCode);
        if(account == null){
            log.error("Account Not Found");
            throw new AccountNotFoundException();
        }
        return account;
    }

    @Override
    public List<Account> getClientAccounts(long idClient) {
        if(clientRepository.findByIdClient(idClient) == null) {
            log.error("Client Not Found");
            throw new ClientNotFoundException();
        }
        List<Account> account_list = accountRepository.findByClient(idClient);
        if( account_list == null){
            log.error("This client doesn't had any Account");
            throw new AccountNotFoundException();
        }
        return account_list;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(String accountCode) {
        if (accountRepository.findOne(accountCode) == null){
            log.error("Account Not Found");
            throw new AccountNotFoundException();
        }
        accountRepository.delete(accountCode);
    }
}
