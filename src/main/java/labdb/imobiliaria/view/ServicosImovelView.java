package labdb.imobiliaria.view;

import labdb.imobiliaria.models.ServicosImovel;
import labdb.imobiliaria.models.Imovel;
import labdb.imobiliaria.models.Profissional;
import labdb.imobiliaria.repository.ServicosImovelRepository;
import labdb.imobiliaria.repository.ImovelRepository;
import labdb.imobiliaria.repository.ProfissionalRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ServicosImovelView {

    private final ServicosImovelRepository servicosImovelRepository;
    private final ImovelRepository imovelRepository;
    private final ProfissionalRepository profissionalRepository;
    private final Scanner scanner;

    public ServicosImovelView(ServicosImovelRepository servicosImovelRepository, ImovelRepository imovelRepository, ProfissionalRepository profissionalRepository) {
        this.servicosImovelRepository = servicosImovelRepository;
        this.imovelRepository = imovelRepository;
        this.profissionalRepository = profissionalRepository;
        this.scanner = new Scanner(System.in);
    }

    public void criarServicoImovel() {
        ServicosImovel servico = new ServicosImovel();

        System.out.print("Digite o ID do imóvel: ");
        int imovelId = scanner.nextInt();
        scanner.nextLine();
        Imovel imovel = imovelRepository.buscarPorId(imovelId);
        if (imovel == null) {
            System.out.println("Imóvel não encontrado!");
            return;
        }
        servico.setImovel(imovel);

        System.out.print("Digite o ID do profissional responsável pelo serviço: ");
        int profissionalId = scanner.nextInt();
        scanner.nextLine();
        Profissional profissional = profissionalRepository.buscarPorId(profissionalId);
        if (profissional == null) {
            System.out.println("Profissional não encontrado!");
            return;
        }
        servico.setProfissional(profissional);

        System.out.print("Digite a data do serviço (yyyy-MM-dd): ");
        try {
            Date dataServico = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
            servico.setDataServico(dataServico);
        } catch (ParseException e) {
            System.out.println("Formato de data inválido!");
            return;
        }

        System.out.print("Digite o valor total do serviço: ");
        servico.setValorTotal(scanner.nextDouble());
        scanner.nextLine();

        System.out.print("Digite observações (opcional): ");
        servico.setObs(scanner.nextLine());

        servicosImovelRepository.criarOuAtualizar(servico);
        System.out.println("Serviço cadastrado com sucesso!");
    }

    public void listarServicosPorImovel() {
        System.out.print("Digite o ID do imóvel para listar serviços: ");
        int imovelId = scanner.nextInt();
        scanner.nextLine();
        Imovel imovel = imovelRepository.buscarPorId(imovelId);
        if (imovel == null) {
            System.out.println("Imóvel não encontrado!");
            return;
        }

        List<ServicosImovel> servicos = servicosImovelRepository.buscarPorImovel(imovel);
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço encontrado para este imóvel.");
            return;
        }

        for (ServicosImovel servico : servicos) {
            System.out.println(servico);
        }
    }

    public void atualizarServicoImovel() {
        System.out.print("Digite o ID do serviço para atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        ServicosImovel servico = servicosImovelRepository.buscarPorId(id);
        if (servico == null) {
            System.out.println("Serviço não encontrado!");
            return;
        }

        System.out.print("Digite a nova data do serviço (yyyy-MM-dd) (atual: " + servico.getDataServico() + "): ");
        try {
            Date dataServico = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
            servico.setDataServico(dataServico);
        } catch (ParseException e) {
            System.out.println("Formato de data inválido!");
            return;
        }

        System.out.print("Digite o novo valor total (atual: " + servico.getValorTotal() + "): ");
        servico.setValorTotal(scanner.nextDouble());
        scanner.nextLine();

        System.out.print("Digite novas observações (atual: " + servico.getObs() + "): ");
        servico.setObs(scanner.nextLine());

        servicosImovelRepository.criarOuAtualizar(servico);
        System.out.println("Serviço atualizado com sucesso!");
    }

    public void removerServicoImovel() {
        System.out.print("Digite o ID do serviço para remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        ServicosImovel servico = servicosImovelRepository.buscarPorId(id);
        if (servico == null) {
            System.out.println("Serviço não encontrado!");
            return;
        }

        servicosImovelRepository.remover(servico);
        System.out.println("Serviço removido com sucesso!");
    }
}
