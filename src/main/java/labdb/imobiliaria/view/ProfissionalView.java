package labdb.imobiliaria.view;

import jakarta.persistence.EntityManager;
import labdb.imobiliaria.models.Profissional;
import labdb.imobiliaria.repository.ProfissionalRepository;

import java.util.List;
import java.util.Scanner;

public class ProfissionalView {

    private final ProfissionalRepository profissionalRepository;
    private final EntityManager entityManager;
    private final Scanner scanner;

    public ProfissionalView(ProfissionalRepository profissionalRepository, EntityManager entityManager) {
        this.profissionalRepository = profissionalRepository;
        this.entityManager = entityManager;
        this.scanner = new Scanner(System.in);
    }

    public void criarProfissional() {
        entityManager.getTransaction().begin();
        try {
            Profissional profissional = new Profissional();

            System.out.print("Digite o nome do profissional: ");
            profissional.setNome(scanner.nextLine());

            System.out.print("Digite a profissão: ");
            profissional.setProfissao(scanner.nextLine());

            System.out.print("Digite o telefone 1: ");
            profissional.setTelefone1(scanner.nextLine());

            System.out.print("Digite o telefone 2 (opcional): ");
            profissional.setTelefone2(scanner.nextLine());

            System.out.print("Digite o email: ");
            profissional.setEmail(scanner.nextLine());

            System.out.print("Digite o valor por hora: ");
            profissional.setValorHora(scanner.nextDouble());
            scanner.nextLine();

            System.out.print("Digite observações (opcional): ");
            profissional.setObs(scanner.nextLine());

            profissionalRepository.criarOuAtualizar(profissional);
            entityManager.getTransaction().commit();
            System.out.println("Profissional cadastrado com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao cadastrar profissional: " + e.getMessage());
        }
    }

    public void listarProfissionais() {
        entityManager.getTransaction().begin();
        try {
            List<Profissional> profissionais = profissionalRepository.buscarTodos();
            entityManager.getTransaction().commit();

            if (profissionais.isEmpty()) {
                System.out.println("Nenhum profissional encontrado.");
                return;
            }
            for (Profissional profissional : profissionais) {
                System.out.println(profissional);
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao listar profissionais: " + e.getMessage());
        }
    }

    public void atualizarProfissional() {
        entityManager.getTransaction().begin();
        try {
            System.out.print("Digite o ID do profissional para atualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Profissional profissional = profissionalRepository.buscarPorId(id);
            if (profissional == null) {
                System.out.println("Profissional não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }

            System.out.print("Digite o novo nome (atual: " + profissional.getNome() + "): ");
            profissional.setNome(scanner.nextLine());

            System.out.print("Digite a nova profissão (atual: " + profissional.getProfissao() + "): ");
            profissional.setProfissao(scanner.nextLine());

            System.out.print("Digite o novo telefone 1 (atual: " + profissional.getTelefone1() + "): ");
            profissional.setTelefone1(scanner.nextLine());

            System.out.print("Digite o novo telefone 2 (atual: " + profissional.getTelefone2() + "): ");
            profissional.setTelefone2(scanner.nextLine());

            System.out.print("Digite o novo email (atual: " + profissional.getEmail() + "): ");
            profissional.setEmail(scanner.nextLine());

            System.out.print("Digite o novo valor por hora (atual: " + profissional.getValorHora() + "): ");
            profissional.setValorHora(scanner.nextDouble());
            scanner.nextLine();

            System.out.print("Digite novas observações (atual: " + profissional.getObs() + "): ");
            profissional.setObs(scanner.nextLine());

            profissionalRepository.criarOuAtualizar(profissional);
            entityManager.getTransaction().commit();
            System.out.println("Profissional atualizado com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao atualizar profissional: " + e.getMessage());
        }
    }

    public void removerProfissional() {
        entityManager.getTransaction().begin();
        try {
            System.out.print("Digite o ID do profissional para remover: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Profissional profissional = profissionalRepository.buscarPorId(id);
            if (profissional == null) {
                System.out.println("Profissional não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }

            profissionalRepository.remover(profissional);
            entityManager.getTransaction().commit();
            System.out.println("Profissional removido com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao remover profissional: " + e.getMessage());
        }
    }
}
