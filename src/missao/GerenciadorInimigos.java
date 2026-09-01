package missao;

import java.util.Random;

public class GerenciadorInimigos {

    private Random random;

    public GerenciadorInimigos(Random random) {
        this.random = random;
    }

    public void movimentar(Missao missao, int minX, int maxX, int minY, int maxY) {
        for (Inimigo inimigo : missao.getInimigos()) {
            int direcao = random.nextInt(5);

            switch (direcao) {
                case 0: moverCima(inimigo, minY); break;
                case 1: moverBaixo(inimigo, maxY); break;
                case 2: moverDireita(inimigo, maxX); break;
                case 3: moverEsquerda(inimigo, minX); break;
                case 4: break;
            }
        }
    }

    private void moverCima(Inimigo inimigo, int minY) {
        if (inimigo.getY() > minY) {
            inimigo.moverCima();
        }
    }

    private void moverBaixo(Inimigo inimigo, int maxY) {
        if (inimigo.getY() < maxY) {
            inimigo.moverBaixo();
        }
    }

    private void moverDireita(Inimigo inimigo, int maxX) {
        if (inimigo.getX() < maxX) {
            inimigo.moverDireita();
        }
    }

    private void moverEsquerda(Inimigo inimigo, int minX) {
        if (inimigo.getX() > minX) {
            inimigo.moverEsquerda();
        }
    }
}