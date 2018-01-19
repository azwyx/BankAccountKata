package amh.kata.bankaccount.Services;

import amh.kata.bankaccount.entities.Account;

import java.util.List;

public interface IAccountService {
    public Account saveAccount(Account account);
    public Account getAccount(String accountCode);
    public List<Account> getClientAccounts(long idClient);
    public List<Account> getAllAccounts();
    public void deleteAccount(String accountCode);
}
