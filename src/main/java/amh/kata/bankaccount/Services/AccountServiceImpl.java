package amh.kata.bankaccount.Services;

import amh.kata.bankaccount.entities.Account;
import amh.kata.bankaccount.entities.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService{


    @Override
    public Account saveAccount(Account account) {
        return null;
    }

    @Override
    public Account getAccount(String accountCode) {
        return null;
    }

    @Override
    public List<Account> getClientAccounts(long idClient) {
        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
        return null;
    }

    @Override
    public void deleteAccount(String accountCode) {

    }
}
