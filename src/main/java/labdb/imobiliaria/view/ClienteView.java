package labdb.imobiliaria.view;

import labdb.imobiliaria.models.Cliente;
import labdb.imobiliaria.repository.ClienteRepository;

import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ClienteView {

    private final ClienteRepository clienteRepository;
    private final Scanner scanner;

    public ClienteView(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        this.scanner = new Scanner(System.in);
    }

    public void criarCliente() {
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
            return;
        }

        try {
            clienteRepository.criarOuAtualizar(cliente);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void listarClientes() {
        List<Cliente> clientes = clienteRepository.buscarTodos();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
            return;
        }
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    public void atualizarCliente() {
        System.out.print("Digite o ID do cliente para atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = clienteRepository.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.print("Digite o novo nome (atual: " + cliente.getNome() + "): ");
        cliente.setNome(scanner.nextLine());

        System.out.print("Digite o novo telefone (atual: " + cliente.getTelefone() + "): ");
        cliente.setTelefone(scanner.nextLine());

        System.out.print("Digite o novo email (atual: " + cliente.getEmail() + "): ");
        cliente.setEmail(scanner.nextLine());

        try {
            clienteRepository.criarOuAtualizar(cliente);
            System.out.println("Cliente atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void removerCliente() {
        System.out.print("Digite o ID do cliente para remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = clienteRepository.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        clienteRepository.remover(cliente);
        System.out.println("Cliente removido com sucesso!");
    }
}
