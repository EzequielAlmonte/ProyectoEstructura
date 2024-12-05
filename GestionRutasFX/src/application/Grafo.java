package application;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
    private List<Parada> paradas; // Paradas del grafo
    private List<Ruta> rutas; // Rutas del grafo
    private List<List<Parada>> listaAdyacencia; // Lista de adyacencia

    public Grafo(int numParadas) {
        this.paradas = new ArrayList<>();
        this.rutas = new ArrayList<>();
        this.listaAdyacencia = new ArrayList<>();
        
        // Crear las paradas
        for (int i = 1; i <= numParadas; i++) {
            Parada nuevaParada = new Parada(i);
            paradas.add(nuevaParada);
            listaAdyacencia.add(new ArrayList<>());
        }
    }

    public List<Parada> getParadas() {
        return paradas;
    }

    public List<Ruta> getRutas() {
        return rutas;
    }

    // Obtener rutas que salen de una parada
    public List<Ruta> obtenerRutasSalientes(int numeroParada) {
        List<Ruta> rutasSalientes = new ArrayList<>();
        Parada parada = paradas.get(numeroParada - 1);

        for (Ruta ruta : rutas) {
            if (ruta.getOrigen().equals(parada)) {
                rutasSalientes.add(ruta);
            }
        }
        return rutasSalientes;
    }

    // Agregar una nueva ruta
    public void agregarRuta(int origen, int destino, int tiempo, int transbordos, double costo, double distancia) {
        // Validar entradas
        if (origen <= 0 || destino <= 0 || origen > paradas.size() || destino > paradas.size()) {
            throw new IllegalArgumentException("Número de parada inválido.");
        }
        if (tiempo < 0 || transbordos < 0 || costo < 0 || distancia < 0) {
            throw new IllegalArgumentException("Valores no pueden ser negativos.");
        }

        Parada paradaOrigen = paradas.get(origen - 1);
        Parada paradaDestino = paradas.get(destino - 1);

        Ruta nuevaRuta = new Ruta(paradaOrigen, paradaDestino, tiempo, transbordos, costo, distancia);
        rutas.add(nuevaRuta);
        listaAdyacencia.get(origen - 1).add(paradaDestino);
        paradaOrigen.agregarRuta(nuevaRuta);
    }

    // Eliminar una ruta
    public boolean eliminarRuta(int origen, int destino) {
        Ruta rutaEliminar = null;

        // Buscar la ruta a eliminar
        for (Ruta ruta : rutas) {
            if (ruta.getOrigen().getNumero() == origen && ruta.getDestino().getNumero() == destino) {
                rutaEliminar = ruta;
                break;
            }
        }

        // Eliminar la ruta si se encuentra
        if (rutaEliminar != null) {
            rutas.remove(rutaEliminar);
            return true;
        }
        return false; // No se encontró la ruta
    }

    // Modificar una ruta existente
    public void modificarRuta(int origen, int destino, int nuevoTiempo, int nuevosTransbordos, double nuevoCosto, double nuevaDistancia) {
        for (Ruta ruta : rutas) {
            if (ruta.getOrigen().getNumero() == origen && ruta.getDestino().getNumero() == destino) {
                ruta.setTiempo(nuevoTiempo);
                ruta.setTransbordos(nuevosTransbordos);
                ruta.setCosto(nuevoCosto);
                ruta.setDistancia(nuevaDistancia);
                break;
            }
        }
    }
}
