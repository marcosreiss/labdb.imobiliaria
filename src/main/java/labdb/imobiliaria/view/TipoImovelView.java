package labdb.imobiliaria.view;

import jakarta.persistence.EntityManager;
import labdb.imobiliaria.models.TipoImovel;
import labdb.imobiliaria.repository.TipoImovelRepository;

import java.util.List;
import java.util.Scanner;

public class TipoImovelView {

    private final TipoImovelRepository tipoImovelRepository;
    private final EntityManager entityManager;
    private final Scanner scanner;

    public TipoImovelView(TipoImovelRepository tipoImovelRepository, EntityManager entityManager) {
        this.tipoImovelRepository = tipoImovelRepository;
        this.entityManager = entityManager;
        this.scanner = new Scanner(System.in);
    }

    public void criarTipoImovel() {
        entityManager.getTransaction().begin();
        try {
            TipoImovel tipoImovel = new TipoImovel();

            System.out.print("Digite a descrição do tipo de imóvel: ");
            tipoImovel.setDescricao(scanner.nextLine());

            tipoImovelRepository.criarOuAtualizar(tipoImovel);
            entityManager.getTransaction().commit();
            System.out.println("Tipo de imóvel cadastrado com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao cadastrar tipo de imóvel: " + e.getMessage());
        }
    }

    public void listarTiposImovel() {
        entityManager.getTransaction().begin();
        try {
            List<TipoImovel> tipos = tipoImovelRepository.buscarTodos();
            entityManager.getTransaction().commit();

            if (tipos.isEmpty()) {
                System.out.println("Nenhum tipo de imóvel encontrado.");
                return;
            }
            for (TipoImovel tipo : tipos) {
                System.out.println(tipo);
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao listar tipos de imóvel: " + e.getMessage());
        }
    }

    public void atualizarTipoImovel() {
        entityManager.getTransaction().begin();
        try {
            System.out.print("Digite o ID do tipo de imóvel para atualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            TipoImovel tipoImovel = tipoImovelRepository.buscarPorId(id);
            if (tipoImovel == null) {
                System.out.println("Tipo de imóvel não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }

            System.out.print("Digite a nova descrição (atual: " + tipoImovel.getDescricao() + "): ");
            tipoImovel.setDescricao(scanner.nextLine());

            tipoImovelRepository.criarOuAtualizar(tipoImovel);
            entityManager.getTransaction().commit();
            System.out.println("Tipo de imóvel atualizado com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao atualizar tipo de imóvel: " + e.getMessage());
        }
    }

    public void removerTipoImovel() {
        entityManager.getTransaction().begin();
        try {
            System.out.print("Digite o ID do tipo de imóvel para remover: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            TipoImovel tipoImovel = tipoImovelRepository.buscarPorId(id);
            if (tipoImovel == null) {
                System.out.println("Tipo de imóvel não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }

            tipoImovelRepository.remover(tipoImovel);
            entityManager.getTransaction().commit();
            System.out.println("Tipo de imóvel removido com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao remover tipo de imóvel: " + e.getMessage());
        }
    }
}
