import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class grafoGenerator {
    private int[][] matrizAdj;
    private int vertices;

    public grafoGenerator(int[][] matrizAdj) {
        this.vertices = matrizAdj.length;
        this.matrizAdj = matrizAdj;
    }

    public static int[][] cycleC5() {
        int n = 5;
        int[][] C5 = new int[n][n];
        for (int i = 0; i < n; i++) {
            int j1 = (i + 1) % n;
            int j2 = (i + n - 1) % n;
            C5[i][j1] = C5[j1][i] = 1;
            C5[i][j2] = C5[j2][i] = 1;
        }
        return C5;
    }

    public static int[][] mycielski(int[][] G) {
        int n = G.length;
        int N = 2 * n + 1;
        int[][] M = new int[N][N];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                M[i][j] = G[i][j];


        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (G[i][j] == 1) {
                    M[i][n + j] = 1;
                    M[n + j][i] = 1;
                }

        int w = 2 * n;
        for (int j = 0; j < n; j++) {
            M[n + j][w] = 1;
            M[w][n + j] = 1;
        }

        return M;
    }

    public void salvaGrafoArquivo(String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    if (matrizAdj[i][j] == 1) {
                        writer.write(i + " " + j);
                        writer.newLine();
                    }
                }
            }
            System.out.println("Lista de arestas salva em: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }
}


