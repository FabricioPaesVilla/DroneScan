package exemplografo;

public class ExemploGrafo {
    public static void main(String[] args) {
        Grafo<String> grafo = new Grafo<String>();
        grafo.adicionarVertice("Paulo");
        grafo.adicionarVertice("Lorenzo");
        grafo.adicionarVertice("Cleuza");
        grafo.adicionarVertice("Kleber");
        grafo.adicionarVertice("Claudio");

        grafo.adicionaAresta(2.0,"Paulo","Lorenzo");
        grafo.adicionaAresta(3.0,"Lorenzo","Kleber");
        grafo.adicionaAresta(1.0,"Kleber","Cleuza");
        grafo.adicionaAresta(1.0,"Paulo","Cleuza");
        grafo.adicionaAresta(2.0,"Claudio","Lorenzo");
        grafo.adicionaAresta(3.0,"Claudio","Paulo");
    }
}
