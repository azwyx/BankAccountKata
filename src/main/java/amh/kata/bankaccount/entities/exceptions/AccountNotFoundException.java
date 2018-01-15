package amh.kata.bankaccount.entities.exceptions;

public class AccountNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public AccountNotFoundException(String msg) {
        // TODO Auto-generated constructor stub
        System.out.println(msg);
    }
}
