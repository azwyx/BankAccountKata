package amh.kata.bankaccount.entities.exceptions;

public class ClientNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ClientNotFoundException(String msg) {
        System.out.println(msg);
    }
}
