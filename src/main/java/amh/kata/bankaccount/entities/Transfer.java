package amh.kata.bankaccount.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("T")
@Setter
@Getter
public class Transfer extends Operation {
    @ManyToOne
    @JoinColumn(name="TO_ACCOUNT_CODE")
    private Account toAccount;

}
