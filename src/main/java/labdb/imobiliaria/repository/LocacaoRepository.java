package labdb.imobiliaria.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import labdb.imobiliaria.models.Locacao;
import labdb.imobiliaria.models.Imovel;
import labdb.imobiliaria.models.Cliente;
import java.util.List;

public class LocacaoRepository {

    private final EntityManager manager;
    private DAOGenerico<Locacao> daoGenerico;

    public LocacaoRepository(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Locacao buscarPorId(Integer id) {
        return daoGenerico.buscaPorId(Locacao.class, id);
    }

    public List<Locacao> buscarTodasPorCliente(Cliente cliente) {
        TypedQuery<Locacao> query = manager.createQuery(
                "SELECT l FROM Locacao l WHERE l.inquilino = :cliente", Locacao.class);
        query.setParameter("cliente", cliente);
        return query.getResultList();
    }

    public Locacao criarOuAtualizar(Locacao locacao) {
        if (!imovelDisponivel(locacao.getImovel())) {
            throw new IllegalArgumentException("O imóvel não está disponível para locação.");
        }
        return daoGenerico.salvaOuAtualiza(locacao);
    }

    public void remover(Locacao locacao) {
        daoGenerico.remove(locacao);
    }

    private boolean imovelDisponivel(Imovel imovel) {
        TypedQuery<Long> query = manager.createQuery(
                "SELECT COUNT(l) FROM Locacao l WHERE l.imovel = :imovel AND l.ativo = true", Long.class);
        query.setParameter("imovel", imovel);
        return query.getSingleResult() == 0;
    }
}
