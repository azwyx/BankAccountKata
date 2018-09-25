package com.kata.bankaccount.services;

import com.kata.bankaccount.entities.Client;

import java.util.List;

public interface IClientService {
    // methode pour ajouter ou mettre à jour un client
    public Client saveClient(Client c);
    // methode pour récupérer les données d'un client
    public Client getClient(Long idClient);
    // methode pour récupérer tous les clients
    public List<Client> listClient();
    // methode pour supprimer un client
    public void deleteClient(Long idClient);
}
