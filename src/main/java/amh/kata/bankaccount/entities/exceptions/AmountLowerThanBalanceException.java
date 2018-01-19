package amh.kata.bankaccount.entities.exceptions;

public class AmountLowerThanBalanceException extends Exception {
    private static final long serialVersionUID = 1L;

    public AmountLowerThanBalanceException(String msg) {
        // TODO Auto-generated constructor stub
        System.out.println(msg);
    }
}
