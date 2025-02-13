package labdb.imobiliaria.view;

import jakarta.persistence.EntityManager;
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
    private final EntityManager entityManager;
    private final Scanner scanner;

    public ServicosImovelView(ServicosImovelRepository servicosImovelRepository, ImovelRepository imovelRepository, ProfissionalRepository profissionalRepository, EntityManager entityManager) {
        this.servicosImovelRepository = servicosImovelRepository;
        this.imovelRepository = imovelRepository;
        this.profissionalRepository = profissionalRepository;
        this.entityManager = entityManager;
        this.scanner = new Scanner(System.in);
    }

    public void criarServicoImovel() {
        entityManager.getTransaction().begin();
        try {
            ServicosImovel servico = new ServicosImovel();

            System.out.print("Digite o ID do imóvel: ");
            int imovelId = scanner.nextInt();
            scanner.nextLine();
            Imovel imovel = imovelRepository.buscarPorId(imovelId);
            if (imovel == null) {
                System.out.println("Imóvel não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }
            servico.setImovel(imovel);

            System.out.print("Digite o ID do profissional responsável pelo serviço: ");
            int profissionalId = scanner.nextInt();
            scanner.nextLine();
            Profissional profissional = profissionalRepository.buscarPorId(profissionalId);
            if (profissional == null) {
                System.out.println("Profissional não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }
            servico.setProfissional(profissional);

            System.out.print("Digite a data do serviço (yyyy-MM-dd): ");
            try {
                Date dataServico = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
                servico.setDataServico(dataServico);
            } catch (ParseException e) {
                System.out.println("Formato de data inválido!");
                entityManager.getTransaction().rollback();
                return;
            }

            System.out.print("Digite o valor total do serviço: ");
            servico.setValorTotal(scanner.nextDouble());
            scanner.nextLine();

            System.out.print("Digite observações (opcional): ");
            servico.setObs(scanner.nextLine());

            servicosImovelRepository.criarOuAtualizar(servico);
            entityManager.getTransaction().commit();
            System.out.println("Serviço cadastrado com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao cadastrar serviço: " + e.getMessage());
        }
    }

    public void listarServicosPorImovel() {
        entityManager.getTransaction().begin();
        try {
            System.out.print("Digite o ID do imóvel para listar serviços: ");
            int imovelId = scanner.nextInt();
            scanner.nextLine();
            Imovel imovel = imovelRepository.buscarPorId(imovelId);
            if (imovel == null) {
                System.out.println("Imóvel não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }

            List<ServicosImovel> servicos = servicosImovelRepository.buscarPorImovel(imovel);
            entityManager.getTransaction().commit();

            if (servicos.isEmpty()) {
                System.out.println("Nenhum serviço encontrado para este imóvel.");
                return;
            }

            for (ServicosImovel servico : servicos) {
                System.out.println(servico);
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao listar serviços do imóvel: " + e.getMessage());
        }
    }

    public void atualizarServicoImovel() {
        entityManager.getTransaction().begin();
        try {
            System.out.print("Digite o ID do serviço para atualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            ServicosImovel servico = servicosImovelRepository.buscarPorId(id);
            if (servico == null) {
                System.out.println("Serviço não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }

            System.out.print("Digite a nova data do serviço (yyyy-MM-dd) (atual: " + servico.getDataServico() + "): ");
            try {
                Date dataServico = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
                servico.setDataServico(dataServico);
            } catch (ParseException e) {
                System.out.println("Formato de data inválido!");
                entityManager.getTransaction().rollback();
                return;
            }

            System.out.print("Digite o novo valor total (atual: " + servico.getValorTotal() + "): ");
            servico.setValorTotal(scanner.nextDouble());
            scanner.nextLine();

            System.out.print("Digite novas observações (atual: " + servico.getObs() + "): ");
            servico.setObs(scanner.nextLine());

            servicosImovelRepository.criarOuAtualizar(servico);
            entityManager.getTransaction().commit();
            System.out.println("Serviço atualizado com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao atualizar serviço: " + e.getMessage());
        }
    }

    public void removerServicoImovel() {
        entityManager.getTransaction().begin();
        try {
            System.out.print("Digite o ID do serviço para remover: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            ServicosImovel servico = servicosImovelRepository.buscarPorId(id);
            if (servico == null) {
                System.out.println("Serviço não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }

            servicosImovelRepository.remover(servico);
            entityManager.getTransaction().commit();
            System.out.println("Serviço removido com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao remover serviço: " + e.getMessage());
        }
    }
}
