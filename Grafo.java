import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Grafo {
    private Map<Integer, List<Integer>> grafo = new HashMap<>();
    private String arquivo;
    private int numVertices;
    private int[] listaCor;
    private List<Integer> coresUsadas = new ArrayList<>();
    private int numCores = 0;

    public Grafo(String arquivo, int numVertices) {
        this.arquivo = arquivo;
        this.numVertices = numVertices;
        this.listaCor = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            this.listaCor[i] = -1;
        }
    }

    private void addAresta(int u, int v) {
        this.grafo.putIfAbsent(u, new ArrayList<>());
        this.grafo.putIfAbsent(v, new ArrayList<>());

        this.grafo.get(u).add(v);
        this.grafo.get(v).add(u);
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
    public void coloreGrafo(){
        for (int i = 1; i <= this.numVertices; i++) {
            if(this.coloreVertice(i, 0)) {
                System.out.println("Numero cromatico: " + i);
                break;
            }
        }
    }
    public boolean coloreVertice(int k, int v){
        if(v == this.numVertices) return true;

        for(int i = 1; i <= k; i++){
            if(this.verifica(v, i)){
                this.listaCor[v] = i;

                if(this.coloreVertice(k, v+1)){
                    return true;
                }

                this.listaCor[v] = -1;
            }
        }
        return false;
    }
    public boolean verifica(int v, int c){
        if(this.grafo.get(v) == null) return true;
        for(Integer u: this.grafo.get(v)){
            if(this.listaCor[u] == c){
                return false;
            }
        }
        return true;
    }
    public void imprimirGrafo() {
        for (Map.Entry<Integer, List<Integer>> entry : this.grafo.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
    public void imprimeCores(){
        System.out.println("Cores:");
        for (int i = 0; i < this.numVertices; i++) {
            System.out.println(i + " -> " + this.listaCor[i]);
        }
        System.out.println("Total cores:" + this.contarCoresUnicas());
    }
    public int contarCoresUnicas() {
        Set<Integer> coresUnicas = new HashSet<>();
        for (int cor : this.listaCor) {
            if (cor != -1) {
                coresUnicas.add(cor);
            }
        }
        return  coresUnicas.size();
    }
    public boolean verificaColoracao() {
        for (Integer u : grafo.keySet()) {
            int corDeU = listaCor[u];

            List<Integer> vizinhos = grafo.get(u);
            if (vizinhos == null) continue;

            for (Integer v : vizinhos) {
                int corDeV = listaCor[(v)];

                if (corDeU == corDeV) {
                    System.out.println("VIOLAÇÃO: Vértice " + u + " e vizinho " + v + " ambos têm a cor " + corDeU);
                    return false;
                }
            }
        }
        return true;
    }
}
