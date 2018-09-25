package com.kata.bankaccount.restcontrollers;

import com.kata.bankaccount.services.IOperationService;
import com.kata.bankaccount.entities.Deposit;
import com.kata.bankaccount.entities.Transfer;
import com.kata.bankaccount.entities.Withdrawal;
import com.kata.bankaccount.tools.OperationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operations")
public class OperationRestController {

    @Autowired
    private IOperationService operationService;

    @RequestMapping(value="/deposit",method= RequestMethod.POST)
    public Deposit deposit(@RequestBody OperationRequest opRequest) {
        return operationService.deposit(opRequest);
    }

    @RequestMapping(value="/withdrawal",method= RequestMethod.POST)
    public Withdrawal withdrawal(@RequestBody OperationRequest opRequest) {
        return operationService.withdrawal(opRequest);
    }

    @RequestMapping(value="/transfer",method= RequestMethod.POST)
    public Transfer transfer(@RequestBody OperationRequest opRequest) {
        return operationService.transfer(opRequest);
    }

    @RequestMapping(value="/transfers/history/{accountCode}",method= RequestMethod.GET)
    public List<Transfer> transferHistory(@PathVariable String accountCode) {
        return operationService.transferHistory(accountCode);
    }
}
