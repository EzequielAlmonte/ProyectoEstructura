package logico;

public class Ruta {
    private Parada origen;
    private Parada destino;
    private int tiempo; // minutos
    private int transbordos;// cantidad de transbordos

    public Ruta(Parada origen, Parada destino, int tiempo, int transbordos) {
        this.origen = origen;
        this.destino = destino;
        this.tiempo = tiempo;
        this.transbordos = transbordos;
    }

    public Parada getOrigen() {
        return origen;
    }

    public Parada getDestino() {
        return destino;
    }

    public int getTiempo() {
        return tiempo;
    }

    public int getTransbordos() {
        return transbordos;
    }
    
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public void setTransbordos(int transbordos) {
        this.transbordos = transbordos;
    }
}