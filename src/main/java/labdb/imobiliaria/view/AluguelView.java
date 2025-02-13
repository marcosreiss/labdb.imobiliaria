package labdb.imobiliaria.view;

import jakarta.persistence.EntityManager;
import labdb.imobiliaria.models.Aluguel;
import labdb.imobiliaria.repository.AluguelRepository;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AluguelView {

    private final AluguelRepository aluguelRepository;
    private final EntityManager entityManager;
    private final Scanner scanner;

    public AluguelView(AluguelRepository aluguelRepository, EntityManager entityManager) {
        this.aluguelRepository = aluguelRepository;
        this.entityManager = entityManager;
        this.scanner = new Scanner(System.in);
    }

    public void criarAluguel() {
        entityManager.getTransaction().begin();
        try {
            Aluguel aluguel = new Aluguel();

            System.out.print("Digite a data de vencimento (yyyy-mm-dd): ");
            aluguel.setDataVencimento(java.sql.Date.valueOf(scanner.nextLine()));

            System.out.print("Digite o valor pago: ");
            aluguel.setValorPago(scanner.nextDouble());
            scanner.nextLine();

            System.out.print("Digite observações: ");
            aluguel.setObs(scanner.nextLine());

            aluguelRepository.criarOuAtualizar(aluguel);
            entityManager.getTransaction().commit();
            System.out.println("Aluguel cadastrado com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao cadastrar aluguel: " + e.getMessage());
        }
    }

    public void listarAlugueis() {
        entityManager.getTransaction().begin();
        try {
            List<Aluguel> alugueis = aluguelRepository.buscarTodos();
            entityManager.getTransaction().commit();

            if (alugueis.isEmpty()) {
                System.out.println("Nenhum aluguel encontrado.");
                return;
            }
            for (Aluguel aluguel : alugueis) {
                System.out.println(aluguel);
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao listar aluguéis: " + e.getMessage());
        }
    }

    public void atualizarAluguel() {
        entityManager.getTransaction().begin();
        try {
            System.out.print("Digite o ID do aluguel para atualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Aluguel aluguel = aluguelRepository.buscarPorId(id);
            if (aluguel == null) {
                System.out.println("Aluguel não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }

            System.out.print("Digite novo valor pago: ");
            aluguel.setValorPago(scanner.nextDouble());
            scanner.nextLine();

            System.out.print("Digite novas observações: ");
            aluguel.setObs(scanner.nextLine());

            aluguelRepository.criarOuAtualizar(aluguel);
            entityManager.getTransaction().commit();
            System.out.println("Aluguel atualizado com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao atualizar aluguel: " + e.getMessage());
        }
    }

    public void removerAluguel() {
        entityManager.getTransaction().begin();
        try {
            System.out.print("Digite o ID do aluguel para remover: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Aluguel aluguel = aluguelRepository.buscarPorId(id);
            if (aluguel == null) {
                System.out.println("Aluguel não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }

            aluguelRepository.remover(aluguel);
            entityManager.getTransaction().commit();
            System.out.println("Aluguel removido com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao remover aluguel: " + e.getMessage());
        }
    }
}
