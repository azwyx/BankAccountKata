package com.kata.bankaccount.services;

import com.kata.bankaccount.entities.Client;

import java.util.List;

public interface IClientService {
    public Client saveClient(Client c);
    public Client getClient(Long idClient);
    public List<Client> listClient();
    void deleteClient(Long idClient);
}
