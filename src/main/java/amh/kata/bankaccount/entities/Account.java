package amh.kata.bankaccount.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Account implements Serializable{
    @Id
    @NonNull
    private String accountCode;
    @CreationTimestamp
    @NonNull
    private Date dateCreation;
    private double balance;
    @NonNull
    @ManyToOne
    @JoinColumn(name="CODE_CLI")
    private Client client;
    @Getter(onMethod = @__( @JsonIgnore ))
    @OneToMany(mappedBy="account")
    private Collection<Operation> operations;

    public Account(String accountCode, Date dateCreation, double balance, Client client) {
        this.accountCode = accountCode;
        this.dateCreation = dateCreation;
        this.balance = balance;
        this.client = client;
    }
}
