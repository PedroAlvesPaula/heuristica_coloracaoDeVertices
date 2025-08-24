import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        // Parâmetros do nosso experimento
        int tamanhoInicial = 1;  // Começar com 1000 vértices
        int tamanhoFinal = 13;   // Ir até 10000 vértices
        int incremento = 1;      // Aumentar de 1000 em 1000

        // Agora você pode adicionar quantas densidades quiser aqui
        double[] densidades = {1};

        // Loop que itera sobre cada densidade do array
        for (double densidade : densidades) {
            String nomeArquivoCSV = String.format("resultados_densidade_%.1f.csv", densidade).replace(",", ".");

            System.out.printf("%n--- Iniciando experimento para densidade: %.1f ---%n", densidade);
            System.out.printf("Resultados serão salvos em: %s%n", nomeArquivoCSV);

            // Usando try-with-resources para garantir que o arquivo seja fechado
            // Este bloco agora está completamente dentro do loop de densidade
            try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivoCSV))) {

                // Escreve o cabeçalho do nosso arquivo CSV
                writer.println("vertices,tempo_ns");

                // Loop principal do experimento para os tamanhos de grafo
                for (int numVertices = tamanhoInicial; numVertices <= tamanhoFinal; numVertices += incremento) {
                    System.out.printf("Processando grafo com %d vértices...%n", numVertices);

                    // O nome do grafo temporário também deve ser único por densidade
                    String nomeArquivoGrafo = String.format("grafo_temp_d%.1f_v%d.txt", densidade, numVertices).replace(",",".");

                    // 1. Gerar um novo grafo com o tamanho atual
                    GrafoGenerator generator = new GrafoGenerator(numVertices);
                    generator.criarGrafo(nomeArquivoGrafo, densidade);

                    // 2. Carregar o grafo e medir o tempo de coloração
                    Grafo grafo = new Grafo(nomeArquivoGrafo, numVertices);
                    grafo.lerDeArquivo();

                    long startTime = System.nanoTime();
                    grafo.coloreGrafo(); // A função que estamos medindo
                    long endTime = System.nanoTime();

                    long durationInNanos = endTime - startTime;
                    String tempoFormatado = String.format("%.3e", (double)durationInNanos).replace(",", ".");

                    // 3. Salvar o resultado no arquivo CSV específico desta densidade
                    writer.println(numVertices + "," + tempoFormatado);
                }

                System.out.printf("--- Fim do experimento para densidade: %.1f ---%n", densidade);

            } catch (IOException e) {
                System.err.println("Ocorreu um erro ao escrever o arquivo CSV: " + e.getMessage());
                e.printStackTrace();
            }
        } // Fim do loop de densidades

        System.out.println("\nTodos os experimentos foram finalizados!");
    }
}

//import java.io.IOException;
//import java.util.Scanner;
//import java.util.concurrent.TimeUnit; // Import necessário para conversão (opcional, mas útil)
//
//public class Main {
//    public static void main(String[] args) throws IOException {
//        Scanner scanner = new Scanner(System.in);
//        Grafo grafo = new Grafo("grafo.txt", 10);
//
//        GrafoGenerator g = new GrafoGenerator(10);
//        g.criarGrafo("grafo.txt", 1);
//
//        try {
//            grafo.lerDeArquivo();
//            grafo.imprimirGrafo();
//
//            // --- INÍCIO DA MEDIÇÃO ---
//            long startTime = System.nanoTime();
//
//            grafo.coloreGrafo(); // A função que você quer medir
//
//            long endTime = System.nanoTime();
//            // --- FIM DA MEDIÇÃO ---
//
//            long durationInNanos = endTime - startTime;
//
//            // Convertendo para unidades mais legíveis
//            long durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNanos);
//            double durationInSeconds = durationInNanos / 1_000_000_000.0;
//
//            System.out.println("-------------------------------------------");
//            System.out.println("ANÁLISE DE DESEMPENHO - coloreGrafo()");
//            System.out.printf("Tempo de execução: %d nanossegundos%n", durationInNanos);
//            System.out.printf("Tempo de execução: %d milissegundos%n", durationInMillis);
//            System.out.printf("Tempo de execução: %.4f segundos%n", durationInSeconds);
//            System.out.println("-------------------------------------------");
//
//            // O resto do seu código
//            grafo.verificaColoracao();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//
//        }
//    }
//}

//import java.io.IOException;
//import java.util.Scanner;
//public class Main {
//    public static void main(String[] args) throws IOException {
//        Scanner scanner = new Scanner(System.in);
//        Grafo grafo = new Grafo("grafo.txt", 10000);
//
//        GrafoGenerator g = new GrafoGenerator(10000);
//        g.criarGrafo("grafo.txt", 1);
//
////        System.out.print("Arquivo criado como 'grafo.txt', deseja carregar o grafo na memória?(sim: 1, nao: 2) ");
////        int loadGrafo = scanner.nextInt();
//        int loadGrafo = 1;
//        switch (loadGrafo) {
//            case 1:
//                try {
//                    grafo.lerDeArquivo();
//                    grafo.coloreGrafo();
//                    grafo.verificaColoracao();
//                    System.out.println(grafo.contarCoresUnicas());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
//    }
//
//
////    public static void createGrafo(int k) {
////        Runtime runtime = Runtime.getRuntime();
////
////        // Força Garbage Collector antes da medição (opcional)
////        runtime.gc();
////
////        // Memória inicial
////        long memoriaInicial = runtime.totalMemory() - runtime.freeMemory();
////        System.out.println("Memória usada no início: " + memoriaInicial / 1024 / 1024 + " MB");
////
////        int[][] G = grafoGenerator.cycleC5();
////        for (int u = 0; u < k; u++) {
////            G = grafoGenerator.mycielski(G);
////
////            // Medição a cada iteração
////            long memoriaUsada = runtime.totalMemory() - runtime.freeMemory();
////            System.out.println("Iteração " + (u + 1) + " -> Memória usada: " + memoriaUsada / 1024 / 1024 + " MB");
////        }
////
////        grafoGenerator grafo = new grafoGenerator(G);
////        grafo.salvaGrafoArquivo("grafo.txt");
////
////        // Memória final
////        long memoriaFinal = runtime.totalMemory() - runtime.freeMemory();
////        System.out.println("Memória usada ao final: " + memoriaFinal / 1024 / 1024 + " MB");
////    }
//}
