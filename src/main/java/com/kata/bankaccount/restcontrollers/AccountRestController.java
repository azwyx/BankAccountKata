package com.kata.bankaccount.restcontrollers;

import com.kata.bankaccount.services.IAccountService;
import com.kata.bankaccount.entities.Account;
import com.kata.bankaccount.entities.exceptions.AccountAlreadyExistException;
import com.kata.bankaccount.entities.exceptions.AccountNotFoundException;
import com.kata.bankaccount.entities.exceptions.ClientNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountRestController {

    @Autowired
    private IAccountService accountService;

    @RequestMapping(value="/saveAccount",method= RequestMethod.POST)
    public Account saveAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @RequestMapping(value="/updateAccount",method= RequestMethod.POST)
    public Account upadteAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @RequestMapping(value="/accountDetails/{accountCode}",method=RequestMethod.GET)
    public Account getAccount(@PathVariable String accountCode) {
        return accountService.getAccount(accountCode);
    }

    @RequestMapping(value="/clientAccounts/{idClient}",method=RequestMethod.GET)
    public List<Account> getClientAccounts(@PathVariable long idClient){
        return accountService.getClientAccounts(idClient);
    }

    @RequestMapping(value="/all",method=RequestMethod.GET)
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @RequestMapping(value="/delete/{accountCode}", method=RequestMethod.GET)
    public String deleteAccount(@PathVariable String accountCode) throws ClientNotFoundException {
        accountService.deleteAccount(accountCode);
        return "Account by code "+accountCode+" is successfully deleted";
    }
}
