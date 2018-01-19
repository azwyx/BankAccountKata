package amh.kata.bankaccount.RestControllers;

import amh.kata.bankaccount.Services.AccountService;
import amh.kata.bankaccount.entities.Account;
import amh.kata.bankaccount.entities.Client;
import amh.kata.bankaccount.entities.Employe;
import amh.kata.bankaccount.entities.exceptions.AccountAlreadyExistException;
import amh.kata.bankaccount.entities.exceptions.AccountNotFoundException;
import amh.kata.bankaccount.entities.exceptions.ClientNotFoundException;
import amh.kata.bankaccount.entities.exceptions.EmployeNotFoundException;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountRestController.class)
public class AccountRestControllerTest {
    @Autowired
    private MockMvc mokMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper mapper;

    Client client;
    Employe employe_1;
    Employe employe_2;
    Account account_1;
    Account account_2;
    List<Account> account_list;

    @Before
    public void setUp() throws Exception {
        client = new Client("HARIRI", "Amine", "azerty", "azertypass");
        employe_1 = new Employe((long) 3, "Steve");
        employe_1 = new Employe((long) 5, "John");
        account_1 = new Account("account_1", new Date(), client, employe_1);
        account_2 = new Account("account_2", new Date(), client, employe_2);
        account_list= new ArrayList<Account>();
        account_list.add(account_1);
        account_list.add(account_2);
    }
    // creer un compte avec succes
    @Test
    public void createAccount_TestSuccess_ShouldReturnCreatedAccount() throws Exception {
        String json = mapper.writeValueAsString(account_1);

        given(accountService.saveAccount(anyObject())).willReturn(account_1);

        mokMvc.perform(MockMvcRequestBuilders.post("/account/saveAccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("codeAccount").value("account_1"))
                .andExpect(jsonPath("client.firstname").value("HARIRI"))
                .andExpect(jsonPath("employe.idEmploye").value((long)3));

    }
    // creer un compte echoue ( id existant )
    @Test
    public void createAccount_TestFail_ShouldReturnAlreadyExistException() throws Exception {
        String json = mapper.writeValueAsString(account_1);
        given(accountService.saveAccount(anyObject())).willThrow(new AccountAlreadyExistException("Account by this code already exist"));

        mokMvc.perform(MockMvcRequestBuilders.post("/account/saveAccount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAlreadyReported());
    }
    // consulter un compteDetails avec succes ( compte existant )
    @Test
    public void getAccountDetails_TestSuccess_ShouldReturnAccount() throws Exception {
        given(accountService.getAccount(anyString())).willReturn(account_1);

        mokMvc.perform(MockMvcRequestBuilders.get("/accounts/accountDetails/{accountCode}", "account_1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("codeAccount").value("account_1"))
                .andExpect(jsonPath("client.firstname").value("HARIRI"))
                .andExpect(jsonPath("employe.idEmploye").value((long)3));
    }
    // consulter un compteDetails FAIL (inexistant)
    @Test
    public void getAccountDetails_TestFail_ShouldReturnNotFoundException() throws Exception {
        given(accountService.getAccount(anyString())).willThrow(new AccountNotFoundException("Account Not Found"));

        mokMvc.perform(MockMvcRequestBuilders.get("/accounts/accountDetails/{accountCode}", "account_1"))
                .andExpect(status().isNotFound());

    }
    // voir list des comptes d'un client
    @Test
    public void getClientAccounts_TestSuccess_ShouldReturnAccountList() throws Exception {

        given(accountService.getClientAccounts(anyLong())).willReturn(account_list);

        mokMvc.perform(MockMvcRequestBuilders.get("/accounts/clientAccounts/{idClient}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("account[0].codeAccount").value(account_list.get(0).getAccountCode()))
                .andExpect(jsonPath("account[0].client.firstname").value(account_list.get(0).getClient().getFirstname()))
                .andExpect(jsonPath("account[0].employe.idEmploye").value(account_list.get(0).getEmploye().getIdEmploye()))
                .andExpect(jsonPath("account[1].codeAccount").value(account_list.get(1).getAccountCode()))
                .andExpect(jsonPath("account[1].client.firstname").value(account_list.get(1).getClient().getFirstname()))
                .andExpect(jsonPath("account[1].employe.idEmploye").value(account_list.get(1).getEmploye().getIdEmploye()));
    }
    // voir list des compte echoue car le client n'a pas de compte
    @Test
    public void getClientAccounts_Test_ShouldReturnException() throws Exception {
        given(accountService.getClientAccounts(anyLong())).willThrow(new AccountNotFoundException("This client doesn't has any account"));

        mokMvc.perform(MockMvcRequestBuilders.get("/accounts/clientAccounts/{idClient}", 6))
                .andExpect(status().isNotFound());
    }
    // voir list des compte echoue car le client n'existe pas
    @Test
    public void getClientAccounts_TestFail_ShouldReturnClientNotFoundException() throws Exception {
        given(accountService.getClientAccounts(anyLong())).willThrow(new ClientNotFoundException("Client Not Found"));

        mokMvc.perform(MockMvcRequestBuilders.get("/accounts/clientAccounts/{idClient}", 7))
                .andExpect(status().isNotFound());
    }
    // voir liste des compte crées par un employe
    @Test
    public void getAccountsCreatedByEmpl_TestSuccess_ShouldReturnAccountList() throws Exception {
        List<Account> test_list = new ArrayList<Account>();
        test_list.add(account_1);
        given(accountService.getAccountsCreatedByEmpl(anyLong())).willReturn(test_list);

        mokMvc.perform(MockMvcRequestBuilders.get("/accounts/accountsCreatedByEmpl/{idEmploye}", 3))
                .andExpect(status().isOk())
                .andExpect(jsonPath("account[0].codeAccount").value(account_list.get(0).getAccountCode()))
                .andExpect(jsonPath("account[0].client.firstname").value(account_list.get(0).getClient().getFirstname()))
                .andExpect(jsonPath("account[0].employe.idEmploye").value(account_list.get(0).getEmploye().getIdEmploye()))
                .andExpect(jsonPath("account[1].codeAccount").value(account_list.get(1).getAccountCode()))
                .andExpect(jsonPath("account[1].client.firstname").value(account_list.get(1).getClient().getFirstname()))
                .andExpect(jsonPath("account[1].employe.idEmploye").value(account_list.get(1).getEmploye().getIdEmploye()));
    }

    // voir liste des compte crées par un employe
    @Test
    public void getAccountsCreatedByEmpl_TestFail_ShouldReturnEmployeNotFoundException() throws Exception {
        given(accountService.getAccountsCreatedByEmpl(anyLong())).willThrow(new EmployeNotFoundException("Employe Not Found"));

        mokMvc.perform(MockMvcRequestBuilders.get("/accounts/accountsCreatedByEmpl/{idEmploye}", 7))
                .andExpect(status().isNotFound());
    }
    // voir list des comptes
    @Test
    public void getAccountList_TestSuccess_ShouldReturnAccountList() throws Exception {
        given(accountService.getAllAccounts()).willReturn(account_list);

        mokMvc.perform(MockMvcRequestBuilders.get("/accounts/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("account[0].codeAccount").value(account_list.get(0).getAccountCode()))
                .andExpect(jsonPath("account[0].client.firstname").value(account_list.get(0).getClient().getFirstname()))
                .andExpect(jsonPath("account[0].employe.idEmploye").value(account_list.get(0).getEmploye().getIdEmploye()))
                .andExpect(jsonPath("account[1].codeAccount").value(account_list.get(1).getAccountCode()))
                .andExpect(jsonPath("account[1].client.firstname").value(account_list.get(1).getClient().getFirstname()))
                .andExpect(jsonPath("account[1].employe.idEmploye").value(account_list.get(1).getEmploye().getIdEmploye()));
    }
}
