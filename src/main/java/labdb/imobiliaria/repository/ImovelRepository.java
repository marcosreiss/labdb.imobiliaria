package labdb.imobiliaria.repository;

import jakarta.persistence.EntityManager;
import labdb.imobiliaria.models.Imovel;
import java.util.List;

public class ImovelRepository {

    private final EntityManager manager;
    private DAOGenerico<Imovel> daoGenerico;

    public ImovelRepository(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Imovel buscarPorId(Integer id) {
        return daoGenerico.buscaPorId(Imovel.class, id);
    }

    public List<Imovel> buscarTodos() {
        return daoGenerico.buscarTodos(Imovel.class);
    }

    public Imovel criarOuAtualizar(Imovel imovel) {
        return daoGenerico.salvaOuAtualiza(imovel);
    }

    public void remover(Imovel imovel) {
        daoGenerico.remove(imovel);
    }
}
