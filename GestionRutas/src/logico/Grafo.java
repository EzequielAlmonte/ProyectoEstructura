package logico;

import java.util.*;

public class Grafo{
    private List<Parada> paradas; //aristas
    private List<Ruta> rutas; //nodos
    private List<List<Parada>> listaAdyacencia; // lista de adyacencia

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
    
 // Funcion para obtener rutas que salen de una parada
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
    
    public void agregarRuta(int origen, int destino, int tiempo, int transbordos) {
        // Los índices en la lista son (número de parada - 1)
        Parada paradaOrigen = paradas.get(origen - 1);
        Parada paradaDestino = paradas.get(destino - 1);
        
        Ruta nuevaRuta = new Ruta(paradaOrigen, paradaDestino, tiempo, transbordos);
        rutas.add(nuevaRuta);
        
        // Actualizar la lista de adyacencia
        listaAdyacencia.get(origen - 1).add(paradaDestino);
        paradaOrigen.agregarRuta(nuevaRuta);
    }
  /*  
    public void agregarRuta(int origen, int destino, int tiempo, int transbordos) {
        // Valida números de parada
        if (origen <= 0 || destino <= 0 || origen > paradas.size() || destino > paradas.size()) {
            throw new IllegalArgumentException("Número de parada inválido.");
        }
        // Valida el tiempo y transbordos
        if (tiempo < 0 || transbordos < 0) {
            throw new IllegalArgumentException("Tiempo o transbordos no pueden ser negativos.");
        }

        // Agregar la ruta
        Parada paradaOrigen = paradas.get(origen - 1);
        Parada paradaDestino = paradas.get(destino - 1);
        
        Ruta nuevaRuta = new Ruta(paradaOrigen, paradaDestino, tiempo, transbordos);
        rutas.add(nuevaRuta);
        
        // Actualiza la lista de adyacencia y rutas conectadas de la parada origen
        listaAdyacencia.get(origen - 1).add(paradaDestino);
        paradaOrigen.agregarRuta(nuevaRuta);
    }
*/
	
 // Obtener todas las paradas adyacentes a una parada
    public List<Parada> obtenerParadasAdyacentes(int numeroParada) {
        return new ArrayList<>(listaAdyacencia.get(numeroParada - 1));
    }
    
 // Método para eliminar una ruta específica entre dos paradas
    public void eliminarRuta(int origen, int destino) {
        Parada paradaOrigen = paradas.get(origen - 1);
        Parada paradaDestino = paradas.get(destino - 1);

        rutas.removeIf(ruta -> ruta.getOrigen().equals(paradaOrigen) && ruta.getDestino().equals(paradaDestino));
        listaAdyacencia.get(origen - 1).remove(paradaDestino);
    }

    // Método para modificar una ruta existente
    public void modificarRuta(int origen, int destino, int nuevoTiempo, int nuevosTransbordos) {
        for (Ruta ruta : rutas) {
            if (ruta.getOrigen().getNumero() == origen && ruta.getDestino().getNumero() == destino) {
                ruta.setTiempo(nuevoTiempo);
                ruta.setTransbordos(nuevosTransbordos);
                break;
            }
        }
    }

}