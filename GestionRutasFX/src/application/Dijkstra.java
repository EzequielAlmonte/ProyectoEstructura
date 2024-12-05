package application;

import java.util.List;

import java.util.List;

public class Dijkstra {
    private Grafo grafo;

    public Dijkstra(Grafo grafo) {
        this.grafo = grafo;
    }

    public double[] calcularDistancias(int numeroParadaInicio, String criterio) {
        int numParadas = grafo.getParadas().size();
        double[] distancias = new double[numParadas];
        boolean[] visitado = new boolean[numParadas];

        for (int i = 0; i < numParadas; i++) {
            distancias[i] = Double.MAX_VALUE;
            visitado[i] = false;
        }
        distancias[numeroParadaInicio - 1] = 0;

        for (int i = 0; i < numParadas - 1; i++) {
            int u = minDistancia(distancias, visitado);
            visitado[u] = true;

            Parada paradaActual = grafo.getParadas().get(u);
            List<Ruta> rutasSalientes = paradaActual.getRutasConectadas();

            for (Ruta ruta : rutasSalientes) {
                int indiceDestino = ruta.getDestino().getNumero() - 1;
                double pesoRuta;
                switch (criterio.toLowerCase()) {
                    case "costo":
                        pesoRuta = ruta.getCosto();
                        break;
                    case "distancia":
                        pesoRuta = ruta.getDistancia();
                        break;
                    case "transbordos":
                        pesoRuta = ruta.getTransbordos();
                        break;
                    default:
                        pesoRuta = ruta.getTiempo();
                        break;
                }
                if (!visitado[indiceDestino] && distancias[u] != Double.MAX_VALUE
                        && distancias[u] + pesoRuta < distancias[indiceDestino]) {
                    distancias[indiceDestino] = distancias[u] + pesoRuta;
                }
            }
        }
        return distancias;
    }

    private int minDistancia(double[] distancias, boolean[] visitado) {
        double min = Double.MAX_VALUE;
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