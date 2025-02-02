package labdb.imobiliaria.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import labdb.imobiliaria.models.Aluguel;
import labdb.imobiliaria.repository.AluguelRepository;
import labdb.imobiliaria.util.EMFactory;

import java.util.Date;

public class RegistrarPagamentoService {
    private final AluguelRepository repositorio;
    private final EntityManager manager;

    public RegistrarPagamentoService() {
        this.manager = new EMFactory().getEntityManager();
        this.repositorio = new AluguelRepository(manager);
    }

    @Transactional
    public void registrarPagamento(Aluguel aluguel, Date dataPagamento) {
        manager.getTransaction().begin();

        aluguel = repositorio.buscarPorId(aluguel.getId());
        if (aluguel == null) {
            throw new IllegalArgumentException("Aluguel não encontrado.");
        }

        double valorFinal = calcularMulta(aluguel, dataPagamento);
        aluguel.setValorPago(valorFinal);
        aluguel.setDataPagamento(dataPagamento);

        repositorio.criarOuAtualizar(aluguel);
        manager.getTransaction().commit();
    }

    private double calcularMulta(Aluguel aluguel, Date dataPagamento) {
        long diasAtraso = (dataPagamento.getTime() - aluguel.getDataVencimento().getTime()) / (1000 * 60 * 60 * 24);

        if (diasAtraso > 0) {
            double multa = aluguel.getLocacao().getValorAluguel() * 0.0033 * diasAtraso;
            return Math.min(multa, aluguel.getLocacao().getValorAluguel() * 0.20) + aluguel.getLocacao().getValorAluguel();
        }
        return aluguel.getLocacao().getValorAluguel();
    }
}
