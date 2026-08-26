package missao;

public class Inimigo {
    private int x;
    private int y;

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
    public void moverDireita() { x--; }
    public void moverEsquerda() { x++; }

    public boolean colideCom(Nave n) {
        return n.getX() == x && n.getY() == y;
    }

    public void moverAleatorio(int x) {
        switch(x) {
            case 1: this.moverCima(); break;
            case 2: this.moverDireita(); break;
            case 3: this.moverBaixo(); break;
            case 4: this.moverEsquerda(); break;
            default:
                System.out.println("Um erro ocorreu com o movimento do Inimigo, por favor reinicie o jogo.");
                break;
        }
    }

}