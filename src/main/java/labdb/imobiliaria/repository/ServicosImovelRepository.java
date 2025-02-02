package labdb.imobiliaria.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import labdb.imobiliaria.models.ServicosImovel;
import labdb.imobiliaria.models.Imovel;
import java.util.List;

public class ServicosImovelRepository {

    private final EntityManager manager;
    private DAOGenerico<ServicosImovel> daoGenerico;

    public ServicosImovelRepository(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public ServicosImovel buscarPorId(Integer id) {
        return daoGenerico.buscaPorId(ServicosImovel.class, id);
    }

    public List<ServicosImovel> buscarTodos() {
        return daoGenerico.buscarTodos(ServicosImovel.class);
    }

    public List<ServicosImovel> buscarPorImovel(Imovel imovel) {
        TypedQuery<ServicosImovel> query = manager.createQuery(
                "SELECT s FROM ServicosImovel s WHERE s.imovel = :imovel", ServicosImovel.class);
        query.setParameter("imovel", imovel);
        return query.getResultList();
    }

    public ServicosImovel criarOuAtualizar(ServicosImovel servico) {
        return daoGenerico.salvaOuAtualiza(servico);
    }

    public void remover(ServicosImovel servico) {
        daoGenerico.remove(servico);
    }
}
