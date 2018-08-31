package com.fiuba.tdp2.tp0.temperatura.dominio;

public class Pronostico {
    private String dia;
    private int temperaturaMaxima;
    private int temperaturaMinima;
    private int imagen;

    public Pronostico(String dia, int temperaturaMaxima, int temperaturaMinima, int imagen) {
        this.dia = dia;
        this.temperaturaMaxima = temperaturaMaxima;
        this.temperaturaMinima = temperaturaMinima;
        this.imagen = imagen;
    }

    public String getDia() {
        return dia;
    }

    public int getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public int getTemperaturaMinima() {
        return temperaturaMinima;
    }

    public int getImagen() {
        return imagen;
    }
}
