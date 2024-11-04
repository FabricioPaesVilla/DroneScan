package exemplografo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Grafo<TIPO> {
    private ArrayList<Vertice<TIPO>> vertices;
    private ArrayList<Aresta<TIPO>> aresta;

    public Grafo() {
        this.vertices = new ArrayList<Vertice<TIPO>>();
        this.aresta = new ArrayList<Aresta<TIPO>>();
    }

    public void adicionarVertice(TIPO dado) {
        Vertice<TIPO> novoVertice = new Vertice<TIPO>(dado);
        this.vertices.add(novoVertice);
    }

    public void adicionaAresta(Double peso, TIPO dadoInicio, TIPO dadoFim) {
        Vertice<TIPO> inicio = this.getVertice(dadoInicio);
        Vertice<TIPO> fim = this.getVertice(dadoFim);
        Aresta<TIPO> aresta = new Aresta<TIPO>(peso, inicio, fim);
        inicio.adicionaArestaSaida(aresta);
        fim.adicionaArestaEntrada(aresta);
        this.aresta.add(aresta);
    }

    public Vertice<TIPO> getVertice(TIPO dado) {
        Vertice<TIPO> vertice = null;
        for (int i = 0; i < this.vertices.size(); i++) {
            if (this.vertices.get(i).getDado().equals(dado)) {
                vertice = this.vertices.get(i);
                break;
            }
        }
        return vertice;
    }

    public String buscaCaminho(TIPO de, TIPO para) {
        //-----------------------------------------------------------------------------
        Vertice<TIPO> verticeInicial = getVertice(de);
        Vertice<TIPO> verticeDestino = getVertice(para);

        if (verticeInicial == null || verticeDestino == null) {
            return "Um ou ambos os vértices não existem.";
        }

        // Map para armazenar a distância mínima de cada vértice
        Map<Vertice<TIPO>, Double> distancias = new HashMap<>();
        // Map para armazenar o vértice anterior em cada caminho
        Map<Vertice<TIPO>, Vertice<TIPO>> predecessores = new HashMap<>();
        // Conjunto de vértices não visitados
        Set<Vertice<TIPO>> visitados = new HashSet<>();

        // Inicializa as distâncias
        for (Vertice<TIPO> vertice : vertices) {
            distancias.put(vertice, Double.POSITIVE_INFINITY);
        }
        distancias.put(verticeInicial, 0.0);

        // PriorityQueue para os vértices a serem explorados
        PriorityQueue<Vertice<TIPO>> priorityQueue = new PriorityQueue<>((v1, v2) ->
                Double.compare(distancias.get(v1), distancias.get(v2))
        );
        priorityQueue.add(verticeInicial);

        while (!priorityQueue.isEmpty()) {
            Vertice<TIPO> verticeAtual = priorityQueue.poll();

            // Se já visitamos este vértice, ignoramos
            if (visitados.contains(verticeAtual)) {
                continue;
            }

            visitados.add(verticeAtual);

            // Se chegamos ao vértice destino, podemos parar
            if (verticeAtual.equals(verticeDestino)) {
                break;
            }

            // Explora os vizinhos
            for (Aresta<TIPO> aresta : verticeAtual.getArestasSaida()) {
                Vertice<TIPO> vizinho = aresta.getFim();
                double novaDistancia = distancias.get(verticeAtual) + aresta.getPeso();

                // Se encontramos um caminho mais curto para o vizinho
                if (novaDistancia < distancias.get(vizinho)) {
                    distancias.put(vizinho, novaDistancia);
                    predecessores.put(vizinho, verticeAtual);
                    priorityQueue.add(vizinho);
                }
            }
        }

        // Reconstrói o caminho
        if (!visitados.contains(verticeDestino)) {
            return "Caminho não encontrado.";
        }

        StringBuilder caminho = new StringBuilder();
        for (Vertice<TIPO> v = verticeDestino; v != null; v = predecessores.get(v)) {
            caminho.insert(0, v.getDado() + " ");
        }

        return caminho.toString().trim();
        //-----------------------------------------------------------------------------
    }
}
