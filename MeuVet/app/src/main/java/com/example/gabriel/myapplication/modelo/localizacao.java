package com.example.gabriel.myapplication.modelo;

public class localizacao {
    private Double latitude;
    private Double longitude;
    private int raio;

    public localizacao() {
    }

    public localizacao(double latitude, double longitude, int raio) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.raio = raio;
    }

    public int getRaio() {
        return raio;
    }

    public void setRaio(int raio) {
        this.raio = raio;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
