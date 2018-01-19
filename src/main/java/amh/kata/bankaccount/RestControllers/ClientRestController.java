package amh.kata.bankaccount.RestControllers;

import amh.kata.bankaccount.Services.IClientService;
import amh.kata.bankaccount.dao.ClientRepository;
import amh.kata.bankaccount.entities.Client;
import amh.kata.bankaccount.entities.exceptions.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientRestController {

    @Autowired
    private IClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(value="/clients/saveClient",  method= RequestMethod.POST)
    public Client saveClient(@RequestBody Client c) {
        return clientService.saveClient(c);
    }

    @RequestMapping(value="/clients/{idClient}", method=RequestMethod.GET)
    public Client getClient(@PathVariable Long idClient) throws ClientNotFoundException {
        return clientService.getClient(idClient);
    }

    @RequestMapping(value="/clients/delete/{idClient}", method=RequestMethod.GET)
    public String deleteClient(@PathVariable Long idClient) throws ClientNotFoundException {
        clientService.deleteClient(idClient);
        return "Client by Id "+idClient+" is successfully deleted";
    }

    @RequestMapping(value="/clients/list",method=RequestMethod.GET)
    public List<Client> listClient() {
        return clientService.listClient();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void ClientNotFoundHandler(ClientNotFoundException e){

    }
}
