package com.fiuba.tdp2.tp0.temperatura.dominio;



import android.util.Log;

import org.json.JSONArray;
import com.fiuba.tdp2.tp0.temperatura.R;

import org.json.JSONException;
import org.json.JSONObject;

//import java.util.HashSet;
//import java.util.Set;



public  class PronosticoFactory {

    public static Pronostico fromJSONObject(JSONObject jsonObject) throws JSONException {



        String diaPronostico = String.format("%1$-3s", jsonObject.getString("nombre"));
        Pronostico pronostico;

        pronostico = new Pronostico();
        pronostico.setDia(jsonObject.getString("descripcion"));
//            pronostico.setPrioridad(jsonObject.getString("prioridad"));

        Log.d("P", pronostico.toString());

        pronostico.setImagen(R.array.imagenes_clima);
        pronostico.setTemperaturaMinima(Integer.parseInt(jsonObject.getString("t1")));
        pronostico.setTemperaturaMaxima(Integer.parseInt(jsonObject.getString("t2")));
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


        return pronostico;




    }

    public static JSONObject toJSONObject(Pronostico pronostico) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nombre", pronostico.getDia());
            jsonObject.put("descripcion", pronostico.getTemperaturaMinima());
            jsonObject.put("_id", pronostico.getTemperaturaMaxima());
            jsonObject.put("foto", pronostico.getImagen());
//            jsonObject.put("fechaInicio", pronostico.getFechaInicio());
//            jsonObject.put("fechaFin", pronostico.getFechaFin());
//            jsonObject.put("horaInicio", pronostico.getHoraInicio());
//            jsonObject.put("horaFin", pronostico.getHoraFin());


//            JSONArray participantes = new JSONArray();
//
//            for (Contacto contacto: pronostico.getParticipantes()) {
//                participantes.put(contacto.getNombre());
//            }
//
//            jsonObject.put("participantes", participantes);
//
//            JSONArray categorias = new JSONArray();
//            for (Etiqueta etiqueta: pronostico.getEtiquetas()) {
//                categorias.put(etiqueta.serializar());
//            }
//
//            jsonObject.put("categorias", categorias);
//
//            JSONArray beneficios = new JSONArray();
//
//            for (Beneficio beneficio: pronostico.getBeneficios()) {
//                JSONObject jsonObject1 = new JSONObject();
//                jsonObject1.put("precio", beneficio.getPrecio());
//                jsonObject1.put("descuento", beneficio.getDescuento());
//                jsonObject1.put("descripcion", beneficio.getDescripcion());
//                beneficios.put(jsonObject1);
//            }
//
//            jsonObject.put("beneficios", beneficios);

        } catch (JSONException e) {
            Log.e("¨pronosticoFactory", e.getMessage());
        }

        return jsonObject;

    }
}
