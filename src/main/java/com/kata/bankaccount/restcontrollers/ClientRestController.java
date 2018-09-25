package com.kata.bankaccount.restcontrollers;

import com.kata.bankaccount.services.IClientService;
import com.kata.bankaccount.entities.Client;
import com.kata.bankaccount.services.exceptions.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientRestController {

    @Autowired
    private IClientService clientService;

    @RequestMapping(value="/save",  method= RequestMethod.POST)
    public Client saveClient(@RequestBody Client c) {
        return clientService.saveClient(c);
    }

    @RequestMapping(value="/{idClient}", method=RequestMethod.GET)
    public Client getClient(@PathVariable Long idClient) throws ClientNotFoundException {
        return clientService.getClient(idClient);
    }

    @RequestMapping(value="/delete/{idClient}", method=RequestMethod.GET)
    public String deleteClient(@PathVariable Long idClient) throws ClientNotFoundException {
        clientService.deleteClient(idClient);
        return "Client by Id "+idClient+" is successfully deleted";
    }

    @RequestMapping(value="/all",method=RequestMethod.GET)
    public List<Client> allClients() {
        return clientService.listClient();
    }
}
