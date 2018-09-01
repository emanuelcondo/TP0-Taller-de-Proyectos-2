package com.fiuba.tdp2.tp0.temperatura.dominio;

public class Pronostico {
    private String dia;
    private int temperaturaMaxima;
    private int temperaturaMinima;
    private int imagen;

    public Pronostico(){

    }
    public Pronostico(String dia, int temperaturaMaxima, int temperaturaMinima, int imagen) {
        this.setDia(dia);
        this.setTemperaturaMaxima(temperaturaMaxima);
        this.setTemperaturaMinima(temperaturaMinima);
        this.setImagen(imagen);
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

    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setTemperaturaMaxima(int temperaturaMaxima) {
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public void setTemperaturaMinima(int temperaturaMinima) {
        this.temperaturaMinima = temperaturaMinima;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
