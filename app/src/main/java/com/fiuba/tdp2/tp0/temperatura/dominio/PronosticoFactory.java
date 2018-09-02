package com.fiuba.tdp2.tp0.temperatura.dominio;



import android.util.Log;

import com.fiuba.tdp2.tp0.temperatura.R;

import org.json.JSONException;
import org.json.JSONObject;

//import java.util.HashSet;
//import java.util.Set;



public  class PronosticoFactory {

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

        jsonObjectAux = jsonObject.getJSONObject("weather");

        pronostico.setImagen(jsonObjectAux.getInt("id"));
//            pronostico.setFechaInicio(new Fecha(jsonObject.getString("fechaInicio")));
//            pronostico.setFechaFin(new Fecha(jsonObject.getString("fechaFin")));
//            pronostico.setHoraInicio(jsonObject.getString("horaInicio"));
//            pronostico.setHoraFin(jsonObject.getString("horaFin"));
//            pronostico.setFechaRecordatorio(new Fecha(jsonObject.getString("recordatorio")));
//            pronostico.setPeriodicidad(Integer.parseInt(jsonObject.getString("periodicidad")));
//            pronostico.setTiempoEstimado(String.valueOf(jsonObject.getInt("estimacion")), "0");


//            st = jsonObject.getJSONArray("categorias");
//            Set<String> participantes = new HashSet<String>();
//            for (int i = 0; i < st.length(); i++) {
//                participantes.add(st.getString(i));
//            }

//            pronostico.setParticipantes(participantes);
//
//            JSONArray benef = jsonObject.getJSONArray("beneficios");
//
//            for (int i = 0; i < benef.length(); i++) {
//                Beneficio beneficio = new Beneficio();
//                JSONObject jo = benef.getJSONObject(i);
//                beneficio.setDescripcion(jo.getString("descripcion"));
//                beneficio.setDescuento(jo.getDouble("descuento"));
//                beneficio.setPrecio(jo.getDouble("precio"));
//                pronostico.addBeneficio(beneficio);
//            }


        Log.d("PronosticoFactory", pronostico.toString());
        return pronostico;




    }


}
