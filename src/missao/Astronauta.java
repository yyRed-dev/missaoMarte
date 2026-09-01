package missao;

public class Astronauta extends Passageiro {
    public Astronauta (String nome, int x, int y) {
        super(nome, "Astronauta", x, y);
    }

    @Override
    public int getPontos() {
        return 20;
    }

    @Override
    public char getSimbolo() {
        return 'T';
    }
}