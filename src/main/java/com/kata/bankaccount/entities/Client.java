package com.kata.bankaccount.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Client implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idClient;
    @NonNull
    private String firstname;
    @NonNull
    private String lastname;
    private String login;
    private String password;
    @Getter(onMethod = @__( @JsonIgnore ))
    @OneToMany(mappedBy="client", fetch=FetchType.LAZY)
    private Collection<Account> accounts;

    public Client(String firstname, String lastname, String login, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
    }
}
