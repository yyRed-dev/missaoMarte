package missao;

import java.util.Random;

public class CriadorDeMissao {

    private Random random;

    public CriadorDeMissao(Random random) {
        this.random = random;
    }

    public Missao criar(int minX, int maxX, int minY, int maxY, Dificuldade dificuldade) {
        Nave nave = new Nave("A-1", dificuldade.getCapacidadeNave());
        Missao missao = new Missao(nave);
        criarPassageiros(missao, minX, maxX, minY, maxY, dificuldade);
        criarAsteroides(missao, minX, maxX, minY, maxY, dificuldade);
        criarInimigos(missao, minX, maxX, minY, maxY, dificuldade);

        return missao;
    }

    private void criarPassageiros(Missao missao, int minX, int maxX, int minY, int maxY, Dificuldade dificuldade) {
        while (missao.getPassageiros().size() < dificuldade.getCapacidadeNave()) {
            int x = gerarX(minX, maxX);
            int y = gerarY(minY, maxY);

            if (x == missao.getNave().getX() && y == missao.getNave().getY()) {
                continue;
            }

            if (posicaoOcupada(missao, x, y)) {
                continue;
            }

            int quantidade = missao.getPassageiros().size();

            if (quantidade == 0) {
                missao.addPassageiro(new Professor("Dr. Silva", x, y));
            } else if (quantidade == 1) {
                missao.addPassageiro(new Engenheiro("Eng. Rosa", x, y));
            } else {
                missao.addPassageiro(new Astronauta("Astronauta", x, y));
            }
        }
    }

    private void criarAsteroides(Missao missao, int minX, int maxX, int minY, int maxY, Dificuldade dificuldade) {
        while (missao.getAsteroides().size() < dificuldade.getQuantidadeAsteroides()) {
            int x = gerarX(minX, maxX);
            int y = gerarY(minY, maxY);

            if (x == missao.getNave().getX() && y == missao.getNave().getY()) {
                continue;
            }

            if (posicaoOcupada(missao, x, y)) {
                continue;
            }
            missao.addAsteroide(new Asteroide(x, y));
        }
    }

    private void criarInimigos(Missao missao, int minX, int maxX, int minY, int maxY, Dificuldade dificuldade) {
        while (missao.getInimigos().size() < dificuldade.getQuantidadeInimigos()) {
            int x = gerarX(minX, maxX);
            int y = gerarY(minY, maxY);

            if (x == missao.getNave().getX() && y == missao.getNave().getY()) {
                continue;
            }

            if (posicaoOcupada(missao, x, y)) {
                continue;
            }

            missao.addInimigo(new Inimigo(x, y));
        }
    }

    private int gerarX(int minX, int maxX) {
        return random.nextInt(maxX - minX + 1) + minX;
    }

    private int gerarY(int minY, int maxY) {
        return random.nextInt(maxY - minY + 1) + minY;
    }

    private boolean posicaoOcupada(Missao missao, int x, int y) {
        for (Passageiro p : missao.getPassageiros()) {
            if (p.getX() == x && p.getY() == y) {
                return true;
            }
        }

        for (Asteroide a : missao.getAsteroides()) {
            if (a.getX() == x && a.getY() == y) {
                return true;
            }
        }

        for (Inimigo i : missao.getInimigos()) {
            if (i.getX() == x && i.getY() == y) {
                return true;
            }
        }
        return false;
    }
}