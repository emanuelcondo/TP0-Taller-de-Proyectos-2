package com.fiuba.tdp2.tp0.temperatura.services.listeners;


        import android.content.Context;
        import android.util.Log;
        import android.widget.Toast;

        import com.fiuba.tdp2.tp0.temperatura.dominio.Pronostico;
        import com.fiuba.tdp2.tp0.temperatura.dominio.PronosticoFactory;
        import com.fiuba.tdp2.tp0.temperatura.services.web.ResponseListener;

        import org.json.JSONArray;
        import org.json.JSONException;

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

        JSONArray array = (JSONArray)response;
        Log.d("ActividadesListener", response.toString());
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
        //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestError(int codError, String errorMessage) {
        String error = codError + ": " + errorMessage;
        Log.d("ActividadesListener", error);
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }


    public List<Pronostico> getPronosticos() {
        return pronosticos;
    }

    public void setPronosticos(List<Pronostico> pronosticos) {
        this.pronosticos = pronosticos;
    }
}