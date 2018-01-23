package amh.kata.bankaccount.Services;

import amh.kata.bankaccount.dao.OperationRepository;
import amh.kata.bankaccount.entities.*;
import amh.kata.bankaccount.entities.exceptions.AccountNotFoundException;
import amh.kata.bankaccount.entities.exceptions.AmountLowerThanBalanceException;
import amh.kata.bankaccount.entities.exceptions.AmountMinMaxValueException;
import amh.kata.bankaccount.tools.OperationRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class OperationServiceTest {
    @Mock
    private OperationRepository operationRepository;

    @Mock
    private AccountServiceImpl accountService;

    @InjectMocks
    private OperationServiceImpl operationService;

    private static final Logger logger = Logger.getLogger("MyLog");
    private static final double MIN_VALUE = 0;
    private static final double MAX_VALUE = 999999999;
    private static final String TRANSFER_TYPE = "T";

    private Client client;
    private Account account_1;
    private Account account_2;
    private Operation opDeposit;
    private Operation opWithdrawal;
    private Transfer opTransfer;

    @Before
    public void setUp() throws Exception {
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
    public void deposit_ShouldReturnCreatedOperation(){
        OperationRequest opRequest = new OperationRequest("account_1", 800);

        given(accountService.getAccount(anyString())).willReturn(account_1);
        given(operationRepository.save(any(Operation.class))).willReturn(opDeposit);

        //logger.info("opDeposit avant ==>  " + opDeposit.getAmount() + " ** "+opDeposit.getAccount().getAccountCode() + " ** " + opDeposit.getAccount().getBalance());
        Operation op_result = operationService.deposit(opRequest);
        //logger.info("opDeposit apres ==>  " + opDeposit.getAmount() + " ** "+opDeposit.getAccount().getAccountCode() + " ** " + opDeposit.getAccount().getBalance());

        assertThat(op_result.getAmount()).isEqualTo(800);
        assertThat(op_result.getAccount().getBalance()).isEqualTo(2800);
        assertThat(op_result.getAccount().getAccountCode()).isEqualTo("account_1");
    }

    @Test(expected = AccountNotFoundException.class)
    public void deposit_ShoulReturnAccountNotFoundException(){
        OperationRequest opRequest = new OperationRequest("account_1", 800);

        given(accountService.getAccount(anyString())).willThrow(new AccountNotFoundException("Account Not Found"));
        //given(operationRepository.save(any(Operation.class))).willReturn(opDeposit);
        Operation op_result = operationService.deposit(opRequest);
    }

    @Test(expected = AmountMinMaxValueException.class)
    public void deposit_ShoulReturnAmountMinMaxValueException(){
        OperationRequest opRequest = new OperationRequest("account_1", MIN_VALUE);

        given(accountService.getAccount(anyString())).willReturn(account_1);

        operationService.deposit(opRequest);
    }

    @Test
    public void withdrawal_ShouldReturnCreatedOperation(){
        OperationRequest opRequest = new OperationRequest("account_2", 700);

        given(accountService.getAccount(anyString())).willReturn(account_2);
        given(operationRepository.save(any(Operation.class))).willReturn(opWithdrawal);

        //logger.info("opDeposit avant ==>  " + opDeposit.getAmount() + " ** "+opDeposit.getAccount().getAccountCode() + " ** " + opDeposit.getAccount().getBalance());
        Operation op_result = operationService.withdrawal(opRequest);
        //logger.info("opDeposit apres ==>  " + opDeposit.getAmount() + " ** "+opDeposit.getAccount().getAccountCode() + " ** " + opDeposit.getAccount().getBalance());

        assertThat(op_result.getAmount()).isEqualTo(700);
        assertThat(op_result.getAccount().getBalance()).isEqualTo(2300);
        assertThat(op_result.getAccount().getAccountCode()).isEqualTo("account_2");
    }

    @Test(expected = AccountNotFoundException.class)
    public void withdrawal_ShoulReturnAccountNotFoundException(){
        OperationRequest opRequest = new OperationRequest("account_3", 650);

        given(accountService.getAccount(anyString())).willThrow(new AccountNotFoundException("Account Not Found"));

        Operation op_result = operationService.withdrawal(opRequest);
    }

    @Test(expected = AmountMinMaxValueException.class)
    public void withrawal_ShoulReturnAmountMinMaxValueException(){
        OperationRequest opRequest = new OperationRequest("account_2", MAX_VALUE);

        given(accountService.getAccount(anyString())).willReturn(account_2);

        Operation op_result = operationService.withdrawal(opRequest);
    }

    @Test(expected = AmountLowerThanBalanceException.class)
    public void withrawal_ShoulReturnAmountLowerThanBalance(){
        OperationRequest opRequest = new OperationRequest("account_2", 3500);

        given(accountService.getAccount(anyString())).willReturn(account_2);

        Operation op_result = operationService.withdrawal(opRequest);
    }

    @Test
    public void Transfer_ShouldReturnCreatedOperation(){
        OperationRequest opRequest = new OperationRequest("account_1", 500, "account_2");

        given(accountService.getAccount("account_1")).willReturn(account_1);
        given(accountService.getAccount("account_2")).willReturn(account_2);
        given(operationRepository.save(any(Operation.class))).willReturn(opTransfer);

        //logger.info("opDeposit avant ==>  " + opDeposit.getAmount() + " ** "+opDeposit.getAccount().getAccountCode() + " ** " + opDeposit.getAccount().getBalance());
        Transfer op_result = operationService.transfer(opRequest);
        //logger.info("opDeposit apres ==>  " + opDeposit.getAmount() + " ** "+opDeposit.getAccount().getAccountCode() + " ** " + opDeposit.getAccount().getBalance());

        assertThat(op_result.getAmount()).isEqualTo(500);
        assertThat(op_result.getAccount().getBalance()).isEqualTo(1500);
        assertThat(op_result.getAccount().getAccountCode()).isEqualTo("account_1");
        assertThat(op_result.getToAccount().getBalance()).isEqualTo(3500);
        assertThat(op_result.getToAccount().getAccountCode()).isEqualTo("account_2");
    }

    @Test(expected = AccountNotFoundException.class)
    public void Transfer_ShouldReturnAccountNotFoundException(){
        OperationRequest opRequest = new OperationRequest("account_1", 500, "account_7");

        given(accountService.getAccount("account_1")).willReturn(account_1);
        given(accountService.getAccount("account_7")).willThrow(new AccountNotFoundException("Account Not Found"));

        Transfer op_result = operationService.transfer(opRequest);
    }

    @Test(expected = AmountMinMaxValueException.class)
    public void Transfer_ShouldReturnAmountMinMaxValueException(){
        OperationRequest opRequest = new OperationRequest("account_1", MAX_VALUE, "account_2");

        given(accountService.getAccount("account_1")).willReturn(account_1);
        given(accountService.getAccount("account_2")).willReturn(account_1);

        Transfer op_result = operationService.transfer(opRequest);
    }

    @Test(expected = AmountLowerThanBalanceException.class)
    public void Transfer_ShouldReturnAmountLowerThanBalance(){
        OperationRequest opRequest = new OperationRequest("account_1", 2500, "account_2");

        given(accountService.getAccount("account_1")).willReturn(account_1);
        given(accountService.getAccount("account_2")).willReturn(account_1);

        Transfer op_result = operationService.transfer(opRequest);
    }

    @Test
    public void TransferHistory_ShouldReturnAllTransferOperations(){
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

        given(accountService.getAccount("account_1")).willReturn(account_1);
        given(operationRepository.findByTypeAndAccount(TRANSFER_TYPE, "account_1")).willReturn(opTransferList);

        List<Transfer> list_op_result = operationService.transferHistory("account_1");

        assertThat(list_op_result.size()).isEqualTo(2);
        assertThat(list_op_result.get(0).getAccount().getAccountCode()).isEqualTo("account_1");
        assertThat(list_op_result.get(0).getAccount().getBalance()).isEqualTo(2000);
        assertThat(list_op_result.get(0).getAmount()).isEqualTo(200);
        assertThat(list_op_result.get(0).getToAccount().getAccountCode()).isEqualTo("account_2");
        assertThat(list_op_result.get(0).getToAccount().getBalance()).isEqualTo(3000);
        assertThat(list_op_result.get(1).getAccount().getAccountCode()).isEqualTo("account_2");
        assertThat(list_op_result.get(1).getAccount().getBalance()).isEqualTo(3000);
        assertThat(list_op_result.get(1).getAmount()).isEqualTo(400);
        assertThat(list_op_result.get(1).getToAccount().getAccountCode()).isEqualTo("account_1");
        assertThat(list_op_result.get(1).getToAccount().getBalance()).isEqualTo(2000);
    }

    @Test(expected = AccountNotFoundException.class)
    public void TransferHistory_ShouldReturnAccountNotFoundException(){
        given(accountService.getAccount("account_8")).willThrow(new AccountNotFoundException("Account Not Found"));
        List<Transfer> list_op_result = operationService.transferHistory("account_8");
    }
}
