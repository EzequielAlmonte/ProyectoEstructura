package logico;

import java.util.*;

public class Parada {
    private int numero;  
    private List<Ruta> rutasConectadas;

    public Parada(int numero) {
        this.numero = numero;
        this.rutasConectadas = new ArrayList<>();
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