package com.fiuba.tdp2.tp0.temperatura.dominio;

import android.util.Log;

import com.fiuba.tdp2.tp0.temperatura.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Vector;
import java.util.Date;

//import java.util.HashSet;
//import java.util.Set;



public  class PronosticoFactory {

    /**
     * Dado un JSON con los proximos 5 dias, calcula y devuelve los objetos PronosticoDelDia adecuados
     */
    public static Vector<PronosticoDelDia> deJSON(JSONObject pronostico5DiasJSON) throws JSONException {
        int cantidad = pronostico5DiasJSON.getInt("cnt");
        JSONArray lista = pronostico5DiasJSON.getJSONArray("list");
        int diaActual = 0; // 1-31, 0 = Sin inicializar. Como solo predecimos 5 dias, no me preocupo.
        Vector<JSONObject> infoDia = new Vector<JSONObject>();
        Vector<PronosticoDelDia> pronostico = new Vector<PronosticoDelDia>();
        for (int i = 0; i < cantidad; ++i) {
            JSONObject medicion = lista.getJSONObject(i);
            long timestampUnix = medicion.getLong("dt");
            Calendar fecha = Calendar.getInstance();
            fecha.setTimeInMillis(timestampUnix*1000);
            int diaLeido = fecha.get(Calendar.DAY_OF_MONTH);
            if (diaLeido != diaActual) {
                if (!infoDia.isEmpty()) {
                    pronostico.add(new PronosticoDelDia(infoDia));
                }
                infoDia.clear();
                diaActual = diaLeido;
            }
            infoDia.add(medicion);
        }
        if (pronostico.size() < 5) pronostico.add(new PronosticoDelDia(infoDia));
        return pronostico;
    }

    /**
     * Dado un JSON con los proximos 5 dias, y el pronostico actual
     * calcula y devuelve los objetos PronosticoDelDia adecuados
     * Este método modifica el pronostico5DiasJSON recibido y le agrega un elemento
     */
    public static Vector<PronosticoDelDia> deJSON(JSONObject pronostico5DiasJSON, JSONObject pronositcoActual) throws JSONException {
        JSONArray lista = pronostico5DiasJSON.getJSONArray("list");
        lista.put(0, pronositcoActual);
        return deJSON(pronostico5DiasJSON);
    }

    public static Pronostico fromJSONObject(JSONObject jsonObject) throws JSONException {

        /*
        {
      "main": {
        "temp": 19.09,
        "temp_min": 19.09,
        "temp_max": 20.74
      },
      "weather": [
        {
          "id": 802,
          "main": "Clouds",
          "description": "scattered clouds",
          "icon": "03n"
        }
      ],

      "dt_txt": "2018-09-02 00:00:00"
    },
        * */


        String diaHora = jsonObject.getString("dt_txt");
        Pronostico pronostico;

        pronostico = new Pronostico();
        pronostico.setDiaHora(diaHora);

        JSONObject jsonObjectAux = jsonObject.getJSONObject("main");

        pronostico.setTemperaturaMinima(jsonObjectAux.getDouble("temp_min"));
        pronostico.setTemperaturaMaxima(jsonObjectAux.getDouble("temp_max"));

        JSONArray jsonArray = jsonObject.getJSONArray("weather");

        jsonObjectAux = jsonArray.getJSONObject(0);

        pronostico.setImagen(jsonObjectAux.getInt("id"));

        Log.d("PronosticoFactory", pronostico.toString());
        return pronostico;

    }


}
