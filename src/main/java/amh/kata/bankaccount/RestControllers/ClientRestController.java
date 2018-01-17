package amh.kata.bankaccount.RestControllers;

import amh.kata.bankaccount.Services.ClientService;
import amh.kata.bankaccount.entities.Client;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientRestController {
    private ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value="/clients/saveClient",  method= RequestMethod.POST)
    public Client saveClient(@RequestBody Client c) {
        return clientService.saveClient(c);
    }

    @RequestMapping(value="/clients/{idClient}", method=RequestMethod.GET)
    public Client getListOperationsCompte(@PathVariable Long idClient) {
        return clientService.getClient(idClient);
    }

    @RequestMapping(value="/clients/list",method=RequestMethod.GET)
    public List<Client> listClient() {
        return clientService.listClient();
    }
}
