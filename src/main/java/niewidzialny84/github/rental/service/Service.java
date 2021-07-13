package niewidzialny84.github.rental.service;

import javax.persistence.EntityManager;

public abstract class Service {
    protected final EntityManager em;

    public Service(EntityManager em) {
        this.em = em;
    }
}
