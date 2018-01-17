package amh.kata.bankaccount.RestControllers;

import amh.kata.bankaccount.Services.ClientService;
import amh.kata.bankaccount.Services.ClientServiceImpl;
import amh.kata.bankaccount.entities.Client;
import amh.kata.bankaccount.entities.exceptions.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @RequestMapping(value="/clients/saveClient",  method= RequestMethod.POST)
    public Client saveClient(@RequestBody Client c) {
        return clientService.saveClient(c);
    }

    @RequestMapping(value="/clients/{idClient}", method=RequestMethod.GET)
    public Client getClient(@PathVariable Long idClient) throws ClientNotFoundException {
        return clientService.getClient(idClient);
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
