package amh.kata.bankaccount.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("D")
public class Deposit extends Operation {
}
