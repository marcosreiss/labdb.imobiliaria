package labdb.imobiliaria.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import labdb.imobiliaria.models.EntidadeBase;

import java.util.List;
import java.util.Objects;

class DAOGenerico<T extends EntidadeBase> {

    private final EntityManager manager;

    DAOGenerico(EntityManager manager) {
        this.manager = manager;
    }

    List<T> buscarTodos(Class<T> clazz) {
        TypedQuery<T> query = manager.createQuery("SELECT t FROM " + clazz.getSimpleName() + " t", clazz);
        return query.getResultList();
    }

    T buscaPorId(Class<T> clazz, Integer id) {
        return manager.find(clazz, id);

    }

    T salvaOuAtualiza(T t) {
        // log, processamento adicional
        if( Objects.isNull(t.getId()) ){
            this.manager.persist(t);
        }
        else
            t = this.manager.merge(t);

        return t;


    }

    void remove(T t) {
        manager.remove(t);
        manager.flush();
    }
}
