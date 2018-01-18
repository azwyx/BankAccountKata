package amh.kata.bankaccount.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.util.Collection;

public class Client implements Serializable{
    private Long idClient;
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    private Collection<Account> accounts;

    public Client() {
    }

    public Client(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Client(String firstname, String lastname, String login, String password) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
    }

    public Long getIdClient() {
        return idClient;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public Collection<Account> getAccounts() {
        return accounts;
    }

    @JsonSetter
    public void setAccounts(Collection<Account> accounts) {
        this.accounts = accounts;
    }
}
