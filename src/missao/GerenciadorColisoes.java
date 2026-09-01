package missao;

public class GerenciadorColisoes {

    public ResultadoColisao verificarColisao(Missao missao) {
        Nave nave = missao.getNave();

        for (Asteroide asteroide : missao.getAsteroides()) {
            if (asteroide.colideCom(nave)) {
                nave.sofreuDano();
                return ResultadoColisao.ASTEROIDE;
            }
        }

        for (Inimigo inimigo : missao.getInimigos()) {
            if (inimigo.colideCom(nave)) {
                nave.sofreuDano();
                return ResultadoColisao.INIMIGO;
            }
        }
        return ResultadoColisao.NENHUMA;
    }
    
}