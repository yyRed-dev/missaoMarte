package missao;

public class RankingEntry {

    private final String nome;
    private final int score;
    private final int tripulacao;

    public RankingEntry(String nome, int score, int tripulacao) {
        this.nome = nome;
        this.score = score;
        this.tripulacao = tripulacao;
    }

    public String getNome() {
        return nome;
    }

    public int getScore() {
        return score;
    }

    public int getTripulacao() {
        return tripulacao;
    }
}