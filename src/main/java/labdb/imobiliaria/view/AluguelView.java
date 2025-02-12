package labdb.imobiliaria.view;

import labdb.imobiliaria.models.Aluguel;
import labdb.imobiliaria.repository.AluguelRepository;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AluguelView {

    private final AluguelRepository aluguelRepository;
    private final Scanner scanner;

    public AluguelView(AluguelRepository aluguelRepository) {
        this.aluguelRepository = aluguelRepository;
        this.scanner = new Scanner(System.in);
    }

    public void criarAluguel() {
        Aluguel aluguel = new Aluguel();

        System.out.print("Digite a data de vencimento (yyyy-mm-dd): ");
        aluguel.setDataVencimento(java.sql.Date.valueOf(scanner.nextLine()));

        System.out.print("Digite o valor pago: ");
        aluguel.setValorPago(scanner.nextDouble());
        scanner.nextLine();

        System.out.print("Digite observações: ");
        aluguel.setObs(scanner.nextLine());

        aluguelRepository.criarOuAtualizar(aluguel);
        System.out.println("Aluguel cadastrado com sucesso!");
    }

    public void listarAlugueis() {
        List<Aluguel> alugueis = aluguelRepository.buscarTodos();
        for (Aluguel aluguel : alugueis) {
            System.out.println(aluguel);
        }
    }

    public void atualizarAluguel() {
        System.out.print("Digite o ID do aluguel para atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Aluguel aluguel = aluguelRepository.buscarPorId(id);
        if (aluguel == null) {
            System.out.println("Aluguel não encontrado!");
            return;
        }

        System.out.print("Digite novo valor pago: ");
        aluguel.setValorPago(scanner.nextDouble());
        scanner.nextLine();

        System.out.print("Digite novas observações: ");
        aluguel.setObs(scanner.nextLine());

        aluguelRepository.criarOuAtualizar(aluguel);
        System.out.println("Aluguel atualizado com sucesso!");
    }

    public void removerAluguel() {
        System.out.print("Digite o ID do aluguel para remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Aluguel aluguel = aluguelRepository.buscarPorId(id);
        if (aluguel == null) {
            System.out.println("Aluguel não encontrado!");
            return;
        }

        aluguelRepository.remover(aluguel);
        System.out.println("Aluguel removido com sucesso!");
    }
}
