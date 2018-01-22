package amh.kata.bankaccount.entities;

public class Transfer extends Operation {
    private Account toAccount;
    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

}
