package labdb.imobiliaria.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import labdb.imobiliaria.models.Aluguel;
import labdb.imobiliaria.models.Cliente;
import labdb.imobiliaria.models.Imovel;
import java.util.Date;
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

    public List<Aluguel> buscarPorInquilino(Cliente inquilino) {
        TypedQuery<Aluguel> query = manager.createQuery(
                "SELECT a FROM Aluguel a WHERE a.locacao.inquilino = :inquilino", Aluguel.class);
        query.setParameter("inquilino", inquilino);
        return query.getResultList();
    }

    public List<Aluguel> buscarPorNomeInquilino(String nome) {
        TypedQuery<Aluguel> query = manager.createQuery(
                "SELECT a FROM Aluguel a WHERE UPPER(a.locacao.inquilino.nome) LIKE :nome", Aluguel.class);
        query.setParameter("nome", nome.toUpperCase() + "%");
        return query.getResultList();
    }

    public List<Imovel> buscarImoveisDisponiveisPorPreco(double valorMaximo) {
        TypedQuery<Imovel> query = manager.createQuery(
                "SELECT i FROM Imovel i WHERE i.valorAluguelSugerido <= :valorMaximo " +
                        "AND i.id NOT IN (SELECT l.imovel.id FROM Locacao l WHERE l.ativo = true)", Imovel.class);
        query.setParameter("valorMaximo", valorMaximo);
        return query.getResultList();
    }

    public List<Aluguel> buscarAlugueisAtrasados() {
        TypedQuery<Aluguel> query = manager.createQuery(
                "SELECT a FROM Aluguel a WHERE a.dataPagamento IS NULL AND a.dataVencimento < :hoje", Aluguel.class);
        query.setParameter("hoje", new Date());
        return query.getResultList();
    }

    public Aluguel criarOuAtualizar(Aluguel aluguel) {
        return daoGenerico.salvaOuAtualiza(aluguel);
    }

    public void remover(Aluguel aluguel) {
        daoGenerico.remove(aluguel);
    }
}
