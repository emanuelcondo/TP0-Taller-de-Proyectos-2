package com.fiuba.tdp2.tp0.temperatura.services.listeners;


        import android.content.Context;
        import android.util.Log;
        import android.widget.Toast;

        import com.fiuba.tdp2.tp0.temperatura.dominio.Pronostico;
        import com.fiuba.tdp2.tp0.temperatura.dominio.PronosticoFactory;
        import com.fiuba.tdp2.tp0.temperatura.services.web.ResponseListener;
        import com.fiuba.tdp2.tp0.temperatura.vista.PronosticoAdapter;

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
    private List<Pronostico> pronosticosPrevios;
    private PronosticoAdapter pronosticoAdapter;

    public PronosticosListener(Context context, PronosticoAdapter pronosticoAdapter) {
        this.context = context;
        this.pronosticoAdapter = pronosticoAdapter;
    }
    @Override
    public void onRequestCompleted(Object response) {

        Log.d("PronosticosListener", response.toString());
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

        pronosticosPrevios = getPronosticos();
        getPronosticos().clear();
        try {
            JSONObject jsonObject;
            JSONArray allForecasts = ((JSONObject)response).getJSONArray("list");

            for (int i = 0; i < allForecasts.length(); i++) {
                jsonObject = allForecasts.getJSONObject(i);
                Log.d("PronosticoListener", "Item: " + jsonObject.getString("dt_txt"));
                Pronostico pronostico = PronosticoFactory.fromJSONObject(jsonObject);
                getPronosticos().add(pronostico);
            }

            Log.d("PronosticoListener", "Resultado: " + ((JSONObject)response).getString("cod"));
            pronosticoAdapter.notifyDataSetChanged();


        } catch (JSONException e) {
            Log.e("PronosticoListener", e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestError(int codError, String errorMessage) {
        setPronosticos(pronosticosPrevios);
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