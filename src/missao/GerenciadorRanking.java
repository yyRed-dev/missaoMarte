package missao;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class GerenciadorRanking {

    private final RepositorioRankingJson repositorio;

    public GerenciadorRanking(RepositorioRankingJson repositorio) {
        this.repositorio = repositorio;
    }

    public List<RankingEntry> carregar(Path path) { return repositorio.carregar(path); }

    public void adicionarPontuacao(List<RankingEntry> ranking, String nome, int score, int tripulacao, int passageirosResgatados, Dificuldade dificuldade, Path path) {
        ranking.add(new RankingEntry(nome, score, tripulacao, java.time.LocalDateTime.now(), passageirosResgatados, dificuldade));
        ranking.sort(Comparator.comparingInt(RankingEntry::getScore).reversed());

        if (ranking.size() > 5) {
            ranking.subList(5, ranking.size()).clear();
        }
        repositorio.salvar(path, ranking);
    }

    public boolean ehTop5(List<RankingEntry> ranking, int score) {
        if (ranking.size() < 5) return true;
        return score > ranking.get(ranking.size() - 1).getScore();
    }

    public void salvar(Path path, List<RankingEntry> ranking) { repositorio.salvar(path, ranking); }
    public void resetar(Path path) { repositorio.resetar(path); }

}