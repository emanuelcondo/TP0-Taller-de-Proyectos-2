package com.fiuba.tdp2.tp0.temperatura.dominio;

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

    public int mes = -1;
    public int dia = -1;
    public String nombreImagenDia = null;
    public String nombreImagenNoche = null;
    public double tmpPromedioDia = Double.NaN;
    public double tmpPromedioNoche = Double.NaN;
    public boolean faltaInfoDia = false;

    /**
     * Constructor por parametro
     */
    public PronosticoDelDia(String nombreImagenDia, String nombreImagenNoche, double tmpPromedioDia, double tmpPromedioNoche) {
        this.nombreImagenDia = nombreImagenDia;
        this.nombreImagenNoche = nombreImagenNoche;
        this.tmpPromedioDia = tmpPromedioDia;
        this.tmpPromedioNoche = tmpPromedioNoche;
    }

    /**
     * Constructor para el caso en que ya haya pasado el mediodia y no se tenga info
     */
    public PronosticoDelDia(String nombreImagenNoche, double tmpPromedioNoche) {
        this.nombreImagenNoche = nombreImagenNoche;
        this.tmpPromedioNoche = tmpPromedioNoche;
        this.faltaInfoDia = true;
    }

    /**
     * Constructor para el caso en que nos pasan un vector de JSONObjects segun documentación
     * Todos correspondientes al mismo dia
     * Los iconos elegidos corresponderan al primero tras las 6am diruno y al primero tras las 8 pm nocturno
     * Esto es lo que se aclaro en la pregunta del grupo
     * Se asume que la lista de jsons viene correctamente ordenada de menor a mayor
     */
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
}
