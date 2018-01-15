package amh.kata.bankaccount.entities.exceptions;

public class AmountMinMaxValueException extends Exception {
    private static final long serialVersionUID = 1L;

    public AmountMinMaxValueException(String msg) {
        System.out.println(msg);
    }
}
