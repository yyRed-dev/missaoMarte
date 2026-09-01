package missao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        System.out.print("Digite o nome do piloto: ");
        String pilotoNome = scanner.nextLine().trim();
        if (pilotoNome.isEmpty()) {
            pilotoNome = "Piloto Anônimo";
        }

        System.out.print("Tamanho do mapa (Grid): ");
        int tamanhoMapa = scanner.nextInt();
        while (tamanhoMapa < 5) {
            System.out.print("Tamanho minimo = 5. Tamanho do mapa: ");
            tamanhoMapa = scanner.nextInt();
        }
        int minX = -tamanhoMapa;
        int maxX = tamanhoMapa;
        int minY = -tamanhoMapa;
        int maxY = tamanhoMapa;

        System.out.println("Selecione a Dificuldade (1: FACIL / 2: MEDIO / 3: DIFICIL)");
        System.out.print("Dificuldade: ");
        int opcaoDificuldade = scanner.nextInt();

        while(opcaoDificuldade != 1 && opcaoDificuldade != 2 && opcaoDificuldade != 3) {
            System.out.print("\nInforme um valor valido para a dificuldade (1: FACIL / 2: MEDIO / 3: DIFICIL).\nDificuldade: ");
            opcaoDificuldade = scanner.nextInt();
        }

        Dificuldade dificuldade = null;

        switch(opcaoDificuldade) {
            case 1: dificuldade = Dificuldade.FACIL; break;
            case 2: dificuldade = Dificuldade.MEDIO; break;
            case 3: dificuldade = Dificuldade.DIFICIL; break;
            default: System.out.println("Erro durante a seleção de dificuldade. Por favor, reinicie o jogo e tente novamente."); break;
        }   

        System.out.println("================================================================");
        System.out.println("Missão Marte Unifor — Console");
        System.out.println();
        System.out.println("Ranking dos melhores pilotos:");
        if (ranking.isEmpty()) {
            System.out.println(" - Ainda não há pontuações registradas.");
        } else {
            for (int i = 0; i < Math.min(5, ranking.size()); i++) {
                RankingEntry entry = ranking.get(i);
                System.out.printf(" %d. %s: %d pontos - %d tripulantes%n",i + 1,entry.getNome(),entry.getScore(),entry.getTripulacao() );
            }
        }
        
        System.out.println();
        System.out.println("Bem-vindo à Missão Marte Unifor! Sua nave foi selecionada para uma expedição de resgate e pesquisa na superfície marciana.");
        System.out.println("Seu objetivo é localizar e embarcar todos os passageiros necessários para completar a missão antes que o seu tempo (pontuação) chegue a zero.");
        System.out.println();
        System.out.println("Objetivo:");
        System.out.println(" - Mover a nave pelo mapa");
        System.out.println(" - Encontrar e embarcar todos os passageiros");
        System.out.println(" - Evitar colisões com asteroides e naves alheias");
        System.out.println(" - Manter a pontuação acima de zero");
        System.out.println();
        System.out.println("Comandos:");
        System.out.println(" - w: mover para cima");
        System.out.println(" - s: mover para baixo");
        System.out.println(" - a: mover para a esquerda");
        System.out.println(" - d: mover para a direita");
        System.out.println(" - c: embarcar passageiro na posição atual");
        System.out.println(" - q: sair do jogo");
        System.out.println();
        System.out.println("Pontuação inicial: 20/18/15 pontos (varia com a Dificuldade). Cada movimento custa 1 ponto. Cada embarque vale pontos de acordo com o tipo do tripulante.");
        System.out.println();
        System.out.println("Pressione Enter para iniciar a missão...");
        scanner.nextLine();
        System.out.println("================================================================");

        boolean playAgain = true;
        while (playAgain) {
            int score = dificuldade.getPontuacaoInicial();
            Missao missao = criadorDeMissao.criar(minX, maxX, minY, maxY, dificuldade);
            Nave nave = missao.getNave();
            boolean running = true;

            while (running) {
                renderizadorMapa.desenhar(missao, minX, maxX, minY, maxY, score, pilotoNome);
                System.out.printf("Nave em (%d,%d) | Pontos: %d | Passageiros a bordo: %d | Passageiros restantes: %d\n",
                        nave.getX(), nave.getY(), score, nave.getPassageiros().size(), missao.todosEmbarcados() ? 0 : missao.getPassageiros().size());

                System.out.print("Para onde ir? ");
                String line = scanner.nextLine().trim().toLowerCase();
                if (line.isEmpty()) continue;
                char cmd = line.charAt(0);
                switch (cmd) {
                    case 'w': nave.moveUp(); score--; break;
                    case 's': nave.moveDown(); score--; break;
                    case 'a': nave.moveLeft(); score--; break;
                    case 'd': nave.moveRight(); score--; break;
                    case 'c': {
                        Passageiro p = missao.passagemNaPosicao();
                        if (p == null) {
                            System.out.println("Nenhum passageiro nesta posição.");
                        } else {
                            boolean ok = missao.embarcarPassageiroNaPosicao();
                            if (ok) {
                                score += p.getPontos();
                                System.out.println( p.getTipo() + " embarcado. +" +p.getPontos()+ " pontos!");
                            } else {
                                System.out.println("Nave cheia, não foi possível embarcar.");
                            }
                        }
                        break;
                    }
                    case 'q': running = false; break;
                    default: System.out.println("Comando desconhecido.");
                }
                gerenciadorInimigos.movimentar(missao, minX, maxX, minY, maxY);

                ResultadoColisao resultadoColisao = gerenciadorColisoes.verificarColisao(missao);

                switch (resultadoColisao) {
                    case ASTEROIDE: System.out.println("\nColisão com Asteroide! Total de vidas restantes: " + missao.getNave().getVidas() + "/3."); break;
                    case INIMIGO: System.out.println("\nColisão com Nave inimiga! Total de vidas restantes: " + missao.getNave().getVidas() + "/3."); break;
                    case NENHUMA:break;
                    default: System.out.println("Um erro ocorreu durante o calculo de colisão, por favor reinicie o jogo."); break;
                }

                if (missao.getNave().getVidas() == 0) {
                    System.out.println("Missão abortada.");
                    break;
                }

                if (score <= 0) {
                    System.out.println("Pontuação zerada. Missão perdida.");
                    break;
                }

                if (missao.todosEmbarcados()) {
                    System.out.println("Todos os passageiros embarcados! Missão concluída com sucesso.");
                    System.out.printf("Pontuação final: %d\n", score);
                    
                    if (score > 0 && gerenciadorRanking.ehTop5(ranking, score)) {

                        gerenciadorRanking.adicionarPontuacao( ranking, pilotoNome, score, nave.getPassageiros().size(), rankingPath );

                        System.out.println("Novo ranking salvo! Você está entre os 5 maiores pontuadores.");
                        }
                    }
                    break;
                }
            }

            if (!ranking.isEmpty()) {
                System.out.println();
                System.out.println("Ranking Top 5:");
                printRanking(ranking);
            } else {
                System.out.println();
                System.out.println("Ranking vazio. Seja o primeiro a marcar pontos!");
            }

            System.out.print("Deseja iniciar nova missão? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (resposta.equals("s") || resposta.equals("sim")) {
                System.out.println("Preparando nova missão...");
            } else {
                playAgain = false;
            }
        scanner.close();
        System.out.println("Fim da execução.");
    }
    

    private static void printRanking(List<RankingEntry> ranking) {
        int position = 1;
        for (RankingEntry entry : ranking) {
            System.out.printf("%d. %s - %d pontos - %d tripulantes%n",position++, entry.name, entry.score, entry.tripulacao);
        }
    }

}