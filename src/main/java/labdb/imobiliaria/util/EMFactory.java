package labdb.imobiliaria.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EMFactory {

    private static final EntityManagerFactory factory =
            Persistence.createEntityManagerFactory("imobiliaria");


    public EntityManager getEntityManager() {
        return factory.createEntityManager();

    }

    public void close() {
        factory.close();
    }
}
