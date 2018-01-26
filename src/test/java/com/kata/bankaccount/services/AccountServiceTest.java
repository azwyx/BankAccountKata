package com.kata.bankaccount.services;

import com.kata.bankaccount.dao.AccountRepository;
import com.kata.bankaccount.dao.ClientRepository;
import com.kata.bankaccount.entities.Account;
import com.kata.bankaccount.entities.Client;
import com.kata.bankaccount.entities.exceptions.AccountAlreadyExistException;
import com.kata.bankaccount.entities.exceptions.AccountNotFoundException;
import com.kata.bankaccount.entities.exceptions.ClientNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    Client client;
    Account account_1;
    Account account_2;
    List<Account> account_list;


    @Before
    public void setUp() throws Exception {
        client = new Client("HARIRI", "Amine", "azerty", "azertypass");
        account_1 = new Account("account_1", new Date(), client);
        account_2 = new Account("account_2", new Date(), client);
        account_list= new ArrayList<Account>();
        account_list.add(account_1);
        account_list.add(account_2);
    }
    // creer un compte avec succes
    @Test
    public void createAccount_TestSuccess_ShouldReturnCreatedAccount(){
        given(accountRepository.findOne(anyString())).willReturn(null);
        given(accountRepository.save(any(Account.class))).willReturn(account_1);

        Account account = accountService.saveAccount(account_1);

        assertThat(account.getAccountCode()).isEqualTo("account_1");
        assertThat(account.getClient().getFirstname()).isEqualTo("HARIRI");
    }

    // creer un compte echoue ( id inexistant )
    @Test(expected = AccountAlreadyExistException.class)
    public void createAccount_TestFail_ShouldReturnAlreadyExistException(){
        given(accountRepository.findOne(anyString())).willReturn(new Account());
        Account account = accountService.saveAccount(account_1);
    }

    @Test
    public void updateAccount_TestSuccess_ShouldReturnUpdatedAccount() throws Exception {
        given(accountRepository.findOne(anyString())).willReturn(account_1);
        given(accountRepository.save(any(Account.class))).willReturn(account_1);

        Account account = accountService.updateAccount(account_1);

        assertThat(account.getAccountCode()).isEqualTo("account_1");
        assertThat(account.getClient().getFirstname()).isEqualTo("HARIRI");
    }
    // mise à jour d'un compte echoue ( id inexistant )
    @Test(expected = AccountNotFoundException.class)
    public void updateAccount_TestFail_ShouldReturnNotFoundException() throws Exception {
        given(accountRepository.findOne(anyString())).willReturn(null);
        Account account = accountService.updateAccount(account_1);
    }

    // consulter un compteDetails avec succes ( compte existant )
    @Test
    public void getAccountDetails_TestSuccess_ShouldReturnAccount() throws Exception {
        given(accountRepository.findOne(anyString())).willReturn(account_1);

        Account account = accountService.getAccount("account_1");

        assertThat(account.getAccountCode()).isEqualTo("account_1");
        assertThat(account.getClient().getFirstname()).isEqualTo("HARIRI");
        assertThat(account.getBalance()).isEqualTo(0);
    }
    // consulter un compteDetails FAIL (inexistant)
    @Test(expected = AccountNotFoundException.class)
    public void getAccountDetails_TestFail_ShouldReturnNotFoundException() throws Exception {
        given(accountRepository.findOne(anyString())).willReturn(null);
        Account account = accountService.updateAccount(account_1);

    }

    // voir list des comptes d'un client
    @Test
    public void getClientAccounts_TestSuccess_ShouldReturnAccountList() throws Exception {
        given(clientRepository.findByIdClient(anyLong())).willReturn(client);
        given(accountRepository.findByClient(anyLong())).willReturn(account_list);

        List<Account> rslt_list = accountService.getClientAccounts(1);

        assertThat(rslt_list.get(0).getAccountCode()).isEqualTo("account_1");
        assertThat(rslt_list.get(0).getClient().getFirstname()).isEqualTo("HARIRI");
        assertThat(rslt_list.get(0).getBalance()).isEqualTo(0);
        assertThat(rslt_list.get(1).getAccountCode()).isEqualTo("account_2");
        assertThat(rslt_list.get(1).getClient().getFirstname()).isEqualTo("HARIRI");
        assertThat(rslt_list.get(1).getBalance()).isEqualTo(0);
    }
    // voir list des compte echoue car le client n'a pas de compte
    @Test(expected = AccountNotFoundException.class)
    public void getClientAccounts_Test_ShouldReturnException() throws Exception {
        given(clientRepository.findByIdClient(anyLong())).willReturn(client);
        given(accountRepository.findByClient(anyLong())).willReturn(null);

        List<Account> rslt_list = accountService.getClientAccounts(1);
    }
    // voir list des compte echoue car le client n'existe pas
    @Test(expected = ClientNotFoundException.class)
    public void getClientAccounts_TestFail_ShouldReturnClientNotFoundException() throws Exception {
        given(clientRepository.findByIdClient(anyLong())).willReturn(null);
        given(accountRepository.findByClient(anyLong())).willReturn(null);

        List<Account> rslt_list = accountService.getClientAccounts(1);
    }

    // voir list des comptes
    @Test
    public void getAccountList_TestSuccess_ShouldReturnAccountList() throws Exception {
        given(accountRepository.findAll()).willReturn(account_list);

        List<Account> rslt_list = accountService.getAllAccounts();

        assertThat(rslt_list.get(0).getAccountCode()).isEqualTo("account_1");
        assertThat(rslt_list.get(0).getClient().getFirstname()).isEqualTo("HARIRI");
        assertThat(rslt_list.get(0).getBalance()).isEqualTo(0);
        assertThat(rslt_list.get(1).getAccountCode()).isEqualTo("account_2");
        assertThat(rslt_list.get(1).getClient().getFirstname()).isEqualTo("HARIRI");
        assertThat(rslt_list.get(1).getBalance()).isEqualTo(0);
    }

    @Test
    public void deleteAccount_TestSucces_ShouldReturn_StringOK() throws Exception {

        doReturn(new Account()).when(accountRepository).findOne(anyString());
        doNothing().when(accountRepository).delete(anyString());

        accountService.deleteAccount("account_1");

        verify(accountRepository, times(1)).findOne(anyString());
        verify(accountRepository, times(1)).delete(anyString());

    }

    @Test(expected = AccountNotFoundException.class)
    public void deleteClient_TestFail_ShouldReturn_404_Not_Found() throws Exception {

        doReturn(null).when(accountRepository).findOne(anyString());

        accountService.deleteAccount("account_1");
    }
}
