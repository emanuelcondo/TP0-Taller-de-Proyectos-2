package com.fiuba.tdp2.tp0.temperatura.dominio;

import com.fiuba.tdp2.tp0.temperatura.R;

public class Pronostico {
    private String diaHora;
    private double temperaturaMaxima;
    private double temperaturaMinima;
    private int imagen;

    public Pronostico(){

    }
    public Pronostico(String diaHora, double temperaturaMaxima, double temperaturaMinima, int imagen) {
        this.setDiaHora(diaHora);
        this.setTemperaturaMaxima(temperaturaMaxima);
        this.setTemperaturaMinima(temperaturaMinima);
        this.setImagen(imagen);
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
 /*
        HACER UN CASE CON ESTO, SON TODAS LAS IMAGENES POSIBLES y retornar el R.id de la imagen asociada:
ID 	Meaning
200 	thunderstorm with light rain
201 	thunderstorm with rain
202 	thunderstorm with heavy rain
210 	light thunderstorm
211 	thunderstorm
212 	heavy thunderstorm
221 	ragged thunderstorm
230 	thunderstorm with light drizzle
231 	thunderstorm with drizzle
232 	thunderstorm with heavy drizzle
300 	light intensity drizzle
301 	drizzle
302 	heavy intensity drizzle
310 	light intensity drizzle rain
311 	drizzle rain
312 	heavy intensity drizzle rain
313 	shower rain and drizzle
314 	heavy shower rain and drizzle
321 	shower drizzle
500 	light rain
501 	moderate rain
502 	heavy intensity rain
503 	very heavy rain
504 	extreme rain
511 	freezing rain
520 	light intensity shower rain
521 	shower rain
522 	heavy intensity shower rain
531 	ragged shower rain
600 	light snow
601 	snow
602 	heavy snow
611 	sleet
612 	shower sleet
615 	light rain and snow
616 	rain and snow
620 	light shower snow
621 	shower snow
622 	heavy shower snow
701 	mist
711 	smoke
721 	haze
731 	sand, dust whirls
741 	fog
751 	sand
761 	dust
762 	volcanic ash
771 	squalls
781 	tornado
800 	clear sky
801 	few clouds
802 	scattered clouds
803 	broken clouds
804 	overcast clouds

         */
        return imagen;
        // return R.array.imagenes_clima;
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
