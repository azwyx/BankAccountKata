package com.kata.bankaccount.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("T")
@Data
public class Transfer extends Operation {
    @ManyToOne
    @JoinColumn(name="TO_ACCOUNT_CODE")
    private Account toAccount;

}
