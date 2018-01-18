package amh.kata.bankaccount.RestControllers;

import amh.kata.bankaccount.Services.IClientService;
import amh.kata.bankaccount.entities.Client;
import amh.kata.bankaccount.entities.exceptions.ClientNotFoundException;
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
    private IClientService IClientService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void saveClient_ShouldCreateAndReturnClient() throws Exception {
        String json = mapper.writeValueAsString(new Client("Amine", "HARIRI", "azerty", "azertypass"));

        given(IClientService.saveClient(anyObject())).willReturn(new Client("Amine", "HARIRI", "azerty", "azertypass"));

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
    public void getClientDetails_TestSuccess_ShouldReturnClient_() throws Exception {

        Client client = new Client("Amine", "HARIRI", "azerty", "azertypass");

        given(IClientService.getClient(anyLong())).willReturn(client);

        mokMvc.perform(MockMvcRequestBuilders.get("/clients/{idClient}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstname").value("Amine"))
                .andExpect(jsonPath("lastname").value("HARIRI"))
                .andExpect(jsonPath("login").value("azerty"))
                .andExpect(jsonPath("password").value("azertypass"));
    }

    @Test
    public void getClientDetails_TestFail_ShouldReturn_404_Not_Fount_() throws Exception {

        given(IClientService.getClient(anyLong())).willThrow(new ClientNotFoundException("mon_msg"));

        mokMvc.perform(MockMvcRequestBuilders.get("/clients/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getClientList_ShouldReturnClientList() throws Exception {

        List<Client> client_list = new ArrayList<Client>();
        client_list.add(new Client("Amine", "HARIRI", "azerty", "azertypass"));
        client_list.add(new Client("Reda", "Beggar", "iop123", "owertypass"));

        given(IClientService.listClient()).willReturn(client_list);

        mokMvc.perform(MockMvcRequestBuilders.get("/clients/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

}
