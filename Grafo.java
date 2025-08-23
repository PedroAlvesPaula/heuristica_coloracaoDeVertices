import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Grafo {
    private Map<Integer, List<Integer>> grafo = new HashMap<>();
    private String arquivo;
    private int numVertices;
    private int[] listaCor;
    private List<int[]> numGrauVertices = new ArrayList<>();

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
    public void imprimirGrafo() {
        for (Map.Entry<Integer, List<Integer>> entry : this.grafo.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
    public void gerarGrausVertices(){
        this.grafo.forEach((u, v) -> {
            this.numGrauVertices.add(new int[]{u, v.toArray().length});
        });
    }
    public void ordenarnumGrausVertices(){
        this.numGrauVertices.sort((a, b) -> {
            int c = Integer.compare(a[1], b[1]);
            if (c != 0) return c;
            return Integer.compare(a[0], b[0]);
        });
        for (int[] par : numGrauVertices) {
            System.out.println("(" + par[0] + ", " + par[1] + ")");
        }
    }
    public void colorirGrafo() {
        int v = this.numGrauVertices.get(this.numGrauVertices.size() - 1)[0];
        this.listaCor[v] = 1;
//        int[] coresUsadas = new int[this.numVertices];
//        for (int i = 0; i < this.numVertices; i++) {
//            coresUsadas[i] = -1;
//        }
//        coresUsadas[0] = 1;

        for(int i = 2; i <= this.numVertices; i++) {
            v = this.numGrauVertices.get(this.numGrauVertices.size() - i)[0];
            List<Integer> listVizinho = this.grafo.get(v);
            int[] coresVizinhos = new int[listVizinho.size()];
            for (int j = 0; j < listVizinho.size(); j++) {
                coresVizinhos[j] = -1;
            }

            int vizinho = 0;
            for (int integer : listVizinho) {
                if (listaCor[integer] != -1) {
                    if (listaCor[integer] > coresVizinhos[vizinho]) {
                        coresVizinhos[vizinho] = listaCor[integer];
                    }
                }
                vizinho++;
            }

            Arrays.sort(coresVizinhos);

            for (int j = 0; j < coresVizinhos.length / 2; j++) {
                int temp = coresVizinhos[j];
                coresVizinhos[j] = coresVizinhos[coresVizinhos.length - 1 - j];
                coresVizinhos[coresVizinhos.length - 1 - j] = temp;
            }

            Collections.reverse(listVizinho);

            for (int j = 0; j < listVizinho.size(); j++) {
                if (listaCor[listVizinho.get(j)] == -1) continue;
                boolean usacCor = false;
                for (int k = 0; k < coresVizinhos.length; k++) {
                    if (listaCor[listVizinho.get(j)] == coresVizinhos[k]) {
                        usacCor = true;
                    }
                }
                if (!usacCor) {
                    listaCor[v] = listaCor[listVizinho.get(j)];
                } else {
                    listaCor[v] = listaCor[listVizinho.get(j)] + 1;
                    int maiorCor = 0;
                    for (int cor : listaCor) {
                        if (cor > maiorCor) {
                            maiorCor = cor;
                        }
                    }
                    if (maiorCor == listaCor[v] && coresVizinhos[j] != maiorCor) break;
                }
            }
        }
    }
    public void printCores() {
        System.out.println("Imprimindo Cores:\n");
        for (int j : this.listaCor) {
            System.out.println(j);
        }
    }
    public boolean verificaColoracao() {
        for (Integer u : grafo.keySet()) {
            int corDeU = listaCor[u];

            List<Integer> vizinhos = grafo.get(u);
            if (vizinhos == null) continue;

            for (Integer v : vizinhos) {
                int corDeV = listaCor[v];

                if (corDeU == corDeV) {
                    System.out.println("VIOLAÇÃO: Vértice " + u + " e vizinho " + v + " ambos têm a cor " + corDeU);
                    return false;
                }
            }
        }
        return true;
    }
}
