package niewidzialny84.github.rental.repository;

import niewidzialny84.github.rental.entity.Client;

import java.util.List;

public interface ClientRepository {
    List<Client> getAllClients();

    Client getClientById(Long id);

    Client getClientByName(String firstName, String lastName);

    Client saveClient(Client client);

    void deleteClientById(Long id);

    void deleteClient(Client client);
}
