package labdb.imobiliaria.tests;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import labdb.imobiliaria.models.*;
import labdb.imobiliaria.repository.*;
import labdb.imobiliaria.services.RegistrarPagamentoService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class RegistrarPagamentoServiceTeste {

    private final EntityManagerFactory emf;
    private final EntityManager em;
    private final RegistrarPagamentoService pagamentoService;
    private final AluguelRepository aluguelRepository;
    private final LocacaoRepository locacaoRepository;
    private final ImovelRepository imovelRepository;
    private final ClienteRepository clienteRepository;
    private final TipoImovelRepository tipoImovelRepository;

    private Aluguel aluguel;
    private Cliente cliente;
    private Imovel imovel;

    public RegistrarPagamentoServiceTeste() {
        emf = Persistence.createEntityManagerFactory("imobiliaria");
        em = emf.createEntityManager();
        pagamentoService = new RegistrarPagamentoService();
        aluguelRepository = new AluguelRepository(em);
        locacaoRepository = new LocacaoRepository(em);
        imovelRepository = new ImovelRepository(em);
        clienteRepository = new ClienteRepository(em);
        tipoImovelRepository = new TipoImovelRepository(em);
    }

    public void prepararBancoDeDados() {
        em.getTransaction().begin();

        System.out.println("Populando banco de dados com dados mínimos necessários...");

        cliente = new Cliente();
        cliente.setNome("Maria Oliveira");
        cliente.setCpf("98765432100");
        cliente.setTelefone("98888-8888");
        cliente.setEmail("maria@email.com");

        try {
            cliente.setDataNascimento(new SimpleDateFormat("yyyy-MM-dd").parse("1985-08-15"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cliente = clienteRepository.criarOuAtualizar(cliente);
        System.out.println("Cliente cadastrado: " + cliente);

        TipoImovel tipoImovel = new TipoImovel();
        tipoImovel.setDescricao("Casa");
        tipoImovel = tipoImovelRepository.criarOuAtualizar(tipoImovel);
        System.out.println("Tipo de Imóvel cadastrado: " + tipoImovel);

        imovel = new Imovel();
        imovel.setLogradouro("Avenida Brasil, 456");
        imovel.setBairro("Centro");
        imovel.setCep("98765432");
        imovel.setMetragem(120);
        imovel.setDormitorios(3);
        imovel.setBanheiros(2);
        imovel.setSuites(1);
        imovel.setVagasGaragem(2);
        imovel.setValorAluguelSugerido(3200.00);
        imovel.setObs("Casa espaçosa e bem iluminada.");
        imovel.setTipoImovel(tipoImovel);
        imovel.setCliente(cliente);

        imovel = imovelRepository.criarOuAtualizar(imovel);
        System.out.println("Imóvel cadastrado: " + imovel);

        Locacao locacao = new Locacao();
        locacao.setImovel(imovel);
        locacao.setInquilino(cliente);
        locacao.setValorAluguel(3200.00);
        locacao.setPercentualMulta(2.0);
        locacao.setDiaVencimento(10);

        try {
            locacao.setDataInicio(new SimpleDateFormat("yyyy-MM-dd").parse("2025-02-01"));
            locacao.setDataFim(new SimpleDateFormat("yyyy-MM-dd").parse("2026-02-01"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        locacao.setAtivo(true);
        locacao.setObs("Contrato de locação de um ano.");

        locacao = locacaoRepository.criarOuAtualizar(locacao);
        System.out.println("Locação cadastrada: " + locacao);

        aluguel = new Aluguel();
        aluguel.setLocacao(locacao);

        try {
            aluguel.setDataVencimento(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-10"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        aluguel.setValorPago(0.0);
        aluguel.setDataPagamento(null);
        aluguel.setObs("Aluguel aguardando pagamento.");

        aluguel = aluguelRepository.criarOuAtualizar(aluguel);
        System.out.println("Aluguel cadastrado: " + aluguel);

        em.getTransaction().commit();
        System.out.println("Banco de dados preparado com sucesso!\n");
    }

    public void testarPagamentoSemAtraso() {
        System.out.println("Testando pagamento sem atraso...");
        em.getTransaction().begin();
        try {
            Date dataPagamento = new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-10");
            pagamentoService.registrarPagamento(aluguel, dataPagamento);
            em.getTransaction().commit();
            System.out.println("Pagamento registrado com sucesso!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao registrar pagamento: " + e.getMessage());
        }
    }

    public void testarPagamentoComAtraso() {
        System.out.println("Testando pagamento com atraso...");
        em.getTransaction().begin();
        try {
            Date dataPagamento = new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-20");
            pagamentoService.registrarPagamento(aluguel, dataPagamento);
            em.getTransaction().commit();
            System.out.println("Pagamento registrado com sucesso!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Erro ao registrar pagamento: " + e.getMessage());
        }
    }

    public void testarPagamentoAluguelInexistente() {
        System.out.println("Testando tentativa de pagamento em aluguel inexistente...");
        em.getTransaction().begin();
        try {
            Aluguel aluguelInexistente = new Aluguel();
            aluguelInexistente.setId(999); // ID inexistente
            Date dataPagamento = new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-10");
            pagamentoService.registrarPagamento(aluguelInexistente, dataPagamento);
            em.getTransaction().commit();
            System.out.println("Erro! Pagamento foi registrado, mas deveria ter sido negado!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Registro de pagamento negado com sucesso: " + e.getMessage());
        }
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("\n### Menu de Testes ###");
                System.out.println("1 - Testar pagamento sem atraso");
                System.out.println("2 - Testar pagamento com atraso");
                System.out.println("3 - Testar tentativa de pagamento em aluguel inexistente");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");

                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> testarPagamentoSemAtraso();
                    case 2 -> testarPagamentoComAtraso();
                    case 3 -> testarPagamentoAluguelInexistente();
                    case 0 -> {
                        System.out.println("Saindo...");
                        return;
                    }
                    default -> System.out.println("Opção inválida!");
                }
            }
        } finally {
            scanner.close();
            em.close();
            emf.close();
        }
    }

    public static void main(String[] args) {
        RegistrarPagamentoServiceTeste test = new RegistrarPagamentoServiceTeste();
        test.prepararBancoDeDados();
        test.exibirMenu();
    }
}
