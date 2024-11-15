package logico;

import java.util.List;

public class Dijkstra {
	 private Grafo grafo;
	 
	 public Dijkstra(Grafo grafo) {
	        this.grafo = grafo;
	    }

	    public int[] calcularDistancias(int numeroParadaInicio) {
	        int numParadas = grafo.getParadas().size();
	        int[] distancias = new int[numParadas];
	        boolean[] visitado = new boolean[numParadas];

	        // Se inicializan todas las distancias en infinito, excepto la de la parada de inicio
	        for (int i = 0; i < numParadas; i++) {
	            distancias[i] = Integer.MAX_VALUE;
	            visitado[i] = false;
	        }
	        distancias[numeroParadaInicio - 1] = 0;

	        for (int i = 0; i < numParadas - 1; i++) {
	            int u = minDistancia(distancias, visitado);
	            visitado[u] = true;

	            // Actualizando las distancias de las paradas adyacentes
	            Parada paradaActual = grafo.getParadas().get(u);
	            List<Ruta> rutasSalientes = paradaActual.getRutasConectadas();

	            for (Ruta ruta : rutasSalientes) {
	                int indiceDestino = ruta.getDestino().getNumero() - 1;
	                if (!visitado[indiceDestino] && distancias[u] != Integer.MAX_VALUE
	                        && distancias[u] + ruta.getTiempo() < distancias[indiceDestino]) {
	                    distancias[indiceDestino] = distancias[u] + ruta.getTiempo();
	                }
	            }
	        }

	        return distancias;
	    }

	    // Método para encontrar el nodo con la distancia mínima no visitado
	    private int minDistancia(int[] distancias, boolean[] visitado) {
	        int min = Integer.MAX_VALUE;
	        int indiceMin = -1;

	        for (int i = 0; i < distancias.length; i++) {
	            if (!visitado[i] && distancias[i] <= min) {
	                min = distancias[i];
	                indiceMin = i;
	            }
	        }

	        return indiceMin;
	    }

}
