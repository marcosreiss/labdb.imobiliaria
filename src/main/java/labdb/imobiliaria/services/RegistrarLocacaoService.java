package labdb.imobiliaria.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import labdb.imobiliaria.models.Imovel;
import labdb.imobiliaria.models.Locacao;
import labdb.imobiliaria.repository.LocacaoRepository;
import labdb.imobiliaria.util.EMFactory;

public class RegistrarLocacaoService {

    private final LocacaoRepository repositorio;
    private final EntityManager manager;

    public RegistrarLocacaoService() {
        this.manager = new EMFactory().getEntityManager();
        this.repositorio = new LocacaoRepository(manager);
    }

    @Transactional
    public void registrarLocacao(Locacao locacao) {
        manager.getTransaction().begin();

        if (!imovelDisponivel(locacao.getImovel())) {
            throw new IllegalArgumentException("O imóvel já está alugado.");
        }

        locacao.setAtivo(true);
        repositorio.criarOuAtualizar(locacao);
        manager.getTransaction().commit();
    }

    private boolean imovelDisponivel(Imovel imovel) {
        Long locacoesAtivas = manager.createQuery(
                        "SELECT COUNT(l) FROM Locacao l WHERE l.imovel = :imovel AND l.ativo = true", Long.class)
                .setParameter("imovel", imovel)
                .getSingleResult();

        return locacoesAtivas == 0;
    }
}
