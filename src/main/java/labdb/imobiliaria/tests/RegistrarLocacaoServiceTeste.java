package labdb.imobiliaria.tests;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import labdb.imobiliaria.models.*;
import labdb.imobiliaria.repository.*;
import labdb.imobiliaria.services.RegistrarLocacaoService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class RegistrarLocacaoServiceTeste {

    private final EntityManagerFactory emf;
    private final EntityManager em;
    private final RegistrarLocacaoService locacaoService;
    private final LocacaoRepository locacaoRepository;
    private final ImovelRepository imovelRepository;
    private final ClienteRepository clienteRepository;
    private final TipoImovelRepository tipoImovelRepository;

    private Imovel imovelDisponivel;
    private Cliente cliente;

    public RegistrarLocacaoServiceTeste() {
        emf = Persistence.createEntityManagerFactory("imobiliariaPU");
        em = emf.createEntityManager();
        locacaoService = new RegistrarLocacaoService();
        locacaoRepository = new LocacaoRepository(em);
        imovelRepository = new ImovelRepository(em);
        clienteRepository = new ClienteRepository(em);
        tipoImovelRepository = new TipoImovelRepository(em);
    }

    public void prepararBancoDeDados() {
        em.getTransaction().begin();

        System.out.println("Populando banco de dados com dados mínimos necessários...");

        // Criando um cliente
        cliente = new Cliente();
        cliente.setNome("João da Silva");
        cliente.setCpf("12345678900");
        cliente.setTelefone("99999-9999");
        cliente.setEmail("joao@email.com");

        try {
            cliente.setDataNascimento(new SimpleDateFormat("yyyy-MM-dd").parse("1990-05-20"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cliente = clienteRepository.criarOuAtualizar(cliente);
        System.out.println("Cliente cadastrado: " + cliente);

        // Criando um tipo de imóvel
        TipoImovel tipoImovel = new TipoImovel();
        tipoImovel.setDescricao("Apartamento");
        tipoImovel = tipoImovelRepository.criarOuAtualizar(tipoImovel);
        System.out.println("Tipo de Imóvel cadastrado: " + tipoImovel);

        // Criando um imóvel disponível
        imovelDisponivel = new Imovel();
        imovelDisponivel.setLogradouro("Rua das Flores, 123");
        imovelDisponivel.setBairro("Centro");
        imovelDisponivel.setCep("12345678");
        imovelDisponivel.setMetragem(80);
        imovelDisponivel.setDormitorios(2);
        imovelDisponivel.setBanheiros(1);
        imovelDisponivel.setSuites(1);
        imovelDisponivel.setVagasGaragem(1);
        imovelDisponivel.setValorAluguelSugerido(2500.00);
        imovelDisponivel.setObs("Imóvel bem localizado.");
        imovelDisponivel.setTipoImovel(tipoImovel);
        imovelDisponivel.setCliente(cliente);

        imovelDisponivel = imovelRepository.criarOuAtualizar(imovelDisponivel);
        System.out.println("Imóvel cadastrado: " + imovelDisponivel);

        em.getTransaction().commit();
        System.out.println("Banco de dados preparado com sucesso!\n");
    }

    public void testarRegistroComSucesso() {
        System.out.println("Testando registro de locação bem-sucedido...");

        Locacao locacao = new Locacao();
        locacao.setImovel(imovelDisponivel);
        locacao.setInquilino(cliente);
        locacao.setValorAluguel(2500.00);
        locacao.setPercentualMulta(2.0);
        locacao.setDiaVencimento(5);

        try {
            locacao.setDataInicio(new SimpleDateFormat("yyyy-MM-dd").parse("2025-03-01"));
            locacao.setDataFim(new SimpleDateFormat("yyyy-MM-dd").parse("2026-03-01"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        locacao.setAtivo(true);
        locacao.setObs("Contrato de locação de um ano.");

        try {
            locacaoService.registrarLocacao(locacao);
            System.out.println("Locação registrada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao registrar locação: " + e.getMessage());
        }
    }

    public void testarRegistroNegado() {
        System.out.println("Testando tentativa de registrar locação em imóvel já alugado...");

        Locacao locacao = new Locacao();
        locacao.setImovel(imovelDisponivel); // Mesmo imóvel já alugado
        locacao.setInquilino(cliente);
        locacao.setValorAluguel(2600.00);
        locacao.setPercentualMulta(3.0);
        locacao.setDiaVencimento(10);

        try {
            locacao.setDataInicio(new SimpleDateFormat("yyyy-MM-dd").parse("2025-06-01"));
            locacao.setDataFim(new SimpleDateFormat("yyyy-MM-dd").parse("2026-06-01"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        locacao.setAtivo(true);
        locacao.setObs("Tentativa de contrato inválido.");

        try {
            locacaoService.registrarLocacao(locacao);
            System.out.println("Erro! A locação foi registrada, mas deveria ter sido negada!");
        } catch (Exception e) {
            System.out.println("Registro de locação negado com sucesso: " + e.getMessage());
        }
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n### Menu de Testes ###");
            System.out.println("1 - Testar registro de locação bem-sucedido");
            System.out.println("2 - Testar tentativa de locação negada");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    testarRegistroComSucesso();
                    break;
                case 2:
                    testarRegistroNegado();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    scanner.close();
                    em.close();
                    emf.close();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    public static void main(String[] args) {
        RegistrarLocacaoServiceTeste test = new RegistrarLocacaoServiceTeste();
        test.prepararBancoDeDados();
        test.exibirMenu();
    }
}
