package labdb.imobiliaria.view;

import jakarta.persistence.EntityManager;
import labdb.imobiliaria.models.Imovel;
import labdb.imobiliaria.models.TipoImovel;
import labdb.imobiliaria.models.Cliente;
import labdb.imobiliaria.repository.ImovelRepository;
import labdb.imobiliaria.repository.TipoImovelRepository;
import labdb.imobiliaria.repository.ClienteRepository;

import java.util.List;
import java.util.Scanner;

public class ImovelView {

    private final ImovelRepository imovelRepository;
    private final TipoImovelRepository tipoImovelRepository;
    private final ClienteRepository clienteRepository;
    private final EntityManager entityManager;
    private final Scanner scanner;

    public ImovelView(ImovelRepository imovelRepository, TipoImovelRepository tipoImovelRepository, ClienteRepository clienteRepository, EntityManager entityManager) {
        this.imovelRepository = imovelRepository;
        this.tipoImovelRepository = tipoImovelRepository;
        this.clienteRepository = clienteRepository;
        this.entityManager = entityManager;
        this.scanner = new Scanner(System.in);
    }

    public void criarImovel() {
        entityManager.getTransaction().begin();
        try {
            Imovel imovel = new Imovel();

            System.out.print("Digite o logradouro: ");
            imovel.setLogradouro(scanner.nextLine());

            System.out.print("Digite o bairro: ");
            imovel.setBairro(scanner.nextLine());

            System.out.print("Digite o CEP: ");
            imovel.setCep(scanner.nextLine());

            System.out.print("Digite a metragem: ");
            imovel.setMetragem(scanner.nextInt());

            System.out.print("Digite o número de dormitórios: ");
            imovel.setDormitorios(scanner.nextInt());

            System.out.print("Digite o número de banheiros: ");
            imovel.setBanheiros(scanner.nextInt());

            System.out.print("Digite o número de suítes: ");
            imovel.setSuites(scanner.nextInt());

            System.out.print("Digite o número de vagas na garagem: ");
            imovel.setVagasGaragem(scanner.nextInt());

            System.out.print("Digite o valor do aluguel sugerido: ");
            imovel.setValorAluguelSugerido(scanner.nextDouble());
            scanner.nextLine();

            System.out.print("Digite observações (opcional): ");
            imovel.setObs(scanner.nextLine());

            System.out.print("Digite o ID do tipo de imóvel: ");
            int tipoImovelId = scanner.nextInt();

            scanner.nextLine();
            TipoImovel tipoImovel = tipoImovelRepository.buscarPorId(tipoImovelId);
            if (tipoImovel == null) {
                System.out.println("Tipo de imóvel não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }
            imovel.setTipoImovel(tipoImovel);

            System.out.print("Digite o ID do cliente dono do imóvel: ");
            int clienteId = scanner.nextInt();
            scanner.nextLine();
            Cliente cliente = clienteRepository.buscarPorId(clienteId);
            if (cliente == null) {
                System.out.println("Cliente não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }
            imovel.setCliente(cliente);

            imovelRepository.criarOuAtualizar(imovel);
            entityManager.getTransaction().commit();
            System.out.println("Imóvel cadastrado com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao cadastrar imóvel: " + e.getMessage());
        }
    }

    public void listarImoveis() {
        entityManager.getTransaction().begin();
        try {
            List<Imovel> imoveis = imovelRepository.buscarTodos();
            entityManager.getTransaction().commit();

            if (imoveis.isEmpty()) {
                System.out.println("Nenhum imóvel encontrado.");
                return;
            }
            for (Imovel imovel : imoveis) {
                System.out.println(imovel);
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao listar imóveis: " + e.getMessage());
        }
    }

    public void atualizarImovel() {
        entityManager.getTransaction().begin();
        try {
            System.out.print("Digite o ID do imóvel para atualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Imovel imovel = imovelRepository.buscarPorId(id);
            if (imovel == null) {
                System.out.println("Imóvel não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }

            System.out.print("Digite o novo logradouro (atual: " + imovel.getLogradouro() + "): ");
            imovel.setLogradouro(scanner.nextLine());

            System.out.print("Digite o novo bairro (atual: " + imovel.getBairro() + "): ");
            imovel.setBairro(scanner.nextLine());

            System.out.print("Digite o novo CEP (atual: " + imovel.getCep() + "): ");
            imovel.setCep(scanner.nextLine());

            System.out.print("Digite a nova metragem (atual: " + imovel.getMetragem() + "): ");
            imovel.setMetragem(scanner.nextInt());

            System.out.print("Digite o novo número de dormitórios (atual: " + imovel.getDormitorios() + "): ");
            imovel.setDormitorios(scanner.nextInt());

            System.out.print("Digite o novo número de banheiros (atual: " + imovel.getBanheiros() + "): ");
            imovel.setBanheiros(scanner.nextInt());

            System.out.print("Digite o novo número de suítes (atual: " + imovel.getSuites() + "): ");
            imovel.setSuites(scanner.nextInt());

            System.out.print("Digite o novo número de vagas na garagem (atual: " + imovel.getVagasGaragem() + "): ");
            imovel.setVagasGaragem(scanner.nextInt());

            System.out.print("Digite o novo valor do aluguel sugerido (atual: " + imovel.getValorAluguelSugerido() + "): ");
            imovel.setValorAluguelSugerido(scanner.nextDouble());
            scanner.nextLine();

            System.out.print("Digite novas observações (atual: " + imovel.getObs() + "): ");
            imovel.setObs(scanner.nextLine());

            System.out.print("Digite o ID do novo tipo de imóvel: ");
            int tipoImovelId = scanner.nextInt();
            scanner.nextLine();
            TipoImovel tipoImovel = tipoImovelRepository.buscarPorId(tipoImovelId);
            if (tipoImovel == null) {
                System.out.println("Tipo de imóvel não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }
            imovel.setTipoImovel(tipoImovel);

            System.out.print("Digite o ID do novo cliente dono do imóvel: ");
            int clienteId = scanner.nextInt();
            scanner.nextLine();
            Cliente cliente = clienteRepository.buscarPorId(clienteId);
            if (cliente == null) {
                System.out.println("Cliente não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }
            imovel.setCliente(cliente);

            imovelRepository.criarOuAtualizar(imovel);
            entityManager.getTransaction().commit();
            System.out.println("Imóvel atualizado com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao atualizar imóvel: " + e.getMessage());
        }
    }

    public void removerImovel() {
        entityManager.getTransaction().begin();
        try {
            System.out.print("Digite o ID do imóvel para remover: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Imovel imovel = imovelRepository.buscarPorId(id);
            if (imovel == null) {
                System.out.println("Imóvel não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }

            imovelRepository.remover(imovel);
            entityManager.getTransaction().commit();
            System.out.println("Imóvel removido com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao remover imóvel: " + e.getMessage());
        }
    }
}
