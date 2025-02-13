package labdb.imobiliaria.view;

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
    private final Scanner scanner;

    public LocacaoView(LocacaoRepository locacaoRepository, ImovelRepository imovelRepository, ClienteRepository clienteRepository) {
        this.locacaoRepository = locacaoRepository;
        this.imovelRepository = imovelRepository;
        this.clienteRepository = clienteRepository;
        this.scanner = new Scanner(System.in);
    }

    public void criarLocacao() {
        Locacao locacao = new Locacao();

        System.out.print("Digite o ID do imóvel para locação: ");
        int imovelId = scanner.nextInt();
        scanner.nextLine();
        Imovel imovel = imovelRepository.buscarPorId(imovelId);
        if (imovel == null) {
            System.out.println("Imóvel não encontrado!");
            return;
        }
        locacao.setImovel(imovel);

        System.out.print("Digite o ID do inquilino: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine();
        Cliente cliente = clienteRepository.buscarPorId(clienteId);
        if (cliente == null) {
            System.out.println("Inquilino não encontrado!");
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
            return;
        }

        System.out.print("Digite a data de fim (yyyy-MM-dd): ");
        try {
            Date dataFim = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
            locacao.setDataFim(dataFim);
        } catch (ParseException e) {
            System.out.println("Formato de data inválido!");
            return;
        }

        System.out.print("Digite o dia de vencimento: ");
        locacao.setDiaVencimento(scanner.nextInt());
        scanner.nextLine();

        System.out.print("Digite observações (opcional): ");
        locacao.setObs(scanner.nextLine());

        locacao.setAtivo(true);

        try {
            locacaoRepository.criarOuAtualizar(locacao);
            System.out.println("Locação cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void listarLocacoes() {
        System.out.print("Digite o ID do cliente para listar locações: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine();
        Cliente cliente = clienteRepository.buscarPorId(clienteId);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        List<Locacao> locacoes = locacaoRepository.buscarTodasPorCliente(cliente);
        if (locacoes.isEmpty()) {
            System.out.println("Nenhuma locação encontrada para esse cliente.");
            return;
        }

        for (Locacao locacao : locacoes) {
            System.out.println(locacao);
        }
    }

    public void atualizarLocacao() {
        System.out.print("Digite o ID da locação para atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Locacao locacao = locacaoRepository.buscarPorId(id);
        if (locacao == null) {
            System.out.println("Locação não encontrada!");
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
            return;
        }

        System.out.print("Digite novas observações (atual: " + locacao.getObs() + "): ");
        locacao.setObs(scanner.nextLine());

        try {
            locacaoRepository.criarOuAtualizar(locacao);
            System.out.println("Locação atualizada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void removerLocacao() {
        System.out.print("Digite o ID da locação para remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Locacao locacao = locacaoRepository.buscarPorId(id);
        if (locacao == null) {
            System.out.println("Locação não encontrada!");
            return;
        }

        locacaoRepository.remover(locacao);
        System.out.println("Locação removida com sucesso!");
    }
}
