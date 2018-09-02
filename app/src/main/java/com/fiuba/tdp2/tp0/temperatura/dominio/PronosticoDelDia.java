package com.fiuba.tdp2.tp0.temperatura.dominio;

import com.fiuba.tdp2.tp0.temperatura.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

/**
 * Clase simple de datos que representa un dia especifico con su temperatura promedio de dia y noche
 * ^ Notar que el enunciado pide esto, no la máxima y mínima
 * La temperatura promedio es aquella de enter 0-9 horas y 12-21 hs.
 */
public class PronosticoDelDia {

    private String nombreDia;
    private int imagenDia;
    private int imagenNoche;
    private double temperaturaDia;
    private double temperaturaNoche;
    private boolean hayDataDelDia = true;
    //public boolean faltaInfoDia = false;

    public PronosticoDelDia() {

    }

    public String getNombreDia() {
        return nombreDia;
    }

    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }

    public int getImagenDia() {
        switch (imagenDia) {
            case 200: case 201: case 202: case 210: case 211: case 212: case 221: case 230: case 231: case 232:
                return R.drawable.ic_11d;
            case 300: case 301: case 302: case 310: case 311: case 312: case 313: case 314: case 321: case 520: case 521: case 522: case 531:
                return R.drawable.ic_09d;
            case 500: case 501: case 502: case 503: case 504:
                return R.drawable.ic_10d;
            case 511: case 600: case 601: case 602: case 611: case 612: case 615: case 616: case 620: case 621: case 622:
                return R.drawable.ic_13d;
            case 701: case 711: case 721: case 731: case 741: case 751: case 761: case 762: case 771:
                return R.drawable.ic_50d;
            case 800:
                return R.drawable.ic_01d;
            case 801:
                return R.drawable.ic_02d;
            case 802:
                return R.drawable.ic_03d;
            case 803: case 804:
                return R.drawable.ic_04d;
            default:
                return R.drawable.ic_01d;
        }
    }

    public void setImagenDia(int imagenDia) {
        this.imagenDia = imagenDia;
    }

    public int getImagenNoche() {
        switch (imagenDia) {
            case 200: case 201: case 202: case 210: case 211: case 212: case 221: case 230: case 231: case 232:
                return R.drawable.ic_11n;
            case 300: case 301: case 302: case 310: case 311: case 312: case 313: case 314: case 321: case 520: case 521: case 522: case 531:
                return R.drawable.ic_09n;
            case 500: case 501: case 502: case 503: case 504:
                return R.drawable.ic_10n;
            case 511: case 600: case 601: case 602: case 611: case 612: case 615: case 616: case 620: case 621: case 622:
                return R.drawable.ic_13n;
            case 701: case 711: case 721: case 731: case 741: case 751: case 761: case 762: case 771:
                return R.drawable.ic_50n;
            case 800:
                return R.drawable.ic_01n;
            case 801:
                return R.drawable.ic_02n;
            case 802:
                return R.drawable.ic_03n;
            case 803: case 804:
                return R.drawable.ic_04n;
            default:
                return R.drawable.ic_01n;
        }
    }

    public void setImagenNoche(int imagenNoche) {
        this.imagenNoche = imagenNoche;
    }

    public double getTemperaturaDia() {
        return temperaturaDia;
    }

    public void setTemperaturaDia(double temperaturaDia) {
        this.temperaturaDia = temperaturaDia;
    }

    public double getTemperaturaNoche() {
        return temperaturaNoche;
    }

    public void setTemperaturaNoche(double temperaturaNoche) {
        this.temperaturaNoche = temperaturaNoche;
    }

    public boolean hayDataDelDia() {
        return this.hayDataDelDia;
    }

    public void setHayDataDelDia(boolean hayDataDelDia) {
        this.hayDataDelDia = hayDataDelDia;
    }


    /*
    /**
     * Constructor por parametro

    public PronosticoDelDia(String nombreImagenDia, String nombreImagenNoche, double tmpPromedioDia, double tmpPromedioNoche) {
        this.nombreImagenDia = nombreImagenDia;
        this.nombreImagenNoche = nombreImagenNoche;
        this.tmpPromedioDia = tmpPromedioDia;
        this.tmpPromedioNoche = tmpPromedioNoche;
    }

    /**
     * Constructor para el caso en que ya haya pasado el mediodia y no se tenga info

    public PronosticoDelDia(String nombreImagenNoche, double tmpPromedioNoche) {
        this.nombreImagenNoche = nombreImagenNoche;
        this.tmpPromedioNoche = tmpPromedioNoche;
        this.faltaInfoDia = true;
    }


    /*
     * Constructor para el caso en que nos pasan un vector de JSONObjects segun documentación
     * Todos correspondientes al mismo dia
     * Los iconos elegidos corresponderan al primero tras las 6am diruno y al primero tras las 8 pm nocturno
     * Esto es lo que se aclaro en la pregunta del grupo
     * Se asume que la lista de jsons viene correctamente ordenada de menor a mayor

    public PronosticoDelDia(Vector<JSONObject> jsons) throws JSONException {
        Calendar fecha = Calendar.getInstance();
        double tmpAPromediar = 0;
        double contador = 0;
        boolean antesDeLas12 = true;
        for (Iterator<JSONObject> i = jsons.iterator(); i.hasNext();) {
            JSONObject medicion = i.next();

            long timestampUnix = medicion.getLong("dt");
            fecha.setTimeInMillis(timestampUnix * 1000);
            int hora = fecha.get(Calendar.HOUR_OF_DAY);
            dia = fecha.get(Calendar.DAY_OF_MONTH);
            mes = fecha.get(Calendar.MONTH);

            if (nombreImagenDia == null && hora >= 6 && hora < 20) {
                nombreImagenDia = medicion.getJSONArray("weather").getJSONObject(0).getString("icon");
                nombreImagenDia = nombreImagenDia.replace("n", "d");
            }
            if (nombreImagenNoche == null && hora >= 20 && hora < 24) {
                nombreImagenNoche = medicion.getJSONArray("weather").getJSONObject(0).getString("icon");
                nombreImagenNoche = nombreImagenNoche.replace("d", "n");
            }

            if (hora >= 12 && Double.isNaN(tmpPromedioDia)) {
                if (contador == 0) faltaInfoDia = true;
                else {
                    tmpPromedioDia = tmpAPromediar / contador;
                    tmpAPromediar = 0;
                    contador = 0;
                }
            }

            tmpAPromediar += medicion.getJSONObject("main").getDouble("temp");
            contador += 1;
        }
        tmpPromedioNoche = tmpAPromediar / contador;
    }
    */

}
