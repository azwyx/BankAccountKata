package amh.kata.bankaccount;

import amh.kata.bankaccount.entities.Account;
import amh.kata.bankaccount.entities.Operation;
import amh.kata.bankaccount.entities.exceptions.AccountNotFoundException;
import amh.kata.bankaccount.entities.exceptions.AmountLowerThanBalance;
import amh.kata.bankaccount.entities.exceptions.AmountMinMaxValueException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class BankAccountIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void withDrawalSuccessTest() throws AccountNotFoundException, AmountLowerThanBalance, AmountMinMaxValueException {
        String transactionUrl = "/withdraw";

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(transactionUrl)
                // Add query parameter
                .queryParam("code", "compte1")
                .queryParam("amount", "2400")
                .queryParam("empCode", "1");

        ResponseEntity<Account> response = restTemplate.getForEntity(builder.toUriString(), Account.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getClient().getIdClient()).isEqualTo(1);
        assertThat(response.getBody().getBalance()).isEqualTo(3400);
    }

    @Test
    public void depositSuccessTest() throws AccountNotFoundException, AmountMinMaxValueException {
        String transactionUrl = "/deposit";

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(transactionUrl)
                // Add query parameter
                .queryParam("code", "compte2")
                .queryParam("amount", "900")
                .queryParam("empCode", "1");

        ResponseEntity<Account> response = restTemplate.getForEntity(builder.toUriString(), Account.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getClient().getIdClient()).isEqualTo(2);
        assertThat(response.getBody().getBalance()).isEqualTo(3400);
    }

    @Test
    public void transferSuccessTest() throws AccountNotFoundException, AmountMinMaxValueException {
        String transactionUrl = "/transfer";

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(transactionUrl)
                // Add query parameter
                .queryParam("cpt1", "compte2")
                .queryParam("cpt2", "compte1")
                .queryParam("amount", "1200")
                .queryParam("empCode", "1");

        ResponseEntity<Account> response = restTemplate.getForEntity(builder.toUriString(), Account.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getClient().getIdClient()).isEqualTo(2);
        assertThat(response.getBody().getBalance()).isEqualTo(400);
    }

    @Test
    public void operationsHistory() throws Exception {
        List<Operation> searchList;
        String transactionUrl = "/history";

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(transactionUrl)
                // Add query parameter
                .queryParam("code", "compte1");

        ResponseEntity<Operation[]> response = restTemplate.getForEntity(builder.toUriString(), Operation[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().length).isEqualTo(2);

    }
}
