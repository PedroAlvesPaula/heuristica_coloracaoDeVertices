import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Grafo {
    private Map<Integer, List<Integer>> adj = new HashMap<>();
    private String arquivo;

    public Grafo(String arquivo) {
        this.arquivo = arquivo;
    }

    public void addAresta(int u, int v) {
        this.adj.putIfAbsent(u, new ArrayList<>());
        this.adj.putIfAbsent(v, new ArrayList<>());

        this.adj.get(u).add(v);
        this.adj.get(v).add(u);
    }

    public void lerDeArquivo() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(this.arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    String[] partes = linha.split("\\s+");
                    int u = Integer.parseInt(partes[0]);
                    int v = Integer.parseInt(partes[1]);
                    this.addAresta(u, v);
                }
            }
        }
    }
    public void imprimirGrafo() {
        for (Map.Entry<Integer, List<Integer>> entry : this.adj.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
