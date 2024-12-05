package application;

public class Ruta {
    private Parada origen;
    private Parada destino;
    private int tiempo; // tiempo en minutos
    private int transbordos; // cantidad de transbordos
    private double costo; // costo de la ruta
    private double distancia; // distancia en kilómetros

    public Ruta(Parada origen, Parada destino, int tiempo, int transbordos, double costo, double distancia) {
        this.origen = origen;
        this.destino = destino;
        this.tiempo = tiempo;
        this.transbordos = transbordos;
        this.costo = costo;
        this.distancia = distancia;
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

    public double getCosto() {
        return costo;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public void setTransbordos(int transbordos) {
        this.transbordos = transbordos;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
}