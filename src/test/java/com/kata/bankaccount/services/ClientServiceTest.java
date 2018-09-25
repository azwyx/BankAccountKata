package com.kata.bankaccount.services;

import com.kata.bankaccount.dao.ClientRepository;
import com.kata.bankaccount.entities.Client;
import com.kata.bankaccount.services.exceptions.ClientNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void saveClient() {
        given(clientRepository.save(any(Client.class))).willReturn(new Client("Amine", "HARIRI", "azerty", "azertypass"));

        Client client = clientService.saveClient(new Client("Amine", "HARIRI", "azerty", "azertypass"));

        assertThat(client.getFirstname()).isEqualTo("Amine");
        assertThat(client.getLastname()).isEqualTo("HARIRI");
        assertThat(client.getLogin()).isEqualTo("azerty");
        assertThat(client.getPassword()).isEqualTo("azertypass");

    }

    @Test
    public void getClient() {
        given(clientRepository.findByIdClient(anyLong())).willReturn(new Client("Amine", "HARIRI", "azerty", "azertypass"));

        Client client = clientService.getClient(Integer.toUnsignedLong(1));

        assertThat(client.getFirstname()).isEqualTo("Amine");
        assertThat(client.getLastname()).isEqualTo("HARIRI");
        assertThat(client.getLogin()).isEqualTo("azerty");
        assertThat(client.getPassword()).isEqualTo("azertypass");
    }

    @Test(expected = ClientNotFoundException.class)
    public void getClient_NotFound() {
        given(clientRepository.findByIdClient(anyLong())).willReturn(null);

        Client client = clientService.getClient(Integer.toUnsignedLong(1));
    }

    @Test
    public void deleteClient() {
        doReturn(new Client()).when(clientRepository).findOne(anyLong());
        doNothing().when(clientRepository).delete(anyLong());

        clientService.deleteClient((long)1);

        verify(clientRepository, times(1)).findOne(anyLong());
        verify(clientRepository, times(1)).delete(anyLong());

    }

    @Test(expected = ClientNotFoundException.class)
    public void deleteClient_NotFound() {
        doReturn(null).when(clientRepository).findOne(anyLong());
        clientService.deleteClient((long)1);
    }

    @Test
    public void listClient() {
        List<Client> list_client = new ArrayList<Client>();
        list_client.add(new Client("Amine", "HARIRI", "azerty", "azertypass"));
        list_client.add(new Client("Reda", "BEGGAR", "qwerty", "qwertypass"));

        given(clientRepository.findAll()).willReturn(list_client);

        List<Client> rslt_list_clientClient = clientService.listClient();

        assertThat(rslt_list_clientClient).isNotEmpty();
        assertThat(rslt_list_clientClient.size()).isEqualTo(2);
        assertThat(rslt_list_clientClient.get(0).getLastname()).isEqualTo("HARIRI");
        assertThat(rslt_list_clientClient.get(1).getLastname()).isEqualTo("BEGGAR");
    }
}