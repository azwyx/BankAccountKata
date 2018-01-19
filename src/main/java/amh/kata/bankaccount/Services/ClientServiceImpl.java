package amh.kata.bankaccount.Services;

import amh.kata.bankaccount.dao.ClientRepository;
import amh.kata.bankaccount.entities.Client;
import amh.kata.bankaccount.entities.exceptions.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client saveClient(Client c) {
        return clientRepository.save(c);
    }

    @Override
    public Client getClient(Long idClient) {
        Client client = clientRepository.finByIdClient(idClient);
        if(client == null) throw new ClientNotFoundException("Client Not Found");
        return client;
    }

    @Override
    public List<Client> listClient() {
        return clientRepository.findAll();
    }

    @Override
    public void deleteClient(Long idClient) {
        if(clientRepository.findOne(idClient) == null)
            throw new ClientNotFoundException("Client Not Found");

        clientRepository.delete(idClient);
    }
}
