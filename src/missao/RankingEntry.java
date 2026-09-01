package missao;

import java.time.LocalDateTime;

public class RankingEntry {

    private final String nome;
    private final int score;
    private final int tripulacao;
    private final LocalDateTime dataHora;
    private final int passageirosResgatados;
    private final Dificuldade dificuldade;

    public RankingEntry(String nome, int score, int tripulacao,LocalDateTime dataHora, int passageirosResgatados, Dificuldade dificuldade) {
        this.nome = nome;
        this.score = score;
        this.tripulacao = tripulacao;
        this.dataHora = dataHora;
        this.passageirosResgatados = passageirosResgatados;
        this.dificuldade = dificuldade;
    }

    public String getNome() { return nome; }
    public int getScore() { return score;}
    public int getTripulacao() {  return tripulacao; }
    public LocalDateTime getDataHora() { return dataHora; }
    public int getPassageirosResgatados() { return passageirosResgatados; }
    public Dificuldade getDificuldade() { return dificuldade; }
}