package com.kata.bankaccount.services;

import com.kata.bankaccount.dao.ClientRepository;
import com.kata.bankaccount.entities.Client;
import com.kata.bankaccount.entities.exceptions.ClientNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client saveClient(Client c) {
        return clientRepository.save(c);
    }

    @Override
    public Client getClient(Long idClient) {
        Client client = clientRepository.findByIdClient(idClient);
        if(client == null){
            log.error("Client Not Found");
            throw new ClientNotFoundException();
        }
        return client;
    }

    @Override
    public List<Client> listClient() {
        return clientRepository.findAll();
    }

    @Override
    public void deleteClient(Long idClient) throws ClientNotFoundException{
        if(clientRepository.findOne(idClient) == null){
            log.error("Client Not Found");
            throw new ClientNotFoundException();
        }
        clientRepository.delete(idClient);
    }
}
