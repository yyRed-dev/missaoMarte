package missao;

public class Passageiro {
    private String nome;
    private String tipo;
    private int x;
    private int y;
    private int pontos;

    public Passageiro(String nome, String tipo, int x, int y) {
        this.nome = nome;
        this.tipo = tipo;
        this.x = x;
        this.y = y;
        this.pontos = pontos;
    }

    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public int getX() { return x; }
    public int getY() { return y; }
    public void setPontos(int pontos) { this.pontos = pontos; }
    public int getPontos() { return pontos; }
}