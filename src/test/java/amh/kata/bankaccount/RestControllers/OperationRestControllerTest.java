package amh.kata.bankaccount.RestControllers;

import amh.kata.bankaccount.Services.IAccountService;
import amh.kata.bankaccount.Services.OperationService;
import amh.kata.bankaccount.entities.*;
import amh.kata.bankaccount.entities.exceptions.AccountNotFoundException;
import amh.kata.bankaccount.entities.exceptions.AmountMinMaxValueException;
import amh.kata.bankaccount.tools.OperationRequest;
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
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OperationRestController.class)
public class OperationRestControllerTest{
    @Autowired
    private MockMvc mokMvc;

    @MockBean
    private OperationService operationService;

    @Autowired
    private ObjectMapper mapper;

    private static final double MIN_VALUE = 0;
    private static final double MAX_VALUE = 999999999;
    private static final String TYPE = "T";

    private Client client;
    private Account account_1;
    private Account account_2;
    private Operation opDeposit;
    private Operation opWithdrawal;
    private Transfer opTransfer;



    @Before
    public void setUp(){
        client = new Client("HARIRI", "Amine", "azerty", "azertypass");
        account_1 = new Account("account_1", new Date(), 2000, client);
        account_2 = new Account("account_2", new Date(), 3000, client);

        opDeposit = new Deposit();
        opDeposit.setDateOperation(new Date());
        opDeposit.setAmount(800);
        opDeposit.setAccount(account_1);

        opWithdrawal = new Withdrawal();
        opWithdrawal.setDateOperation(new Date());
        opWithdrawal.setAmount(700);
        opWithdrawal.setAccount(account_2);

        opTransfer = new Transfer();
        opTransfer.setDateOperation(new Date());
        opTransfer.setAmount(500);
        opTransfer.setAccount(account_1);
        opTransfer.setToAccount(account_2);
    }

    @Test
    public void deposit_ShouldReturnCreatedOperation() throws Exception {


        OperationRequest opRequest = new OperationRequest("account_1", 800);
        String jsonRequest = mapper.writeValueAsString(opRequest);

        given(operationService.deposit(anyObject())).willReturn(opDeposit);

        mokMvc.perform(MockMvcRequestBuilders.post("/operations/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("amount").value(800))
                .andExpect(jsonPath("account.accountCode").value("account_1"))
                .andExpect(jsonPath("account.balance").value(2000));
    }

    @Test
    public void deposit_ShoulReturnAccountNotFoundException() throws Exception {
        OperationRequest opRequest = new OperationRequest("account_1", 800);
        String jsonRequest = mapper.writeValueAsString(opRequest);

        given(operationService.deposit(anyObject())).willThrow(new AccountNotFoundException("Account Not Found"));

        mokMvc.perform(MockMvcRequestBuilders.post("/operations/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deposit_ShoulReturnAmountMinMaxValueException() throws Exception {
        OperationRequest opRequest = new OperationRequest("account_1", MIN_VALUE);
        String jsonRequest = mapper.writeValueAsString(opRequest);

        given(operationService.deposit(anyObject())).willThrow(new AmountMinMaxValueException("Bad Request ! amount's value is not valid"));

        mokMvc.perform(MockMvcRequestBuilders.post("/operations/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void withdrawal_ShouldReturnCreatedOperation() throws Exception {
        OperationRequest opRequest = new OperationRequest("account_2", 700);
        String jsonRequest = mapper.writeValueAsString(opRequest);

        given(operationService.withdrawal(anyObject())).willReturn(opWithdrawal);

        mokMvc.perform(MockMvcRequestBuilders.post("/operations/withdrawal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("amount").value(700))
                .andExpect(jsonPath("account.accountCode").value("account_2"))
                .andExpect(jsonPath("account.balance").value(3000));
    }

    @Test
    public void withdrawal_ShoulReturnAccountNotFoundException() throws Exception {
        OperationRequest opRequest = new OperationRequest("account_2", 700);
        String jsonRequest = mapper.writeValueAsString(opRequest);

        given(operationService.withdrawal(anyObject())).willThrow(new AccountNotFoundException("Account Not Found"));

        mokMvc.perform(MockMvcRequestBuilders.post("/operations/withdrawal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void withrawal_ShoulReturnAmountMinMaxValueException() throws Exception {
        OperationRequest opRequest = new OperationRequest("account_2", MAX_VALUE);
        String jsonRequest = mapper.writeValueAsString(opRequest);

        given(operationService.withdrawal(anyObject())).willThrow(new AmountMinMaxValueException("Bad Request ! amount's value is not valid"));

        mokMvc.perform(MockMvcRequestBuilders.post("/operations/withdrawal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void withrawal_ShoulReturnAmountLowerThanBalance() throws Exception {
        OperationRequest opRequest = new OperationRequest("account_2", 4000);
        String jsonRequest = mapper.writeValueAsString(opRequest);

        given(operationService.withdrawal(anyObject())).willThrow(new AmountMinMaxValueException("Bad Request ! amount's value is lower than balance"));

        mokMvc.perform(MockMvcRequestBuilders.post("/operations/withdrawal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void Transfer_ShouldReturnCreatedOperation() throws Exception {
        OperationRequest opRequest = new OperationRequest("account_1", 500, "account_2");
        String jsonRequest = mapper.writeValueAsString(opRequest);

        given(operationService.transfer(anyObject())).willReturn(opTransfer);

        mokMvc.perform(MockMvcRequestBuilders.post("/operations/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("amount").value(500))
                .andExpect(jsonPath("account.accountCode").value("account_1"))
                .andExpect(jsonPath("account.balance").value(2000))
                .andExpect(jsonPath("toAccount.accountCode").value("account_2"))
                .andExpect(jsonPath("toAccount.balance").value(3000));
    }

    @Test
    public void Transfer_ShouldReturnAccountNotFoundException() throws Exception {
        OperationRequest opRequest = new OperationRequest("account_1", 500, "account_2");
        String jsonRequest = mapper.writeValueAsString(opRequest);

        given(operationService.transfer(anyObject())).willThrow(new AccountNotFoundException("Account Not Found"));

        mokMvc.perform(MockMvcRequestBuilders.post("/operations/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void Transfer_ShouldReturnAmountMinMaxValueException() throws Exception {
        OperationRequest opRequest = new OperationRequest("account_1", MIN_VALUE, "account_2");
        String jsonRequest = mapper.writeValueAsString(opRequest);

        given(operationService.transfer(anyObject())).willThrow(new AmountMinMaxValueException("Bad Request ! amount's value is not valid"));

        mokMvc.perform(MockMvcRequestBuilders.post("/operations/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void Transfer_ShouldReturnAmountLowerThanBalance() throws Exception {
        OperationRequest opRequest = new OperationRequest("account_1", 5000, "account_2");
        String jsonRequest = mapper.writeValueAsString(opRequest);

        given(operationService.transfer(anyObject())).willThrow(new AmountMinMaxValueException("Bad Request ! amount's value is lower than balance"));

        mokMvc.perform(MockMvcRequestBuilders.post("/operations/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void TransferHistory_ShouldReturnAllTransferOperations() throws Exception {
        List<Transfer> opTransferList;
        opTransferList = new ArrayList<Transfer>();

        opTransferList.add(new Transfer());
        opTransferList.get(0).setAccount(account_1);
        opTransferList.get(0).setAmount(200);
        opTransferList.get(0).setToAccount(account_2);

        opTransferList.add(new Transfer());
        opTransferList.get(1).setAccount(account_2);
        opTransferList.get(1).setAmount(400);
        opTransferList.get(1).setToAccount(account_1);

        given(operationService.transferHistory(anyString())).willReturn(opTransferList);

        mokMvc.perform(MockMvcRequestBuilders.get("/operations/transferHistory/{accountCode}", "account_1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].account.accountCode").value("account_1"))
                .andExpect(jsonPath("$.[0].account.balance").value(2000))
                .andExpect(jsonPath("$.[0].amount").value(200))
                .andExpect(jsonPath("$.[0].toAccount.accountCode").value("account_2"))
                .andExpect(jsonPath("$.[0].toAccount.balance").value(3000))
                .andExpect(jsonPath("$.[1].account.accountCode").value("account_2"))
                .andExpect(jsonPath("$.[1].account.balance").value(3000))
                .andExpect(jsonPath("$.[1].amount").value(400))
                .andExpect(jsonPath("$.[1].toAccount.accountCode").value("account_1"))
                .andExpect(jsonPath("$.[1].toAccount.balance").value(2000));
    }

    @Test
    public void TransferHistory_ShouldReturnAccountNotFoundException() throws Exception {
        given(operationService.transferHistory(anyString())).willThrow(new AccountNotFoundException("Account Not Found"));

        mokMvc.perform(MockMvcRequestBuilders.get("/operations/transferHistory/{accountCode}", "account_1"))
                .andExpect(status().isNotFound());
    }

}
