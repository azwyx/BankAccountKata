package amh.kata.bankaccount.entities;

import java.io.Serializable;
import java.util.Date;

public class Operation implements Serializable {

    private Long opId;
    private double amount;
    private Date dateOperation;
    private Account account;

    public Operation() {
        super();
    }

    public Long getOpId() {

        return opId;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
