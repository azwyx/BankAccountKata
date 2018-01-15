package amh.kata.bankaccount.entities.exceptions;

public class AmountLowerThanBalance extends Exception {
    private static final long serialVersionUID = 1L;

    public AmountLowerThanBalance(String msg) {
        // TODO Auto-generated constructor stub
        System.out.println(msg);
    }
}
