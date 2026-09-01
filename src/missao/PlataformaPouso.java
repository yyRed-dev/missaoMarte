package missao;

public class PlataformaPouso {

    private final int x;
    private final int y;
    private final char simbolo = '_';

    public PlataformaPouso(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public char getSimbolo() { return simbolo; }
}