package amh.kata.bankaccount.RestControllers;

import amh.kata.bankaccount.Services.OperationService;
import amh.kata.bankaccount.entities.Operation;
import amh.kata.bankaccount.entities.Transfer;
import amh.kata.bankaccount.entities.exceptions.AccountNotFoundException;
import amh.kata.bankaccount.tools.OperationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OperationRestController {

    @Autowired
    private OperationService operationService;

    @RequestMapping(value="/operations/deposit",method= RequestMethod.POST)
    public Operation deposit(@RequestBody OperationRequest opRequest) {
        return operationService.deposit(opRequest);
    }

    @RequestMapping(value="/operations/withdrawal",method= RequestMethod.POST)
    public Operation withdrawal(@RequestBody OperationRequest opRequest) {
        return operationService.withdrawal(opRequest);
    }

    @RequestMapping(value="/operations/transfer",method= RequestMethod.POST)
    public Operation transfer(@RequestBody OperationRequest opRequest) {
        return operationService.transfer(opRequest);
    }

    @RequestMapping(value="/operations/transferHistory/{accountCode}",method= RequestMethod.GET)
    public List<Transfer> transferHistory(@PathVariable String accountCode) {
        return operationService.transferHistory(accountCode);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void AccountNotFoundHandler(AccountNotFoundException e){

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private void AmountMinMaxValueHandler(AccountNotFoundException e){

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private void AmountLowerThanHandler(AccountNotFoundException e){

    }
}
