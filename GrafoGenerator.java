import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.*;

public class GrafoGenerator {
    private int V;
    private Random random = new Random();
    private List<Long> arestas;

    public GrafoGenerator(int V) {
        this.V = V;
        this.arestas = new ArrayList<>();
    }

    public void addAresta(int u, int v){
        if(u != v){
            long aresta = ((long) u << 32) | (v & 0xffffffffL);
            arestas.add(aresta);
        }
    }

    public void createGrafo(double densidade) {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i != j && random.nextDouble() < densidade) {
                    addAresta(i, j);
                }
            }
        }
    }

    public void salvaArquivo(String nomeArquivo){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (long aresta : arestas) {
                int u = (int) (aresta >> 32);
                int v = (int) aresta;
                writer.write(u + " " + v);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }
}
