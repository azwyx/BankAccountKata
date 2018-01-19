package amh.kata.bankaccount.entities.exceptions;

public class AccountNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public AccountNotFoundException(String msg) {
        System.out.println(msg);
    }
}
