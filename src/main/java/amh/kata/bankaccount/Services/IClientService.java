package amh.kata.bankaccount.Services;

import amh.kata.bankaccount.entities.Client;

import java.util.List;

public interface IClientService {
    public Client saveClient(Client c);
    public Client getClient(Long idClient);
    public List<Client> listClient();
}
