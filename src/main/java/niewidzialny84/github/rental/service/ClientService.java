package niewidzialny84.github.rental.service;

import niewidzialny84.github.rental.entity.Client;
import niewidzialny84.github.rental.repository.ClientRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClientService extends Service implements ClientRepository {

    public ClientService(EntityManager em) {
        super(em);
    }

    @Override
    public List<Client> getAllClients() {
        return em.createQuery("SELECT x FROM Client x", Client.class).getResultList();
    }

    @Override
    public Client getClientById(Long id) {
        return em.find(Client.class,id);
    }

    @Override
    public Client getClientByName(String firstName, String lastName) throws NoResultException {
        TypedQuery<Client> q = em.createQuery("SELECT x FROM Client x WHERE x.firstName=:firstName and x.lastName=:lastName", Client.class);
        q.setParameter("firstName",firstName);
        q.setParameter("lastName",lastName);
        return q.getSingleResult();
    }

    @Override
    public Client saveClient(Client client) {
        if (client.getId() == null) {
            em.persist(client);
        } else {
            client = em.merge(client);
        }
        return client;
    }

    @Override
    public void deleteClient(Client client) {
        if (em.contains(client)) {
            Query q = em.createQuery("DELETE FROM RentedCar x WHERE x.client.id=:id");
            q.setParameter("id",client.getId());
            q.executeUpdate();
            em.remove(client);
        } else {
            em.merge(client);
        }
    }
}
