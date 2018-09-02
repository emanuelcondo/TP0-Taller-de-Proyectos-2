package com.fiuba.tdp2.tp0.temperatura.services.listeners;


        import android.content.Context;
        import android.util.Log;
        import android.widget.Toast;

        import com.fiuba.tdp2.tp0.temperatura.dominio.Pronostico;
        import com.fiuba.tdp2.tp0.temperatura.dominio.PronosticoFactory;
        import com.fiuba.tdp2.tp0.temperatura.services.web.ResponseListener;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.List;

//        import ar.com.fiuba.tddp1.gestorvida.dominio.Actividad;
//        import ar.com.fiuba.tddp1.gestorvida.dominio.ActividadFactory;
//        import ar.com.fiuba.tddp1.gestorvida.dominio.Perfil;

public class PronosticosListener implements ResponseListener {

    private Context context;

    private List<Pronostico> pronosticos;

    public PronosticosListener(Context context) {
        this.context = context;
    }
    @Override
    public void onRequestCompleted(Object response) {

        Log.d("ActividadesListener", response.toString());
        /*
        JSONArray array = (JSONArray)response;

        for (int i = 0; i < array.length(); i++) {
            try {

                //Log.d("ActividadesListener", "Actividad " + i);
                Pronostico pronostico = PronosticoFactory.fromJSONObject(array.getJSONObject(i));
                getPronosticos().add(pronostico);
                //Perfil.agregarActividad(actividad);

            } catch (JSONException e) {
                Log.d("ActividadesListener", e.getMessage());
                Toast.makeText(context, "Error ActividadesListener: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }

        Log.d("ActividadesListener", response.toString());
        */
        //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();

        try {
            JSONObject jsonObject;
            JSONArray allForecasts = ((JSONObject)response).getJSONArray("list");

            for (int i = 0; i < allForecasts.length(); i++) {
                jsonObject = allForecasts.getJSONObject(i);
                Log.d("PronosticoListener", "Item: " + jsonObject.getString("dt_txt"));
//                Pronostico pronostico = PronosticoFactory.fromJSONObject(allForecasts.getJSONObject(i));
//                getPronosticos().add(pronostico);
//                Beneficio beneficio = new Beneficio();
//                JSONObject jo = benef.getJSONObject(i);
//                beneficio.setDescripcion(jo.getString("descripcion"));
//                beneficio.setDescuento(jo.getDouble("descuento"));
//                beneficio.setPrecio(jo.getDouble("precio"));
//                pronostico.addBeneficio(beneficio);
            }

            Log.d("PronosticoListener", "Resultado: " + ((JSONObject)response).getString("cod"));

        } catch (JSONException e) {
            Log.e("PronosticoListener", e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestError(int codError, String errorMessage) {
        String error = codError + ": " + errorMessage;
        Log.d("PronosticoListener", error);
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }


    public List<Pronostico> getPronosticos() {
        return pronosticos;
    }

    public void setPronosticos(List<Pronostico> pronosticos) {
        this.pronosticos = pronosticos;
    }
}