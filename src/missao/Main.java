package missao;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        CriadorDeMissao criadorDeMissao = new CriadorDeMissao(random);
        GerenciadorColisoes gerenciadorColisoes = new GerenciadorColisoes();
        GerenciadorInimigos gerenciadorInimigos = new GerenciadorInimigos(random);
        RenderizadorMapa renderizadorMapa = new RenderizadorMapa();
        RepositorioRankingJson repositorioRanking = new RepositorioRankingJson();
        GerenciadorRanking gerenciadorRanking = new GerenciadorRanking(repositorioRanking);
        Path rankingPath = Paths.get("ranking.json");
        List<RankingEntry> ranking = gerenciadorRanking.carregar(rankingPath);
        Scanner scanner = new Scanner(System.in);

        MenuPrincipal menuPrincipal = new MenuPrincipal(scanner, gerenciadorRanking, rankingPath);
        ControladorMissao controladorMissao = new ControladorMissao(scanner, criadorDeMissao, gerenciadorColisoes, gerenciadorInimigos, renderizadorMapa, gerenciadorRanking, rankingPath);

        boolean programaExecutando = true;

        while (programaExecutando) {
            int opcao = menuPrincipal.executar();

            switch (opcao) {
                case 1: controladorMissao.iniciarMissao(ranking); ranking = gerenciadorRanking.carregar(rankingPath); break;
                case 2: menuPrincipal.mostrarRanking(); break;
                case 3: menuPrincipal.resetarRanking(); ranking = gerenciadorRanking.carregar(rankingPath); break;
                case 4: programaExecutando = false; break;
                default: System.out.println("Opção inválida."); break;
            }
        }
        scanner.close();
        System.out.println("Fim da execução.");
    }
}