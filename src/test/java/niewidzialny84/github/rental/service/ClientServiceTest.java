package niewidzialny84.github.rental.service;

import niewidzialny84.github.rental.entity.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.persistence.NoResultException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest extends ServiceAbstractTest{
    private final ClientService service = new ClientService(entityManager);

    @Test
    public void getAll() {
        var ref = new Object() {
            List<Client> l;
        };
        assertDoesNotThrow(()-> ref.l = service.getAllClients());
        assertFalse(ref.l.isEmpty());
    }

    @Test
    public void getFromId() {
        var client = service.getClientById(1L);
        assertNotNull(client);
        assertNotNull(client.getId());
        assertNotNull(client.getFirstName());
        assertNotNull(client.getLastName());
    }

    @Test
    public void getFromIdFail() {
        var client = service.getClientById(-1L);
        assertNull(client);
    }

    @Test
    public void getFromName() {
        saveNew();

        var client = service.getClientByName("John","Connor");
        assertNotNull(client);
        assertNotNull(client.getId());
        assertNotNull(client.getFirstName());
        assertNotNull(client.getLastName());
    }

    @Test
    public void getFromNameFail() {
        assertThrows(NoResultException.class,() -> service.getClientByName("x","y"));
    }

    @Test
    public void saveNew() {
        var newClient = new Client("John","Connor");
        var ret = service.saveClient(newClient);
        assertNotNull(ret);
        assertNotNull(ret.getId());
        assertNotNull(ret.getFirstName());
        assertNotNull(ret.getLastName());
        assertEquals(newClient,ret);
    }

    @ParameterizedTest
    @CsvSource({"John,Connor","Adam,Newman","Luis,Deck","Joe,Doe","Summer,Smith"})
    public void saveModified(String first,String last) {
        var client = service.getClientById(1L);
        assertNotNull(client);
        assertNotNull(client.getId());
        assertNotNull(client.getFirstName());
        assertNotNull(client.getLastName());

        var id = client.getId();
        client.setFirstName(first);
        client.setLastName(last);
        var ret = service.saveClient(client);
        assertNotNull(ret);
        assertNotNull(ret.getId());
        assertNotNull(ret.getFirstName());
        assertNotNull(ret.getLastName());
        assertEquals(client,ret);
        assertEquals(id,ret.getId());
        assertEquals(first,ret.getFirstName());
        assertEquals(last,ret.getLastName());
    }

    @Test
    public void deleteCar() {
        var newClient = new Client("John","Connor");
        var c = service.saveClient(newClient);

        assertNotNull(c);
        assertNotNull(newClient);
        assertEquals(newClient,c);
        assertEquals(newClient.getId(),c.getId());

        assertDoesNotThrow(() -> service.deleteClient(c));
        var client = service.getClientById(newClient.getId());
        assertNull(client);
        assertEquals(newClient,c);
    }
}
