package com.fiuba.tdp2.tp0.temperatura.dominio;


public class Pronostico {
    private String diaHora;
    private double temperaturaMaxima;
    private double temperaturaMinima;
    private int imagen;

    public Pronostico(){

    }

    public String getDiaHora() {
        return diaHora;
    }

    public double getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public double getTemperaturaMinima() {
        return temperaturaMinima;
    }

    public int getImagen() {

        return imagen;
    }

    public void setDiaHora(String diaHora) {
        this.diaHora = diaHora;
    }

    public void setTemperaturaMaxima(double temperaturaMaxima) {
        this.temperaturaMaxima = temperaturaMaxima;
    }

    public void setTemperaturaMinima(double temperaturaMinima) {
        this.temperaturaMinima = temperaturaMinima;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }



    @Override
    public String toString() {
        return diaHora + " - min: " + temperaturaMinima + " - max: " + temperaturaMaxima + " img: " + imagen;
    }

    public int getYear() {
        return Integer.parseInt(this.getDiaHora().substring(0, 4));
    }

    public int getDay() {
        return Integer.parseInt(this.getDiaHora().substring(8, 10));
    }

    public int getHour() {
        return Integer.parseInt(this.getDiaHora().substring(11, 13));
    }

    public int getMonth() {
        return Integer.parseInt(this.getDiaHora().substring(5, 7));
    }
}
