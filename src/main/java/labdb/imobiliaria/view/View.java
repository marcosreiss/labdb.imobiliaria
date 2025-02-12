package labdb.imobiliaria.view;

import java.util.Scanner;

public class View {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n### Menu Principal ###");
            System.out.println("1 - Aluguel");
            System.out.println("2 - Cliente");
            System.out.println("3 - Imóvel");
            System.out.println("4 - Locação");
            System.out.println("5 - Profissional");
            System.out.println("6 - Serviço do Imóvel");
            System.out.println("7 - Tipo de Imóvel");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1: menuAluguel(); break;
                case 2: menuCliente(); break;
                case 3: menuImovel(); break;
                case 4: menuLocacao(); break;
                case 5: menuProfissional(); break;
                case 6: menuServicoImovel(); break;
                case 7: menuTipoImovel(); break;
                case 0:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void menuAluguel() {
        System.out.println("\n### Menu Aluguel ###");
        menuCrud();
    }

    private static void menuCliente() {
        System.out.println("\n### Menu Cliente ###");
        menuCrud();
    }

    private static void menuImovel() {
        System.out.println("\n### Menu Imóvel ###");
        menuCrud();
    }

    private static void menuLocacao() {
        System.out.println("\n### Menu Locação ###");
        menuCrud();
    }

    private static void menuProfissional() {
        System.out.println("\n### Menu Profissional ###");
        menuCrud();
    }

    private static void menuServicoImovel() {
        System.out.println("\n### Menu Serviço do Imóvel ###");
        menuCrud();
    }

    private static void menuTipoImovel() {
        System.out.println("\n### Menu Tipo de Imóvel ###");
        menuCrud();
    }

    private static void menuCrud() {
        while (true) {
            System.out.println("1 - Criar");
            System.out.println("2 - Ler");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Remover");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1: System.out.println("Criando..."); break;
                case 2: System.out.println("Lendo..."); break;
                case 3: System.out.println("Atualizando..."); break;
                case 4: System.out.println("Removendo..."); break;
                case 0: return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
