package amh.kata.bankaccount.Services;

import amh.kata.bankaccount.entities.Client;
import amh.kata.bankaccount.entities.exceptions.ClientNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{


    @Override
    public Client saveClient(Client c) {
        return null;
    }

    @Override
    public Client getClient(Long idClient) {
        return null;
    }

    @Override
    public List<Client> listClient() {
        return null;
    }
}
