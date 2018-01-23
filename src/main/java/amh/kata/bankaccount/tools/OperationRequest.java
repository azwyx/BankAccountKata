package amh.kata.bankaccount.tools;

public class OperationRequest {
    private String accountCode;
    private double amount;
    private String toAccountCode;

    public OperationRequest() {
    }

    public OperationRequest(String accountCode, double amount) {
        this.accountCode = accountCode;
        this.amount = amount;
    }

    public OperationRequest(String accountCode, double amount, String toAccountCode) {
        this.accountCode = accountCode;
        this.amount = amount;
        this.toAccountCode = toAccountCode;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getToAccountCode() {
        return toAccountCode;
    }

    public void setToAccountCode(String toAccountCode) {
        this.toAccountCode = toAccountCode;
    }
}
