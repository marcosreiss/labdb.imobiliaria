package labdb.imobiliaria.repository;

import jakarta.persistence.EntityManager;
import labdb.imobiliaria.models.TipoImovel;
import java.util.List;

public class TipoImovelRepository {

    private final EntityManager manager;
    private DAOGenerico<TipoImovel> daoGenerico;

    public TipoImovelRepository(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public TipoImovel buscarPorId(Integer id) {
        return daoGenerico.buscaPorId(TipoImovel.class, id);
    }

    public List<TipoImovel> buscarTodos() {
        return daoGenerico.buscarTodos(TipoImovel.class);
    }

    public TipoImovel criarOuAtualizar(TipoImovel tipoImovel) {
        return daoGenerico.salvaOuAtualiza(tipoImovel);
    }

    public void remover(TipoImovel tipoImovel) {
        daoGenerico.remove(tipoImovel);
    }
}
