package labdb.imobiliaria.view;

import labdb.imobiliaria.models.TipoImovel;
import labdb.imobiliaria.repository.TipoImovelRepository;

import java.util.List;
import java.util.Scanner;

public class TipoImovelView {

    private final TipoImovelRepository tipoImovelRepository;
    private final Scanner scanner;

    public TipoImovelView(TipoImovelRepository tipoImovelRepository) {
        this.tipoImovelRepository = tipoImovelRepository;
        this.scanner = new Scanner(System.in);
    }

    public void criarTipoImovel() {
        TipoImovel tipoImovel = new TipoImovel();

        System.out.print("Digite a descrição do tipo de imóvel: ");
        tipoImovel.setDescricao(scanner.nextLine());

        tipoImovelRepository.criarOuAtualizar(tipoImovel);
        System.out.println("Tipo de imóvel cadastrado com sucesso!");
    }

    public void listarTiposImovel() {
        List<TipoImovel> tipos = tipoImovelRepository.buscarTodos();
        if (tipos.isEmpty()) {
            System.out.println("Nenhum tipo de imóvel encontrado.");
            return;
        }
        for (TipoImovel tipo : tipos) {
            System.out.println(tipo);
        }
    }

    public void atualizarTipoImovel() {
        System.out.print("Digite o ID do tipo de imóvel para atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        TipoImovel tipoImovel = tipoImovelRepository.buscarPorId(id);
        if (tipoImovel == null) {
            System.out.println("Tipo de imóvel não encontrado!");
            return;
        }

        System.out.print("Digite a nova descrição (atual: " + tipoImovel.getDescricao() + "): ");
        tipoImovel.setDescricao(scanner.nextLine());

        tipoImovelRepository.criarOuAtualizar(tipoImovel);
        System.out.println("Tipo de imóvel atualizado com sucesso!");
    }

    public void removerTipoImovel() {
        System.out.print("Digite o ID do tipo de imóvel para remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        TipoImovel tipoImovel = tipoImovelRepository.buscarPorId(id);
        if (tipoImovel == null) {
            System.out.println("Tipo de imóvel não encontrado!");
            return;
        }

        tipoImovelRepository.remover(tipoImovel);
        System.out.println("Tipo de imóvel removido com sucesso!");
    }
}
