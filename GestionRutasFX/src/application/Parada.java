package application;

import java.util.ArrayList;
import java.util.List;

public class Parada {
    private String etiqueta;
    private int numero;
    private List<Ruta> rutasConectadas; // Rutas que salen de esta parada

    public Parada(int numero) {
        this.numero = numero;
        this.etiqueta = "P" + numero;
        this.rutasConectadas = new ArrayList<>();
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public int getNumero() {
        return numero;
    }

    public List<Ruta> getRutasConectadas() {
        return rutasConectadas;
    }

    public void agregarRuta(Ruta ruta) {
        rutasConectadas.add(ruta);
    }

    public void eliminarRuta(Ruta ruta) {
        rutasConectadas.remove(ruta);
    }
}