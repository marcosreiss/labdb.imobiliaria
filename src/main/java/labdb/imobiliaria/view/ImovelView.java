package labdb.imobiliaria.view;

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
    private final Scanner scanner;

    public ImovelView(ImovelRepository imovelRepository, TipoImovelRepository tipoImovelRepository, ClienteRepository clienteRepository) {
        this.imovelRepository = imovelRepository;
        this.tipoImovelRepository = tipoImovelRepository;
        this.clienteRepository = clienteRepository;
        this.scanner = new Scanner(System.in);
    }

    public void criarImovel() {
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
            return;
        }
        imovel.setTipoImovel(tipoImovel);

        System.out.print("Digite o ID do cliente dono do imóvel: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine();
        Cliente cliente = clienteRepository.buscarPorId(clienteId);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }
        imovel.setCliente(cliente);

        imovelRepository.criarOuAtualizar(imovel);
        System.out.println("Imóvel cadastrado com sucesso!");
    }

    public void listarImoveis() {
        List<Imovel> imoveis = imovelRepository.buscarTodos();
        if (imoveis.isEmpty()) {
            System.out.println("Nenhum imóvel encontrado.");
            return;
        }
        for (Imovel imovel : imoveis) {
            System.out.println(imovel);
        }
    }

    public void atualizarImovel() {
        System.out.print("Digite o ID do imóvel para atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Imovel imovel = imovelRepository.buscarPorId(id);
        if (imovel == null) {
            System.out.println("Imóvel não encontrado!");
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

        System.out.print("Digite o ID do novo tipo de imóvel (atual: " + imovel.getTipoImovel().getId() + "): ");
        int tipoImovelId = scanner.nextInt();
        scanner.nextLine();
        TipoImovel tipoImovel = tipoImovelRepository.buscarPorId(tipoImovelId);
        if (tipoImovel == null) {
            System.out.println("Tipo de imóvel não encontrado!");
            return;
        }
        imovel.setTipoImovel(tipoImovel);

        System.out.print("Digite o ID do novo cliente dono do imóvel (atual: " + imovel.getCliente().getId() + "): ");
        int clienteId = scanner.nextInt();
        scanner.nextLine();
        Cliente cliente = clienteRepository.buscarPorId(clienteId);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }
        imovel.setCliente(cliente);

        imovelRepository.criarOuAtualizar(imovel);
        System.out.println("Imóvel atualizado com sucesso!");
    }

    public void removerImovel() {
        System.out.print("Digite o ID do imóvel para remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Imovel imovel = imovelRepository.buscarPorId(id);
        if (imovel == null) {
            System.out.println("Imóvel não encontrado!");
            return;
        }

        imovelRepository.remover(imovel);
        System.out.println("Imóvel removido com sucesso!");
    }
}
