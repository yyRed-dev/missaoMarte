package missao;

public enum Dificuldade {

    FACIL(3, 20, 1, 2),
    MEDIO(5, 18, 2, 3),
    DIFICIL(7, 15, 3, 5);

    private final int capacidadeNave;
    private final int pontuacaoInicial;
    private final int quantidadeInimigos;
    private final int quantidadeAsteroides;

    Dificuldade(int capacidadeNave, int pontuacaoInicial, int quantidadeInimigos, int quantidadeAsteroides) {
        this.capacidadeNave = capacidadeNave;
        this.pontuacaoInicial = pontuacaoInicial;
        this.quantidadeInimigos = quantidadeInimigos;
        this.quantidadeAsteroides = quantidadeAsteroides;
    }

    public int getCapacidadeNave() { return capacidadeNave; }
    public int getPontuacaoInicial() { return pontuacaoInicial; }
    public int getQuantidadeInimigos() { return quantidadeInimigos; }
    public int getQuantidadeAsteroides() { return quantidadeAsteroides; }

}