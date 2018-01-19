package amh.kata.bankaccount.RestControllers;

import amh.kata.bankaccount.Services.AccountServiceImpl;
import amh.kata.bankaccount.Services.IAccountService;
import amh.kata.bankaccount.entities.Account;
import amh.kata.bankaccount.entities.exceptions.AccountAlreadyExistException;
import amh.kata.bankaccount.entities.exceptions.AccountNotFoundException;
import amh.kata.bankaccount.entities.exceptions.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountRestController {

    @Autowired
    private IAccountService accountService;

    @RequestMapping(value="/accounts/saveAccount",method= RequestMethod.POST)
    public Account saveAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @RequestMapping(value="/accounts/accountDetails/{accountCode}",method=RequestMethod.GET)
    public Account getAccount(@PathVariable String accountCode) {
        return accountService.getAccount(accountCode);
    }

    @RequestMapping(value="/accounts/clientAccounts/{idClient}",method=RequestMethod.GET)
    public List<Account> getClientAccounts(@PathVariable long idClient){
        return accountService.getClientAccounts(idClient);
    }

    @RequestMapping(value="/accounts/all",method=RequestMethod.GET)
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @RequestMapping(value="/accounts/delete/{accountCode}", method=RequestMethod.GET)
    public String deleteAccount(@PathVariable String accountCode) throws ClientNotFoundException {
        accountService.deleteAccount(accountCode);
        return "Account by code "+accountCode+" is successfully deleted";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void AccountNotFoundHandler(AccountNotFoundException e){

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    private void AccountAlreadyExistHandler(AccountAlreadyExistException e){

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void ClientNotFoundHandler(ClientNotFoundException e){

    }

}
