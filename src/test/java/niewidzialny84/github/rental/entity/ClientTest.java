package niewidzialny84.github.rental.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTest {
    private final Long id = 1L;
    private final String firstName = "John";
    private final String lastName = "Connor";
    private final Client client = new Client(id,firstName,lastName);

    @Test
    public void getClientId() {
        assertEquals(id,client.getId());
    }

    @Test
    public void setClientId() {
        client.setId(5L);
        assertEquals(5L,client.getId());
    }

    @Test
    void getFirstName() {
        assertEquals(firstName, client.getFirstName());
    }

    @Test
    void setFirstName() {
        client.setFirstName("ABC");
        assertEquals("ABC", client.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals(lastName, client.getLastName());
    }

    @Test
    void setLastName() {
        client.setLastName("NoNo");
        assertEquals("NoNo", client.getLastName());
    }
}
