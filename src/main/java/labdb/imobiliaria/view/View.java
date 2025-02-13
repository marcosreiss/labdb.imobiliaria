package labdb.imobiliaria.view;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import labdb.imobiliaria.models.*;
import labdb.imobiliaria.repository.*;

import java.util.Scanner;

public class View {

    private static final Scanner scanner = new Scanner(System.in);

    // Fábrica e gerenciador de entidades
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("imobiliariaPU");
    private static final EntityManager em = emf.createEntityManager();

    // Repositories
    private static final AluguelRepository aluguelRepository = new AluguelRepository(em);
    private static final ClienteRepository clienteRepository = new ClienteRepository(em);
    private static final ProfissionalRepository profissionalRepository = new ProfissionalRepository(em);
    private static final TipoImovelRepository tipoImovelRepository = new TipoImovelRepository(em);
    private static final ImovelRepository imovelRepository = new ImovelRepository(em);
    private static final LocacaoRepository locacaoRepository = new LocacaoRepository(em);
    private static final ServicosImovelRepository servicosImovelRepository = new ServicosImovelRepository(em);

    // Views
    private static final AluguelView aluguelView = new AluguelView(aluguelRepository, em);
    private static final ClienteView clienteView = new ClienteView(clienteRepository, em);
    private static final ProfissionalView profissionalView = new ProfissionalView(profissionalRepository, em);
    private static final TipoImovelView tipoImovelView = new TipoImovelView(tipoImovelRepository, em);
    // Imóvel precisa de TipoImovel e Cliente
    private static final ImovelView imovelView = new ImovelView(imovelRepository, tipoImovelRepository, clienteRepository, em);
    // Locação precisa de Imóvel e Cliente
    private static final LocacaoView locacaoView = new LocacaoView(locacaoRepository, imovelRepository, clienteRepository, em);
    // Serviços de Imóvel precisam de Imóvel e Profissional
    private static final ServicosImovelView servicosImovelView = new ServicosImovelView(servicosImovelRepository, imovelRepository, profissionalRepository, em);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n### Menu Principal ###");
            System.out.println("1 - Aluguel");
            System.out.println("2 - Cliente");
            System.out.println("3 - Imóvel");
            System.out.println("4 - Locação");
            System.out.println("5 - Profissional");
            System.out.println("6 - Serviço do Imóvel");
            System.out.println("7 - Tipo de Imóvel");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1: menuAluguel(); break;
                case 2: menuCliente(); break;
                case 3: menuImovel(); break;
                case 4: menuLocacao(); break;
                case 5: menuProfissional(); break;
                case 6: menuServicoImovel(); break;
                case 7: menuTipoImovel(); break;
                case 0:
                    System.out.println("Saindo...");
                    em.close();
                    emf.close();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // ---------------------- MENU ALUGUEL ----------------------
    private static void menuAluguel() {
        while (true) {
            System.out.println("\n### Menu Aluguel ###");
            System.out.println("1 - Criar");
            System.out.println("2 - Ler");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1: aluguelView.criarAluguel(); break;
                case 2: aluguelView.listarAlugueis(); break;
                case 3: aluguelView.atualizarAluguel(); break;
                case 4: aluguelView.removerAluguel(); break;
                case 0: return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // ---------------------- MENU CLIENTE ----------------------
    private static void menuCliente() {
        while (true) {
            System.out.println("\n### Menu Cliente ###");
            System.out.println("1 - Criar");
            System.out.println("2 - Ler");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1: clienteView.criarCliente(); break;
                case 2: clienteView.listarClientes(); break;
                case 3: clienteView.atualizarCliente(); break;
                case 4: clienteView.removerCliente(); break;
                case 0: return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // ---------------------- MENU IMÓVEL ----------------------
    private static void menuImovel() {
        while (true) {
            System.out.println("\n### Menu Imóvel ###");
            System.out.println("1 - Criar");
            System.out.println("2 - Ler");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1: imovelView.criarImovel(); break;
                case 2: imovelView.listarImoveis(); break;
                case 3: imovelView.atualizarImovel(); break;
                case 4: imovelView.removerImovel(); break;
                case 0: return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // ---------------------- MENU LOCAÇÃO ----------------------
    private static void menuLocacao() {
        while (true) {
            System.out.println("\n### Menu Locação ###");
            System.out.println("1 - Criar");
            System.out.println("2 - Ler (Listar por Cliente)");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1: locacaoView.criarLocacao(); break;
                case 2: locacaoView.listarLocacoes(); break;
                case 3: locacaoView.atualizarLocacao(); break;
                case 4: locacaoView.removerLocacao(); break;
                case 0: return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // ---------------------- MENU PROFISSIONAL ----------------------
    private static void menuProfissional() {
        while (true) {
            System.out.println("\n### Menu Profissional ###");
            System.out.println("1 - Criar");
            System.out.println("2 - Ler");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1: profissionalView.criarProfissional(); break;
                case 2: profissionalView.listarProfissionais(); break;
                case 3: profissionalView.atualizarProfissional(); break;
                case 4: profissionalView.removerProfissional(); break;
                case 0: return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // ---------------------- MENU SERVIÇO IMÓVEL ----------------------
    private static void menuServicoImovel() {
        while (true) {
            System.out.println("\n### Menu Serviço do Imóvel ###");
            System.out.println("1 - Criar");
            System.out.println("2 - Ler (Listar por Imóvel)");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1: servicosImovelView.criarServicoImovel(); break;
                case 2: servicosImovelView.listarServicosPorImovel(); break;
                case 3: servicosImovelView.atualizarServicoImovel(); break;
                case 4: servicosImovelView.removerServicoImovel(); break;
                case 0: return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // ---------------------- MENU TIPO IMÓVEL ----------------------
    private static void menuTipoImovel() {
        while (true) {
            System.out.println("\n### Menu Tipo de Imóvel ###");
            System.out.println("1 - Criar");
            System.out.println("2 - Ler");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1: tipoImovelView.criarTipoImovel(); break;
                case 2: tipoImovelView.listarTiposImovel(); break;
                case 3: tipoImovelView.atualizarTipoImovel(); break;
                case 4: tipoImovelView.removerTipoImovel(); break;
                case 0: return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
