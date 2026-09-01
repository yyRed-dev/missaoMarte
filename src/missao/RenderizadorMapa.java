package missao;

public class RenderizadorMapa {

    public void desenhar(Missao missao, int minX, int maxX, int minY, int maxY, int score, String pilotoNome) {
        System.out.println();
        System.out.printf("Mapa da Missão (Pontos: %d) - Piloto: %s%n", score, pilotoNome);

        System.out.print("    ");
        for (int x = minX; x <= maxX; x++) {
            System.out.printf(" %2d", x);
        }

        System.out.println();

        System.out.print("    ");
        for (int x = minX; x <= maxX; x++) {
            System.out.print(" __");
        }
        System.out.println();

        for (int y = minY; y <= maxY; y++) {
            System.out.printf("%3d|", y);

            for (int x = minX; x <= maxX; x++) {
                char symbol = obterSimbolo(missao, x, y);
                System.out.printf(" %2c", symbol);
            }
            System.out.println();
        }

        System.out.println("Legenda: N=Nave, P=Professor, E=Engenheiro, T=Astronauta, A=Asteroide, I=Inimigo, .=Vazio");
        System.out.println("Resumo de comandos: w(cima)/s(baixo)/a(esquerda)/d(direita) mover, c embarcar, q sair");
        System.out.println("Passageiros restantes:");

        for (Passageiro p : missao.getPassageiros()) {
            System.out.printf(" - %s (%s) em (%d,%d)%n",
                    p.getNome(), p.getTipo(), p.getX(), p.getY());
        }
        System.out.println();
    }

    private char obterSimbolo(Missao missao, int x, int y) {

        if (missao.getNave().getX() == x && missao.getNave().getY() == y) {
            return missao.getNave().getSimbolo();
        }

        if (missao.getPlataformaPouso().getX() == x && missao.getPlataformaPouso().getY() == y) {
            return missao.getPlataformaPouso().getSimbolo();
        }

        for (Passageiro p : missao.getPassageiros()) {
            if (p.getX() == x && p.getY() == y) {
                return p.getSimbolo();
            }
        }

        for (Asteroide a : missao.getAsteroides()) {
            if (a.getX() == x && a.getY() == y) {
                return a.getSimbolo();
            }
        }

        for (Inimigo i : missao.getInimigos()) {
            if (i.getX() == x && i.getY() == y) {
                return i.getSimbolo();
            }
        }

        return '.';
    }
}