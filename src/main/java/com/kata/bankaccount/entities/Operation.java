package com.kata.bankaccount.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;



@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType=DiscriminatorType.STRING, length=1)
@Data
public class Operation implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long opId;
    private double amount;
    @CreationTimestamp
    private Date dateOperation;
    @ManyToOne
    @JoinColumn(name="ACCOUNT_CODE")
    private Account account;
}
