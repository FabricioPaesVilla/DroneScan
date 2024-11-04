package exemplografo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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

    public void adicionaAresta(Double peso, TIPO dadoInicio, TIPO dadoFim){
        Vertice<TIPO> inicio = this.getVertice(dadoInicio);
        Vertice<TIPO> fim = this.getVertice(dadoFim);
        Aresta<TIPO> aresta = new Aresta<TIPO>(peso, inicio, fim);
        inicio.adicionaArestaSaida(aresta);
        fim.adicionaArestaEntrada(aresta);
        this.aresta.add(aresta);
    }

    public Vertice<TIPO> getVertice(TIPO dado){
        Vertice<TIPO> vertice = null;
        for (int i = 0; i < this.vertices.size(); i++) {
            if (this.vertices.get(i).getDado().equals(dado)){
                vertice = this.vertices.get(i);
                break;
            }
        }
        return vertice;
    }

    public String buscaCaminho(TIPO de, TIPO para){
	Vertice<TIPO> verticeInicio = getVertice(de);
    Vertice<TIPO> verticeFim = getVertice(para);

    if (verticeInicio == null || verticeFim == null) {
        return "Um ou ambos os vértices não existem.";
    }

    Queue<Vertice<TIPO>> fila = new LinkedList<>();
    ArrayList<Vertice<TIPO>> visitados = new ArrayList<>();
    ArrayList<Vertice<TIPO>> caminho = new ArrayList<>();

    fila.add(verticeInicio);
    visitados.add(verticeInicio);

    while (!fila.isEmpty()) {
        Vertice<TIPO> atual = fila.poll();
        caminho.add(atual);

        if (atual.equals(verticeFim)) {
            break; // Caminho encontrado
        }

        for (Aresta<TIPO> aresta : atual.getArestasSaida()) {
            Vertice<TIPO> proximo = aresta.getVerticeFim();
            if (!visitados.contains(proximo)) {
                fila.add(proximo);
                visitados.add(proximo);
            }
        }
    }

    if (caminho.contains(verticeFim)) {
        StringBuilder resultado = new StringBuilder("Caminho encontrado: ");
        for (Vertice<TIPO> v : caminho) {
            resultado.append(v.getDado()).append(" ");
        }
        return resultado.toString();
    } else {
        return "Caminho não encontrado.";
    }
    }
}
