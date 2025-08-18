import java.io.IOException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grafo grafo = new Grafo("grafo.txt", 10);

//        GrafoGenerator g = new GrafoGenerator(10000);
//        g.createGrafo(0.7);
//        g.salvaArquivo("grafo.txt");

        System.out.print("Arquivo criado como 'grafo.txt', deseja carregar o grafo na memória?(sim: 1, nao: 2) ");
        int loadGrafo = scanner.nextInt();

        switch (loadGrafo) {
            case 1:
                try {
                    grafo.lerDeArquivo();
                    grafo.gerarGrausVertices();
                    grafo.ordenarnumGrausVertices();
                    grafo.colorirGrafo();
                    grafo.printCores();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

//    public static void createGrafo(int k) {
//        Runtime runtime = Runtime.getRuntime();
//
//        // Força Garbage Collector antes da medição (opcional)
//        runtime.gc();
//
//        // Memória inicial
//        long memoriaInicial = runtime.totalMemory() - runtime.freeMemory();
//        System.out.println("Memória usada no início: " + memoriaInicial / 1024 / 1024 + " MB");
//
//        int[][] G = grafoGenerator.cycleC5();
//        for (int u = 0; u < k; u++) {
//            G = grafoGenerator.mycielski(G);
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
//    }
}