package missao;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class ControladorMissao {

    private final Scanner scanner;
    private final CriadorDeMissao criadorDeMissao;
    private final GerenciadorColisoes gerenciadorColisoes;
    private final GerenciadorInimigos gerenciadorInimigos;
    private final RenderizadorMapa renderizadorMapa;
    private final GerenciadorRanking gerenciadorRanking;
    private final Path rankingPath;

    public ControladorMissao(Scanner scanner, CriadorDeMissao criadorDeMissao, GerenciadorColisoes gerenciadorColisoes, GerenciadorInimigos gerenciadorInimigos, RenderizadorMapa renderizadorMapa, GerenciadorRanking gerenciadorRanking, Path rankingPath) {
        this.scanner = scanner;
        this.criadorDeMissao = criadorDeMissao;
        this.gerenciadorColisoes = gerenciadorColisoes;
        this.gerenciadorInimigos = gerenciadorInimigos;
        this.renderizadorMapa = renderizadorMapa;
        this.gerenciadorRanking = gerenciadorRanking;
        this.rankingPath = rankingPath;
    }

    public void iniciarMissao(List<RankingEntry> ranking) {
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

        while (opcaoDificuldade != 1 && opcaoDificuldade != 2 && opcaoDificuldade != 3) {
            System.out.print("\nInforme um valor valido para a dificuldade (1: FACIL / 2: MEDIO / 3: DIFICIL).\nDificuldade: ");
            opcaoDificuldade = scanner.nextInt();
        }

        Dificuldade dificuldade = null;

        switch (opcaoDificuldade) {
            case 1: dificuldade = Dificuldade.FACIL; break;
            case 2: dificuldade = Dificuldade.MEDIO; break;
            case 3: dificuldade = Dificuldade.DIFICIL; break;
        }

        scanner.nextLine();

        System.out.println("================================================================");
        System.out.println("Missão Marte Unifor — Console");
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
                case 'w':
                    if (nave.getY() > minY) {
                        nave.moveUp();
                        score--;
                    } else {
                        System.out.println("Você não pode passar desse ponto!");
                        if (dificuldade == Dificuldade.DIFICIL) score--;
                    }
                    break;

                case 's':
                    if (nave.getY() < maxY) {
                        nave.moveDown();
                        score--;
                    } else {
                        System.out.println("Você não pode passar desse ponto!");
                        if (dificuldade == Dificuldade.DIFICIL) score--;
                    }
                    break;

                case 'a':
                    if (nave.getX() > minX) {
                        nave.moveLeft();
                        score--;
                    } else {
                        System.out.println("Você não pode passar desse ponto!");
                        if (dificuldade == Dificuldade.DIFICIL) score--;
                    }
                    break;

                case 'd':
                    if (nave.getX() < maxX) {
                        nave.moveRight();
                        score--;
                    } else {
                        System.out.println("Você não pode passar desse ponto!");
                        if (dificuldade == Dificuldade.DIFICIL) score--;
                    }
                    break;

                case 'c':
                    Passageiro p = missao.passagemNaPosicao();

                    if (p == null) {
                        System.out.println("Nenhum passageiro nesta posição.");
                    } else {
                        boolean ok = missao.embarcarPassageiroNaPosicao();

                        if (ok) {
                            score += p.getPontos();
                            System.out.println(p.getTipo() + " embarcado. +" + p.getPontos() + " pontos!");
                        } else {
                            System.out.println("Nave cheia, não foi possível embarcar.");
                        }
                    }
                    break;

                case 'q':
                    running = false;
                    continue;

                default:
                    System.out.println("Comando desconhecido.");
                    continue;
            }

            gerenciadorInimigos.moverInimigos( missao.getInimigos(), minX, maxX, minY, maxY);

            ResultadoColisao resultadoColisao = gerenciadorColisoes.verificarColisao(missao);

            switch (resultadoColisao) {
                case ASTEROIDE:
                    System.out.println("\nColisão com Asteroide! Total de vidas restantes: " + nave.getVidas() + "/3.");
                    break;

                case INIMIGO:
                    System.out.println("\nColisão com Nave inimiga! Total de vidas restantes: " + nave.getVidas() + "/3.");
                    break;

                case NENHUMA:
                    break;

                default:
                    System.out.println("Um erro ocorreu durante o calculo de colisão, por favor reinicie o jogo.");
                    break;
            }

            if (nave.getVidas() == 0) {
                System.out.println("Missão abortada.");
                break;
            }

            if (score <= 0) {
                System.out.println("Pontuação zerada. Missão perdida.");
                break;
            }

            if (missao.todosEmbarcados() && nave.getX() == missao.getPlataformaPouso().getX() && nave.getY() == missao.getPlataformaPouso().getY()) {
                System.out.println("Missão concluída! A nave retornou à plataforma de pouso.");

                if (score > 0 && gerenciadorRanking.ehTop5(ranking, score)) {
                    gerenciadorRanking.adicionarPontuacao(ranking, pilotoNome, score, nave.getPassageiros().size(), nave.getPassageiros().size(), dificuldade, rankingPath);
                    System.out.println("Novo ranking salvo! Você está entre os 5 maiores pontuadores.");
                }

                break;
            }
        }
    }
}