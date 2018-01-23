package amh.kata.bankaccount.entities.exceptions;

public class AmountLowerThanBalanceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AmountLowerThanBalanceException(String msg) {
        // TODO Auto-generated constructor stub
        System.out.println(msg);
    }
}
