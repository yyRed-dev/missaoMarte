package missao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RepositorioRankingJson {

    public List<RankingEntry> carregar(Path path) {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        try {
            String json = new String( Files.readAllBytes(path), StandardCharsets.UTF_8 ).trim();
            return interpretarJson(json);

        } catch (IOException e) {
            System.out.println("Não foi possível carregar o ranking: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void salvar(Path path, List<RankingEntry> ranking) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");

        for (int i = 0; i < ranking.size(); i++) {
            RankingEntry entry = ranking.get(i);

            builder.append("{\"name\":\"")
                    .append(entry.getNome().replace("\"", "\\\""))
                    .append("\",\"score\":")
                    .append(entry.getScore())
                    .append(",\"tripulacao\":")
                    .append(entry.getTripulacao())
                    .append("}");
            if (i < ranking.size() - 1) {
                builder.append(",");
            }
        }

        builder.append("]");

        try {
            Files.write(path,builder.toString().getBytes(StandardCharsets.UTF_8) );

        } catch (IOException e) {
            System.out.println("Não foi possível salvar o ranking: " + e.getMessage());
        }
    }

    private List<RankingEntry> interpretarJson(String json) {
        List<RankingEntry> ranking = new ArrayList<>();

        if (json.isEmpty() || json.equals("[]")) {
            return ranking;
        }
        json = json.trim();

        if (json.startsWith("[")) {
            json = json.substring(1);
        }

        if (json.endsWith("]")) {
            json = json.substring(0, json.length() - 1);
        }

        int index = 0;
        while (index < json.length()) {
            int start = json.indexOf('{', index);

            if (start < 0) {
                break;
            }
            int end = json.indexOf('}', start);

            if (end < 0) {
                break;
            }
            String object = json.substring(start + 1, end);

            String nome = null;
            Integer score = null;
            Integer tripulacao = null;

            for (String part : object.split(",")) {

                String[] pair = part.split(":", 2);
                if (pair.length != 2) {
                    continue;
                }

                String key = pair[0].trim().replaceAll("\"", "");
                String value = pair[1].trim();

                if (key.equals("name")) {

                    if (value.startsWith("\"") && value.endsWith("\"")) {
                        nome = value.substring(1, value.length() - 1)
                                .replace("\\\"", "\"");
                    }

                } else if (key.equals("score")) {
                    try {
                        score = Integer.parseInt(value);
                    } catch (NumberFormatException ignored) {
                    }

                } else if (key.equals("tripulacao")) {
                    try {
                        tripulacao = Integer.parseInt(value);
                    } catch (NumberFormatException ignored) {
                    }
                }
            }

            if (nome != null && score != null) {
                ranking.add( new RankingEntry(nome,score,tripulacao != null ? tripulacao : 0) );
            }
            index = end + 1;
        }
        ranking.sort(Comparator.comparingInt(RankingEntry::getScore).reversed() );
        return ranking;
    }
}