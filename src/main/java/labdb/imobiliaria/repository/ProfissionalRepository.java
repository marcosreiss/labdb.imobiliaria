package labdb.imobiliaria.repository;

import jakarta.persistence.EntityManager;
import labdb.imobiliaria.models.Profissional;
import java.util.List;

public class ProfissionalRepository {

    private final EntityManager manager;
    private DAOGenerico<Profissional> daoGenerico;

    public ProfissionalRepository(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Profissional buscarPorId(Integer id) {
        return daoGenerico.buscaPorId(Profissional.class, id);
    }

    public List<Profissional> buscarTodos() {
        return daoGenerico.buscarTodos(Profissional.class);
    }

    public Profissional criarOuAtualizar(Profissional profissional) {
        return daoGenerico.salvaOuAtualiza(profissional);
    }

    public void remover(Profissional profissional) {
        daoGenerico.remove(profissional);
    }
}
