package missao;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {

    private final Scanner scanner;
    private final GerenciadorRanking gerenciadorRanking;
    private final Path rankingPath;

    public MenuPrincipal(Scanner scanner, GerenciadorRanking gerenciadorRanking, Path rankingPath) {
        this.scanner = scanner;
        this.gerenciadorRanking = gerenciadorRanking;
        this.rankingPath = rankingPath;
    }

    public int executar() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("        MISSÃO MARTE UNIFOR");
        System.out.println("========================================");
        System.out.println("1 - Iniciar missão");
        System.out.println("2 - Ver ranking");
        System.out.println("3 - Resetar ranking");
        System.out.println("4 - Sair");
        System.out.print("Escolha: ");

        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Informe uma opção válida: ");
        }

        int opcao = scanner.nextInt();
        scanner.nextLine();

        return opcao;
    }

    public void mostrarRanking() {
        List<RankingEntry> ranking = gerenciadorRanking.carregar(rankingPath);

        System.out.println();
        System.out.println("========== RANKING TOP 5 ==========");

        if (ranking.isEmpty()) {
            System.out.println("Ranking vazio. Seja o primeiro a marcar pontos!");
            return;
        }

        for (int i = 0; i < Math.min(5, ranking.size()); i++) {
            RankingEntry entry = ranking.get(i);
            System.out.printf("%d. %s - %d pontos - %d tripulantes%n", i + 1, entry.getNome(), entry.getScore(), entry.getTripulacao());
        }
    }

    public void resetarRanking() {
        System.out.print("Tem certeza que deseja resetar o ranking? (s/n): ");
        String resposta = scanner.nextLine().trim().toLowerCase();

        if (resposta.equals("s") || resposta.equals("sim")) {
            gerenciadorRanking.resetar(rankingPath);
            System.out.println("Ranking resetado com sucesso.");
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}