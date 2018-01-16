package amh.kata.bankaccount.RestControllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientRestController.class)
public class ClientRestControllerTest {

    @Autowired
    private MockMvc mokMvc;

    @Test
    public void saveClient_ShouldReturnClient() throws Exception {

        mokMvc.perform(MockMvcRequestBuilders.post("/clients/saveClient")
        .contentType(MediaType.APPLICATION_JSON)
        .param("firstname", "Amine")
        .param("lastname", "HARIRI")
        .param("login", "azerty")
        .param("password", "azertypass"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firtname").value("Amine"))
                .andExpect(jsonPath("firtname").value("HARIRI"))
                .andExpect(jsonPath("firtname").value("azerty"))
                .andExpect(jsonPath("firtname").value("Amine"));
    }

}
