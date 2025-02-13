package labdb.imobiliaria.view;

import jakarta.persistence.EntityManager;
import labdb.imobiliaria.models.Cliente;
import labdb.imobiliaria.repository.ClienteRepository;

import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ClienteView {

    private final ClienteRepository clienteRepository;
    private final EntityManager entityManager;
    private final Scanner scanner;

    public ClienteView(ClienteRepository clienteRepository, EntityManager entityManager) {
        this.clienteRepository = clienteRepository;
        this.entityManager = entityManager;
        this.scanner = new Scanner(System.in);
    }

    public void criarCliente() {
        entityManager.getTransaction().begin();
        try {
            Cliente cliente = new Cliente();

            System.out.print("Digite o nome do cliente: ");
            cliente.setNome(scanner.nextLine());

            System.out.print("Digite o CPF: ");
            cliente.setCpf(scanner.nextLine());

            System.out.print("Digite o telefone: ");
            cliente.setTelefone(scanner.nextLine());

            System.out.print("Digite o email: ");
            cliente.setEmail(scanner.nextLine());

            System.out.print("Digite a data de nascimento (yyyy-MM-dd): ");
            String dataStr = scanner.nextLine();
            try {
                Date dataNascimento = new SimpleDateFormat("yyyy-MM-dd").parse(dataStr);
                cliente.setDataNascimento(dataNascimento);
            } catch (ParseException e) {
                System.out.println("Formato de data inválido!");
                entityManager.getTransaction().rollback();
                return;
            }

            clienteRepository.criarOuAtualizar(cliente);
            entityManager.getTransaction().commit();
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    public void listarClientes() {
        entityManager.getTransaction().begin();
        try {
            List<Cliente> clientes = clienteRepository.buscarTodos();
            entityManager.getTransaction().commit();

            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente encontrado.");
                return;
            }
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }
    }

    public void atualizarCliente() {
        entityManager.getTransaction().begin();
        try {
            System.out.print("Digite o ID do cliente para atualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Cliente cliente = clienteRepository.buscarPorId(id);
            if (cliente == null) {
                System.out.println("Cliente não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }

            System.out.print("Digite o novo nome (atual: " + cliente.getNome() + "): ");
            cliente.setNome(scanner.nextLine());

            System.out.print("Digite o novo telefone (atual: " + cliente.getTelefone() + "): ");
            cliente.setTelefone(scanner.nextLine());

            System.out.print("Digite o novo email (atual: " + cliente.getEmail() + "): ");
            cliente.setEmail(scanner.nextLine());

            clienteRepository.criarOuAtualizar(cliente);
            entityManager.getTransaction().commit();
            System.out.println("Cliente atualizado com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public void removerCliente() {
        entityManager.getTransaction().begin();
        try {
            System.out.print("Digite o ID do cliente para remover: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Cliente cliente = clienteRepository.buscarPorId(id);
            if (cliente == null) {
                System.out.println("Cliente não encontrado!");
                entityManager.getTransaction().rollback();
                return;
            }

            clienteRepository.remover(cliente);
            entityManager.getTransaction().commit();
            System.out.println("Cliente removido com sucesso!");
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao remover cliente: " + e.getMessage());
        }
    }
}
