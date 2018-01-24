package amh.kata.bankaccount.tools;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class OperationRequest {
    @NonNull
    private String accountCode;
    @NonNull
    private double amount;
    private String toAccountCode;

    public OperationRequest(String accountCode, double amount, String toAccountCode) {
        this.accountCode = accountCode;
        this.amount = amount;
        this.toAccountCode = toAccountCode;
    }
}
