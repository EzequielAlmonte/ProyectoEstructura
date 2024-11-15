package logico;

public class Main {

	public static void main(String[] args) {
		Grafo grafo = new Grafo(5);

        grafo.agregarRuta(1, 2, 10, 1);
        grafo.agregarRuta(1, 3, 15, 1);
        grafo.agregarRuta(2, 4, 12, 0);
        grafo.agregarRuta(3, 4, 10, 1);
        grafo.agregarRuta(4, 5, 5, 1);

        Dijkstra dijkstra = new Dijkstra(grafo);
        int[] distancias = dijkstra.calcularDistancias(1);

        for (int i = 0; i < distancias.length; i++) {
            System.out.printf("Hasta la parada %d: %d minutos%n", i + 1, distancias[i]);
        }
    }

}
