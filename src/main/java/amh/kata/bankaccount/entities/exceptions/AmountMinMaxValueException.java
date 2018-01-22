package amh.kata.bankaccount.entities.exceptions;

public class AmountMinMaxValueException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AmountMinMaxValueException(String msg) {
        System.out.println(msg);
    }
}
