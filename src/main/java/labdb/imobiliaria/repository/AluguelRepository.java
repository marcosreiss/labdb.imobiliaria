package labdb.imobiliaria.repository;

import jakarta.persistence.EntityManager;
import labdb.imobiliaria.models.Aluguel;
import java.util.List;

public class AluguelRepository {

    private final EntityManager manager;
    private DAOGenerico<Aluguel> daoGenerico;

    public AluguelRepository(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Aluguel buscarPorId(Integer id) {
        return daoGenerico.buscaPorId(Aluguel.class, id);
    }

    public List<Aluguel> buscarTodos() {
        return daoGenerico.buscarTodos(Aluguel.class);
    }

    public Aluguel criarOuAtualizar(Aluguel aluguel) {
        return daoGenerico.salvaOuAtualiza(aluguel);
    }

    public void remover(Aluguel aluguel) {
        daoGenerico.remove(aluguel);
    }
}
