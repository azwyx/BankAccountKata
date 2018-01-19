package amh.kata.bankaccount.entities.exceptions;

public class AccountAlreadyExistException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public AccountAlreadyExistException(String msg) {
        System.out.println(msg);
    }
}
