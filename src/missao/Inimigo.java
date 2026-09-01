package missao;

public class Inimigo {
    private int x;
    private int y;
    private final char simbolo = 'I';

    public Inimigo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public void moverCima() { y--; }
    public void moverBaixo() { y++; }
    public void moverDireita() { x++; }
    public void moverEsquerda() { x--; }

    public char getSimbolo() { return simbolo; }

    public boolean colideCom(Nave n) {
        return n.getX() == x && n.getY() == y;
    }

}