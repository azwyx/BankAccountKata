package amh.kata.bankaccount;

import amh.kata.bankaccount.entities.*;
import amh.kata.bankaccount.entities.exceptions.AccountNotFoundException;
import amh.kata.bankaccount.entities.exceptions.AmountLowerThanBalanceException;
import amh.kata.bankaccount.entities.exceptions.AmountMinMaxValueException;
import amh.kata.bankaccount.tools.OperationRequest;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BankAccountIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    Long idClient1;
    Long idClient2;
    @Test
    public void step1_shouldCreateClient() throws Exception {

        Client client_1 = new Client("HARIRI", "Amine", "azerty", "azertypass");

        HttpEntity<Client> request = new HttpEntity<Client>(client_1);
        ResponseEntity<Client> response = restTemplate.exchange("/clients/saveClient", HttpMethod.POST, request, Client.class);

        // assert

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getIdClient()).isNotNull();
        assertThat(response.getBody().getFirstname()).isEqualTo("HARIRI");
        assertThat(response.getBody().getLogin()).isEqualTo("azerty");

        idClient1 = response.getBody().getIdClient();
    }

    @Test
    public void step2_shouldCreateAnOtherClient() throws Exception {

        Client client_2 = new Client("John", "Steven", "stevenJ", "steven12345");

        // act
        HttpEntity<Client> request = new HttpEntity<Client>(client_2);
        ResponseEntity<Client> response = restTemplate.exchange("/clients/saveClient", HttpMethod.POST, request, Client.class);

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getIdClient()).isNotNull();
        assertThat(response.getBody().getFirstname()).isEqualTo("John");
        assertThat(response.getBody().getLogin()).isEqualTo("stevenJ");

        idClient2 = response.getBody().getIdClient();
    }

    @Test
    public void step3_shouldCreateAnAccount() throws Exception {

        ResponseEntity<Client> createdClient1 = restTemplate.getForEntity("/clients/1", Client.class);
        Client client_1 = createdClient1.getBody();

        Account account_1 = new Account("account_1", new Date(), 2000, client_1);

        // act
        HttpEntity<Account> request = new HttpEntity<Account>(account_1);
        ResponseEntity<Account> response = restTemplate.exchange("/accounts/saveAccount", HttpMethod.POST, request, Account.class);

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getAccountCode()).isEqualTo("account_1");
        assertThat(response.getBody().getBalance()).isEqualTo(2000);
        assertThat(response.getBody().getClient().getIdClient()).isEqualTo(1L);
        assertThat(response.getBody().getClient().getFirstname()).isEqualTo("HARIRI");
    }

    @Test
    public void step4_shouldCreateAnOtherAccount() throws Exception {

        ResponseEntity<Client> createdClient2 = restTemplate.getForEntity("/clients/2", Client.class);
        Client client_2 = createdClient2.getBody();

        Account account_2 = new Account("account_2", new Date(), 3000, client_2);

        // act
        HttpEntity<Account> request = new HttpEntity<Account>(account_2);
        ResponseEntity<Account> response = restTemplate.exchange("/accounts/saveAccount", HttpMethod.POST, request, Account.class);

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getAccountCode()).isEqualTo("account_2");
        assertThat(response.getBody().getBalance()).isEqualTo(3000);
        assertThat(response.getBody().getClient().getIdClient()).isEqualTo(2L);
        assertThat(response.getBody().getClient().getFirstname()).isEqualTo("John");
    }

    @Test
    public void step4_shouldMakeADepositInClient1Account() throws Exception {

        OperationRequest operationRequest = new OperationRequest("account_1", 500);

        // act
        HttpEntity<OperationRequest> request = new HttpEntity<OperationRequest>(operationRequest);
        ResponseEntity<Deposit> response = restTemplate.exchange("/operations/deposit", HttpMethod.POST, request,
                Deposit.class);

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getAmount()).isEqualTo(500);
        assertThat(response.getBody().getAccount().getAccountCode()).isEqualTo("account_1");
        assertThat(response.getBody().getAccount().getClient().getIdClient()).isEqualTo(1L);
        assertThat(response.getBody().getAccount().getBalance()).isEqualTo(2500);
        assertThat(response.getBody() instanceof Deposit);
    }

    @Test
    public void step5_shouldMakeAWithdrawFromClient2Account() throws Exception {

        OperationRequest operationRequest = new OperationRequest("account_2", 700);

        // act
        HttpEntity<OperationRequest> request = new HttpEntity<OperationRequest>(operationRequest);
        ResponseEntity<Withdrawal> response = restTemplate.exchange("/operations/withdrawal", HttpMethod.POST, request,
                Withdrawal.class);

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getAmount()).isEqualTo(700);
        assertThat(response.getBody().getAccount().getAccountCode()).isEqualTo("account_2");
        assertThat(response.getBody().getAccount().getClient().getIdClient()).isEqualTo(2L);
        assertThat(response.getBody().getAccount().getBalance()).isEqualTo(2300);
        assertThat(response.getBody() instanceof Withdrawal);
    }

    @Test
    public void step6_shouldMakeATransferFromAccount1ToAccount2() throws Exception {

        OperationRequest operationRequest = new OperationRequest("account_1", 900, "account_2");

        // act
        HttpEntity<OperationRequest> request = new HttpEntity<OperationRequest>(operationRequest);
        ResponseEntity<Transfer> response = restTemplate.exchange("/operations/transfer", HttpMethod.POST, request,
                Transfer.class);

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getAmount()).isEqualTo(900);
        assertThat(response.getBody().getAccount().getAccountCode()).isEqualTo("account_1");
        assertThat(response.getBody().getAccount().getClient().getIdClient()).isEqualTo(1L);
        assertThat(response.getBody().getAccount().getBalance()).isEqualTo(1600);
        assertThat(response.getBody().getToAccount().getAccountCode()).isEqualTo("account_2");
        assertThat(response.getBody().getToAccount().getClient().getIdClient()).isEqualTo(2L);
        assertThat(response.getBody().getToAccount().getBalance()).isEqualTo(3200);
        assertThat(response.getBody() instanceof Transfer);
    }

    @Test
    public void step7_shouldMakeATransferFromAccount2ToAccount1() throws Exception {

        OperationRequest operationRequest = new OperationRequest("account_2", 300, "account_1");

        // act
        HttpEntity<OperationRequest> request = new HttpEntity<OperationRequest>(operationRequest);
        ResponseEntity<Transfer> response = restTemplate.exchange("/operations/transfer", HttpMethod.POST, request,
                Transfer.class);

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getAmount()).isEqualTo(300);
        assertThat(response.getBody().getAccount().getAccountCode()).isEqualTo("account_2");
        assertThat(response.getBody().getAccount().getClient().getIdClient()).isEqualTo(2L);
        assertThat(response.getBody().getAccount().getBalance()).isEqualTo(2900);
        assertThat(response.getBody().getToAccount().getAccountCode()).isEqualTo("account_1");
        assertThat(response.getBody().getToAccount().getClient().getIdClient()).isEqualTo(1L);
        assertThat(response.getBody().getToAccount().getBalance()).isEqualTo(1900);
        assertThat(response.getBody() instanceof Transfer);
    }

    @Test
    public void step8_shouldGetTransferHistoryForAllAccount1Transfers() throws Exception {
        // act
        ResponseEntity<Transfer[]> response = restTemplate.getForEntity("/operations/transferHistory/account_1", Transfer[].class);

        // assert
        Transfer[] transfers = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(transfers.length).isEqualTo(2);
        assertThat(transfers[0].getAccount().getAccountCode()).isEqualTo("account_1");
        assertThat(transfers[0].getAmount()).isEqualTo(900);
        assertThat(transfers[0].getToAccount().getAccountCode()).isEqualTo("account_2");
        assertThat(transfers[1].getAccount().getAccountCode()).isEqualTo("account_2");
        assertThat(transfers[1].getAmount()).isEqualTo(300);
        assertThat(transfers[1].getToAccount().getAccountCode()).isEqualTo("account_1");

    }
  }
