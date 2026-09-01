package missao;

import java.util.List;
import java.util.Random;

public class GerenciadorInimigos {

    private Random random;

    public GerenciadorInimigos(Random random) {
        this.random = random;
    }

    public void moverInimigo(Inimigo inimigo, int minX, int maxX, int minY, int maxY) {
        int movimento = random.nextInt(4);

        switch (movimento) {
            case 0:
                if (inimigo.getY() > minY ) inimigo.moverCima();
                else moverInimigo(inimigo, minX, maxX, minY, maxY);
                break;

            case 1:
                if (inimigo.getX() < maxX) inimigo.moverDireita();
                else moverInimigo(inimigo, minX, maxX, minY, maxY);
                break;

            case 2:
                if (inimigo.getY() < maxY) inimigo.moverBaixo();
                else moverInimigo(inimigo, minX, maxX, minY, maxY);
                break;

            case 3:
                if (inimigo.getX() > minX) inimigo.moverEsquerda();
                else moverInimigo(inimigo, minX, maxX, minY, maxY);
                break;
        }
    }

    public void moverInimigos(List<Inimigo> inimigos, int minX, int maxX, int minY, int maxY) {
        for (Inimigo inimigo : inimigos) {
            moverInimigo(inimigo, minX, maxX, minY, maxY);
        }
    }

}