package amh.kata.bankaccount.RestControllers;

import amh.kata.bankaccount.Services.AccountService;
import amh.kata.bankaccount.entities.Account;
import amh.kata.bankaccount.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountRestController {

    private AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value="/comptes/saveAccount",method= RequestMethod.POST)
    public Account saveAccount(@RequestBody Account account) {
        return null;
    }

    @RequestMapping(value="/comptes/accountDetails/{accountCode}",method=RequestMethod.GET)
    public Account getAccount(@PathVariable String accountCode) {
        return null;
    }

    @RequestMapping(value="/accounts/clientAccounts/{idClient}",method=RequestMethod.GET)
    public List<Account> getClientAccounts(@PathVariable long idClient){
        return null;
    }

    @RequestMapping(value="/accounts/accountsCreatedByEmpl",method=RequestMethod.GET)
    public List<Account> getAccountsCreatedByEmpl(@PathVariable long idEmploye){
        return null;
    }

    @RequestMapping(value="/accounts/all",method=RequestMethod.GET)
    public List<Account> getAllAccounts(){
        return null;
    }
}
