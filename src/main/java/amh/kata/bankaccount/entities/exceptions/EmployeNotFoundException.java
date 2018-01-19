package amh.kata.bankaccount.entities.exceptions;

public class EmployeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmployeNotFoundException(String msg) { System.out.println(msg); }
}
