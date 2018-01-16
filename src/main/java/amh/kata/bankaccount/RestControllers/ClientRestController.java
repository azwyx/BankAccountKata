package amh.kata.bankaccount.RestControllers;

import amh.kata.bankaccount.Services.ClientService;
import amh.kata.bankaccount.entities.Client;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientRestController {

    @RequestMapping(value="/clients/saveClient",method= RequestMethod.POST)
    public Client saveClient(@RequestBody Client c) {
        return null;
    }
    @RequestMapping(value="/clients/list",method=RequestMethod.GET)
    public List<Client> listClient() {
        return null;
    }
}
