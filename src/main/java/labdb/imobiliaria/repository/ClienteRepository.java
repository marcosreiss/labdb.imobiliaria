package labdb.imobiliaria.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import labdb.imobiliaria.models.Cliente;
import java.util.List;

public class ClienteRepository {

    private final EntityManager manager;
    private DAOGenerico<Cliente> daoGenerico;

    public ClienteRepository(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Cliente buscarPorId(Integer id) {
        return daoGenerico.buscaPorId(Cliente.class, id);
    }

    public List<Cliente> buscarTodos() {
        return daoGenerico.buscarTodos(Cliente.class);
    }

    public Cliente criarOuAtualizar(Cliente cliente) {
        if (cliente.getId() == null) {
            if (cpfExiste(cliente.getCpf())) {
                throw new IllegalArgumentException("CPF já cadastrado no sistema.");
            }
        } else {
            Cliente clienteExistente = buscarPorId(cliente.getId());
            if (clienteExistente != null && !clienteExistente.getCpf().equals(cliente.getCpf()) && cpfExiste(cliente.getCpf())) {
                throw new IllegalArgumentException("Já existe outro cliente cadastrado com este CPF.");
            }
        }
        return daoGenerico.salvaOuAtualiza(cliente);
    }

    public void remover(Cliente cliente) {
        daoGenerico.remove(cliente);
    }

    private boolean cpfExiste(String cpf) {
        TypedQuery<Long> query = manager.createQuery(
                "SELECT COUNT(c) FROM Cliente c WHERE c.cpf = :cpf", Long.class);
        query.setParameter("cpf", cpf);
        return query.getSingleResult() > 0;
    }
}
