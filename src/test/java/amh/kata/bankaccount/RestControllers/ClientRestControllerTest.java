package amh.kata.bankaccount.RestControllers;

import amh.kata.bankaccount.Services.ClientService;
import amh.kata.bankaccount.entities.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientRestController.class)
public class ClientRestControllerTest {

    @Autowired
    private MockMvc mokMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void saveClient_ShouldCreateAndReturnClient() throws Exception {
        String json = mapper.writeValueAsString(new Client("Amine", "HARIRI", "azerty", "azertypass"));

        given(clientService.saveClient(anyObject())).willReturn(new Client("Amine", "HARIRI", "azerty", "azertypass"));

        mokMvc.perform(MockMvcRequestBuilders.post("/clients/saveClient")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstname").value("Amine"))
                .andExpect(jsonPath("lastname").value("HARIRI"))
                .andExpect(jsonPath("login").value("azerty"))
                .andExpect(jsonPath("password").value("azertypass"));
    }

    @Test
    public void getClient_ShouldReturnClient() throws Exception {

        given(clientService.getClient(anyLong())).willReturn(new Client("Amine", "HARIRI", "azerty", "azertypass"));

        mokMvc.perform(MockMvcRequestBuilders.get("/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstname").value("Amine"))
                .andExpect(jsonPath("lastname").value("HARIRI"))
                .andExpect(jsonPath("login").value("azerty"))
                .andExpect(jsonPath("password").value("azertypass"));
    }

    @Test
    public void getClientList_ShouldReturnClientList() throws Exception {
        String json = mapper.writeValueAsString(new Client("Amine", "HARIRI", "azerty", "azertypass"));

        List<Client> client_list = new ArrayList<Client>();
        client_list.add(new Client("Amine", "HARIRI", "azerty", "azertypass"));
        client_list.add(new Client("Reda", "Beggar", "iop123", "owertypass"));

        given(clientService.listClient()).willReturn(client_list);

        mokMvc.perform(MockMvcRequestBuilders.get("/clients/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

}
