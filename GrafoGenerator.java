import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.*;

public class GrafoGenerator {
    private int numVertices;
    private Random random;

    public GrafoGenerator(int numVertices) {
        this.numVertices = numVertices;
        this.random = new Random();
    }

    public void criarGrafo(String arquivo, double densidade) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            for (int i = 0; i < numVertices; i++) {
                for (int j = i + 1; j < numVertices; j++) {
                    if (random.nextDouble() < densidade){
                        writer.write(i + " " + j);
                        writer.newLine();
                    }
                }
            }
        }
    }
}
