package com.fiuba.tdp2.tp0.temperatura.dominio;

import android.util.Log;

import com.fiuba.tdp2.tp0.temperatura.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Vector;
import java.util.Date;

public  class PronosticoFactory {

    /** Server api
     * {
     "dt_txt": string,
     "temp_min": float,
     "temp_max": float,
     "humidity": int,
     "image_code": int,
     "icon": string,
     }
     * */

    public static Pronostico fromJSONObject(JSONObject jsonPronostico) throws JSONException {
        String diaHora = jsonPronostico.getString("dt_txt");
        final Pronostico pronostico = new Pronostico();

        pronostico.setDiaHora(diaHora);
        pronostico.setTemperaturaMinima(jsonPronostico.getDouble("temp_min"));
        pronostico.setTemperaturaMaxima(jsonPronostico.getDouble("temp_max"));
        pronostico.setImagen(jsonPronostico.getInt("image_code"));

        //Log.d("PronosticoFactory", pronostico.toString());
        return pronostico;

    }


}
