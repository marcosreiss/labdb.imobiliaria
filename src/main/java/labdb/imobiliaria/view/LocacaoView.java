package labdb.imobiliaria.view;

import jakarta.persistence.EntityManager;
import labdb.imobiliaria.models.Locacao;
import labdb.imobiliaria.models.Imovel;
import labdb.imobiliaria.models.Cliente;
import labdb.imobiliaria.repository.LocacaoRepository;
import labdb.imobiliaria.repository.ImovelRepository;
import labdb.imobiliaria.repository.ClienteRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class LocacaoView {

    private final LocacaoRepository locacaoRepository;
    private final ImovelRepository imovelRepository;
    private final ClienteRepository clienteRepository;
    private final EntityManager entityManager;
    private final Scanner scanner;

    public LocacaoView(LocacaoRepository locacaoRepository, ImovelRepository imovelRepository, ClienteRepository clienteRepository, EntityManager entityManager) {
        this.locacaoRepository = locacaoRepository;
        this.imovelRepository = imovelRepository;
        this.clienteRepository = clienteRepository;
        this.entityManager = entityManager;
        this.scanner = new Scanner(System.in);
    }

    public void criarLocacao() {
        entityManager.getTransaction().begin();
        try {
            Locacao locacao = new Locacao();

            System.out.print("Digite o ID do imóvel para locação: ");
            int imovelId = scanner.nextInt();
            scanner.nextLine();
            Imovel imovel = imovelRepository.buscarPorId(imovelId);
            if (imovel == null) {
                System.out.println("Imóvel não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }
            locacao.setImovel(imovel);

            System.out.print("Digite o ID do inquilino: ");
            int clienteId = scanner.nextInt();
            scanner.nextLine();
            Cliente cliente = clienteRepository.buscarPorId(clienteId);
            if (cliente == null) {
                System.out.println("Inquilino não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }
            locacao.setInquilino(cliente);

            System.out.print("Digite o valor do aluguel: ");
            locacao.setValorAluguel(scanner.nextDouble());

            System.out.print("Digite o percentual da multa: ");
            locacao.setPercentualMulta(scanner.nextDouble());
            scanner.nextLine();

            System.out.print("Digite a data de início (yyyy-MM-dd): ");
            try {
                Date dataInicio = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
                locacao.setDataInicio(dataInicio);
            } catch (ParseException e) {
                System.out.println("Formato de data inválido!");
                entityManager.getTransaction().rollback();
                return;
            }

            System.out.print("Digite a data de fim (yyyy-MM-dd): ");
            try {
                Date dataFim = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
                locacao.setDataFim(dataFim);
            } catch (ParseException e) {
                System.out.println("Formato de data inválido!");
                entityManager.getTransaction().rollback();
                return;
            }

            System.out.print("Digite o dia de vencimento: ");
            locacao.setDiaVencimento(scanner.nextInt());
            scanner.nextLine();

            System.out.print("Digite observações (opcional): ");
            locacao.setObs(scanner.nextLine());

            locacao.setAtivo(true);

            locacaoRepository.criarOuAtualizar(locacao);
            entityManager.getTransaction().commit();
            System.out.println("Locação cadastrada com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao cadastrar locação: " + e.getMessage());
        }
    }

    public void listarLocacoes() {
        entityManager.getTransaction().begin();
        try {
            System.out.print("Digite o ID do cliente para listar locações: ");
            int clienteId = scanner.nextInt();
            scanner.nextLine();
            Cliente cliente = clienteRepository.buscarPorId(clienteId);
            if (cliente == null) {
                System.out.println("Cliente não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }

            List<Locacao> locacoes = locacaoRepository.buscarTodasPorCliente(cliente);
            entityManager.getTransaction().commit();

            if (locacoes.isEmpty()) {
                System.out.println("Nenhuma locação encontrada para esse cliente.");
                return;
            }

            for (Locacao locacao : locacoes) {
                System.out.println(locacao);
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao listar locações: " + e.getMessage());
        }
    }

    public void atualizarLocacao() {
        entityManager.getTransaction().begin();
        try {
            System.out.print("Digite o ID da locação para atualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Locacao locacao = locacaoRepository.buscarPorId(id);
            if (locacao == null) {
                System.out.println("Locação não encontrada!");
                entityManager.getTransaction().rollback();
                return;
            }

            System.out.print("Digite o novo valor do aluguel (atual: " + locacao.getValorAluguel() + "): ");
            locacao.setValorAluguel(scanner.nextDouble());

            System.out.print("Digite o novo percentual da multa (atual: " + locacao.getPercentualMulta() + "): ");
            locacao.setPercentualMulta(scanner.nextDouble());
            scanner.nextLine();

            System.out.print("Digite nova data de fim (yyyy-MM-dd) (atual: " + locacao.getDataFim() + "): ");
            try {
                Date dataFim = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
                locacao.setDataFim(dataFim);
            } catch (ParseException e) {
                System.out.println("Formato de data inválido!");
                entityManager.getTransaction().rollback();
                return;
            }

            System.out.print("Digite novas observações (atual: " + locacao.getObs() + "): ");
            locacao.setObs(scanner.nextLine());

            locacaoRepository.criarOuAtualizar(locacao);
            entityManager.getTransaction().commit();
            System.out.println("Locação atualizada com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao atualizar locação: " + e.getMessage());
        }
    }

    public void removerLocacao() {
        entityManager.getTransaction().begin();
        try {
            System.out.print("Digite o ID da locação para remover: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Locacao locacao = locacaoRepository.buscarPorId(id);
            if (locacao == null) {
                System.out.println("Locação não encontrada!");
                entityManager.getTransaction().rollback();
                return;
            }

            locacaoRepository.remover(locacao);
            entityManager.getTransaction().commit();
            System.out.println("Locação removida com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao remover locação: " + e.getMessage());
        }
    }
}
