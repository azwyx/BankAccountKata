package amh.kata.bankaccount.Services;

import amh.kata.bankaccount.dao.AccountRepository;
import amh.kata.bankaccount.dao.ClientRepository;
import amh.kata.bankaccount.entities.Account;
import amh.kata.bankaccount.entities.Client;
import amh.kata.bankaccount.entities.exceptions.AccountAlreadyExistException;
import amh.kata.bankaccount.entities.exceptions.AccountNotFoundException;
import amh.kata.bankaccount.entities.exceptions.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Account saveAccount(Account account) {
        if(accountRepository.findOne(account.getAccountCode()) != null)
                throw new AccountAlreadyExistException("Account Already Exist");

        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Account account) {
        if(accountRepository.findOne(account.getAccountCode()) == null)
            throw new AccountNotFoundException("Account Not Found");

        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(String accountCode) {
        Account account = accountRepository.findOne(accountCode);
        if(account == null) throw new AccountNotFoundException("Account Not Found");

        return account;
    }

    @Override
    public List<Account> getClientAccounts(long idClient) {
        if(clientRepository.findByIdClient(idClient) == null) throw new ClientNotFoundException("Client Not Found");
        List<Account> account_list = accountRepository.findByClient(idClient);
        if( account_list == null) throw new AccountNotFoundException("This client doesn't had any Account");
        return account_list;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(String accountCode) {
        if (accountRepository.findOne(accountCode) == null)
            throw new AccountNotFoundException("Account Not Found");
        accountRepository.delete(accountCode);
    }
}
