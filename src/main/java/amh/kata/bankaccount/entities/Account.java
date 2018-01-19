package amh.kata.bankaccount.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Collection;
import java.util.Date;

public class Account {

    private String accountCode;
    private Date dateCreation;
    private double balance;
    private Client client;
    private Collection<Operation> operations;

    public Account() {
    }

    public Account(String accountCode) {
        this.accountCode = accountCode;
    }

    public Account(String accountCode, Date dateCreation, Client client) {
        this.accountCode = accountCode;
        dateCreation = dateCreation;
        this.client = client;
    }

    public Account(String accountCode, Date dateCreation, double balance, Client client, Collection<Operation> operations) {
        this.accountCode = accountCode;
        dateCreation = dateCreation;
        this.balance = balance;
        this.client = client;
        this.operations = operations;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        dateCreation = dateCreation;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @JsonIgnore
    public Collection<Operation> getOperations() {
        return operations;
    }

    @JsonSetter
    public void setOperations(Collection<Operation> operations) {
        this.operations = operations;
    }
}
