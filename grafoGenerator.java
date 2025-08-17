import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.*;

public class grafoGenerator {
    private int V;
    private List<int[]> edges;
    private Random random = new Random();

    public grafoGenerator(int V) {
        this.V = V;
        this.edges = new ArrayList<>();
    }

    public void addEdge(int u, int v){
        if(u != v){
            edges.add(new int[]{u, v});
        }
    }

    public void createRandomEdge(List<Integer> vertice, double densidade ){
        for (int i = 0; i < vertice.size(); i++){
            for(int j = 0; j < vertice.size(); j++){
                if(i != j && random.nextDouble() < densidade){
                    addEdge(vertice.get(i), vertice.get(j));
                }
            }
        }
    }

    public void conectaGargalo(List<Integer> A, List<Integer> B, int numEdge) {
        for (int i = 0; i < numEdge; i++) {
            int u = A.get(random.nextInt(A.size()));
            int v = B.get(random.nextInt(B.size()));
            addEdge(u, v);
            addEdge(v, u);
        }
    }

    public static grafoGenerator geraInstancia(
            int V, int numComunidades, double densidade, int gargaloPorLigacao) {

        grafoGenerator g = new grafoGenerator(V);
        List<List<Integer>> comunidades = new ArrayList<>();

        int tamBase = V / numComunidades;
        int resto = V % numComunidades;
        int atual = 0;

        for (int c = 0; c < numComunidades; c++) {
            int tam = tamBase + (c < resto ? 1 : 0);
            List<Integer> bloco = new ArrayList<>();
            for (int i = 0; i < tam; i++) {
                bloco.add(atual++);
            }
            comunidades.add(bloco);
        }

        for (List<Integer> bloco : comunidades) {
            g.createRandomEdge(bloco, densidade);
        }

        for (int i = 0; i < comunidades.size() - 1; i++) {
            g.conectaGargalo(comunidades.get(i), comunidades.get(i + 1), gargaloPorLigacao);
        }

        return g;
    }

    public void salvaEmArquivo(String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (int[] edge : edges) {
                writer.write(edge[0] + " " + edge[1]);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        grafoGenerator g = grafoGenerator.geraInstancia(
                100000, 7000, 1, 4000);

        g.salvaEmArquivo("grafo.txt");
        System.out.println("Grafo salvo em grafo.txt!");
    }

}
//    private int[][] matrizAdj;
//    private int vertices;
//
//    public grafoGenerator(int[][] matrizAdj) {
//        this.vertices = matrizAdj.length;
//        this.matrizAdj = matrizAdj;
//    }
//
//    public static int[][] cycleC5() {
//        int n = 5;
//        int[][] C5 = new int[n][n];
//        for (int i = 0; i < n; i++) {
//            int j1 = (i + 1) % n;
//            int j2 = (i + n - 1) % n;
//            C5[i][j1] = C5[j1][i] = 1;
//            C5[i][j2] = C5[j2][i] = 1;
//        }
//        return C5;
//    }
//
//    public static int[][] mycielski(int[][] G) {
//        int n = G.length;
//        int N = 2 * n + 1;
//        int[][] M = new int[N][N];
//
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                M[i][j] = G[i][j];
//
//
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                if (G[i][j] == 1) {
//                    M[i][n + j] = 1;
//                    M[n + j][i] = 1;
//                }
//
//        int w = 2 * n;
//        for (int j = 0; j < n; j++) {
//            M[n + j][w] = 1;
//            M[w][n + j] = 1;
//        }
//
//        return M;
//    }
//
//    public void salvaGrafoArquivo(String nomeArquivo) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
//            for (int i = 0; i < vertices; i++) {
//                for (int j = 0; j < vertices; j++) {
//                    if (matrizAdj[i][j] == 1) {
//                        writer.write(i + " " + j);
//                        writer.newLine();
//                    }
//                }
//            }
//            System.out.println("Lista de arestas salva em: " + nomeArquivo);
//        } catch (IOException e) {
//            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
//        }
//    }
//
//    public static void main(String[] args) {
//        Runtime runtime = Runtime.getRuntime();
//
//        // Força Garbage Collector antes da medição (opcional)
//        runtime.gc();
//
//        // Memória inicial
//        long memoriaInicial = runtime.totalMemory() - runtime.freeMemory();
//        System.out.println("Memória usada no início: " + memoriaInicial / 1024 / 1024 + " MB");
//
//        int[][] G = cycleC5();
//        int k = 13;
//        for (int u = 0; u < k; u++) {
//            G = mycielski(G);
//
//            // Medição a cada iteração
//            long memoriaUsada = runtime.totalMemory() - runtime.freeMemory();
//            System.out.println("Iteração " + (u + 1) + " -> Memória usada: " + memoriaUsada / 1024 / 1024 + " MB");
//        }
//
//        grafoGenerator grafo = new grafoGenerator(G);
//        grafo.salvaGrafoArquivo("grafo.txt");
//
//        // Memória final
//        long memoriaFinal = runtime.totalMemory() - runtime.freeMemory();
//        System.out.println("Memória usada ao final: " + memoriaFinal / 1024 / 1024 + " MB");
////        int[][] G = cycleC5();
////        int k = 12;
////        for (int u = 0; u < k; u++) {
////            G = mycielski(G);
////        }
////        grafoGenerator grafo = new grafoGenerator(G);
////
////        grafo.salvaGrafoArquivo("grafo.txt");
//    }
//}