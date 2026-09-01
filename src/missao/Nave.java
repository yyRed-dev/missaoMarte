package missao;

import java.util.ArrayList;
import java.util.List;

public class Nave {
    private String id;
    private int x;
    private int y;
    private int capacidade;
    private List<Passageiro> passageiros = new ArrayList<>();
    private int vidas;
    private final char simbolo = 'N';

    public Nave(String id, int capacidade) {
        this.id = id;
        this.capacidade = capacidade;
        this.x = 0;
        this.y = 0;
        this.vidas = 3;
    }

    public String getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getCapacidade() { return capacidade; }
    public List<Passageiro> getPassageiros() { return passageiros; }
    public int getVidas() { return vidas; }
    public char getSimbolo() { return simbolo; }

    public void moveUp() { y--; }
    public void moveDown() { y++; }
    public void moveLeft() { x--; }
    public void moveRight() { x++; }

    public void sofreuDano() { 
        if (this.vidas > 0) {
            vidas--;
        }
    }

    public boolean embarcar(Passageiro p) {
        if (p == null) {
            return false;
        }
        
        if (passageiros.size() >= capacidade) {
            return false;
        }

        passageiros.add(p);
        return true;
    }
}