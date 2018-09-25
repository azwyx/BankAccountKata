package com.kata.bankaccount.restcontrollers;

import com.kata.bankaccount.services.IAccountService;
import com.kata.bankaccount.entities.Account;
import com.kata.bankaccount.entities.Client;
import com.kata.bankaccount.services.exceptions.AccountAlreadyExistException;
import com.kata.bankaccount.services.exceptions.AccountNotFoundException;
import com.kata.bankaccount.services.exceptions.ClientNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountRestController.class)
public class AccountRestControllerTest {
    @Autowired
    private MockMvc mokMvc;

    @MockBean
    private IAccountService accountService;

    @Autowired
    private ObjectMapper mapper;

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
    public void createAccount_TestSuccess_ShouldReturnCreatedAccount() throws Exception {
        String json = mapper.writeValueAsString(account_1);

        given(accountService.saveAccount(anyObject())).willReturn(account_1);

        mokMvc.perform(MockMvcRequestBuilders.post("/accounts/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("accountCode").value("account_1"))
                .andExpect(jsonPath("client.firstname").value("HARIRI"));

    }
    // creer un compte echoue ( id inexistant )
    @Test
    public void createAccount_TestFail_ShouldReturnAlreadyExistException() throws Exception {
        String json = mapper.writeValueAsString(account_1);
        given(accountService.saveAccount(anyObject())).willThrow(new AccountAlreadyExistException());

        mokMvc.perform(MockMvcRequestBuilders.post("/accounts/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAlreadyReported());
    }

    @Test
    public void updateAccount_TestSuccess_ShouldReturnUpdatedAccount() throws Exception {
        String json = mapper.writeValueAsString(account_1);

        given(accountService.saveAccount(anyObject())).willReturn(account_1);

        mokMvc.perform(MockMvcRequestBuilders.post("/accounts/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("accountCode").value("account_1"))
                .andExpect(jsonPath("client.firstname").value("HARIRI"));

    }
    // mise à jour d'un compte echoue ( id inexistant )
    @Test
    public void updateAccount_TestFail_ShouldReturnNotFoundException() throws Exception {
        String json = mapper.writeValueAsString(account_1);
        given(accountService.saveAccount(anyObject())).willThrow(new AccountNotFoundException());

        mokMvc.perform(MockMvcRequestBuilders.post("/accounts/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // consulter un compteDetails avec succes ( compte existant )
    @Test
    public void getAccountDetails_TestSuccess_ShouldReturnAccount() throws Exception {
        given(accountService.getAccount(anyString())).willReturn(account_1);

        mokMvc.perform(MockMvcRequestBuilders.get("/accounts/details/{accountCode}", "account_1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("accountCode").value("account_1"))
                .andExpect(jsonPath("client.firstname").value("HARIRI"));
    }
    // consulter un compteDetails FAIL (inexistant)
    @Test
    public void getAccountDetails_TestFail_ShouldReturnNotFoundException() throws Exception {
        given(accountService.getAccount(anyString())).willThrow(new AccountNotFoundException());

        mokMvc.perform(MockMvcRequestBuilders.get("/accounts/details/{accountCode}", "account_1"))
                .andExpect(status().isNotFound());

    }
    // voir list des comptes d'un client
    @Test
    public void getClientAccounts_TestSuccess_ShouldReturnAccountList() throws Exception {

        given(accountService.getClientAccounts(anyLong())).willReturn(account_list);

        mokMvc.perform(MockMvcRequestBuilders.get("/accounts/client/{idClient}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].accountCode").value(account_list.get(0).getAccountCode()))
                .andExpect(jsonPath("$.[0].client.firstname").value(account_list.get(0).getClient().getFirstname()))
                .andExpect(jsonPath("$.[1].accountCode").value(account_list.get(1).getAccountCode()))
                .andExpect(jsonPath("$.[1].client.firstname").value(account_list.get(1).getClient().getFirstname()));
    }
    // voir list des compte echoue car le client n'a pas de compte
    @Test
    public void getClientAccounts_Test_ShouldReturnException() throws Exception {
        given(accountService.getClientAccounts(anyLong())).willThrow(new AccountNotFoundException());

        mokMvc.perform(MockMvcRequestBuilders.get("/accounts/client/{idClient}", 6))
                .andExpect(status().isNotFound());
    }
    // voir list des compte echoue car le client n'existe pas
    @Test
    public void getClientAccounts_TestFail_ShouldReturnClientNotFoundException() throws Exception {
        given(accountService.getClientAccounts(anyLong())).willThrow(new ClientNotFoundException());

        mokMvc.perform(MockMvcRequestBuilders.get("/accounts/client/{idClient}", 7))
                .andExpect(status().isNotFound());
    }

    // voir list des comptes
    @Test
    public void getAccountList_TestSuccess_ShouldReturnAccountList() throws Exception {
        given(accountService.getAllAccounts()).willReturn(account_list);

        mokMvc.perform(MockMvcRequestBuilders.get("/accounts/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].accountCode").value(account_list.get(0).getAccountCode()))
                .andExpect(jsonPath("$.[0].client.firstname").value(account_list.get(0).getClient().getFirstname()))
                .andExpect(jsonPath("$.[1].accountCode").value(account_list.get(1).getAccountCode()))
                .andExpect(jsonPath("$.[1].client.firstname").value(account_list.get(1).getClient().getFirstname()));
    }

    @Test
    public void deleteAccount_TestSucces_ShouldReturn_StringOK() throws Exception {

        doNothing().when(accountService).deleteAccount(anyString());

        mokMvc.perform(MockMvcRequestBuilders.get("/accounts/delete/{accountCode}", "account_1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Account by code account_1 is successfully deleted"));
        verify(accountService, times(1)).deleteAccount(anyString());
    }

    @Test
    public void deleteClient_TestFail_ShouldReturn_404_Not_Found() throws Exception {

        doThrow(new AccountNotFoundException()).when(accountService).deleteAccount(anyString());

        mokMvc.perform(MockMvcRequestBuilders.get("/accounts/delete/{accountCode}", "account_1"))
                .andExpect(status().isNotFound());

        verify(accountService, times(1)).deleteAccount(anyString());
    }
}
