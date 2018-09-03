package com.fiuba.tdp2.tp0.temperatura.services.listeners;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.fiuba.tdp2.tp0.temperatura.dominio.Pronostico;
import com.fiuba.tdp2.tp0.temperatura.dominio.PronosticoDelDia;
import com.fiuba.tdp2.tp0.temperatura.dominio.PronosticoFactory;
import com.fiuba.tdp2.tp0.temperatura.services.web.ResponseListener;
import com.fiuba.tdp2.tp0.temperatura.vista.PronosticoDelDiaAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;



public class PronosticosListener implements ResponseListener {

    private static final int NUMERO_MAXIMO_DE_PRONOSTICOS_A_MOSTRAR = 5;
    private Context context;

    private List<Pronostico> pronosticos = new ArrayList<>();
    private List<Pronostico> pronosticosPrevios;
    private List<PronosticoDelDia> pronosticosDelDia;
    private PronosticoDelDiaAdapter pronosticoAdapter;
    private ObjectAnimator refreshAnimator = null;

    public PronosticosListener(Context context, PronosticoDelDiaAdapter pronosticoAdapter) {
        this.context = context;
        this.pronosticoAdapter = pronosticoAdapter;
    }

    public void setRefreshAnimator(ObjectAnimator obj) {
        refreshAnimator = obj;
    }

    @Override
    public void onRequestCompleted(Object response) {

        Log.d("PronosticosListener", response.toString());

        pronosticosPrevios = getPronosticos();
        getPronosticos().clear();
        try {
            JSONObject jsonObject;
            JSONArray allForecasts = ((JSONObject)response).getJSONArray("list");

            for (int i = 0; i < allForecasts.length(); i++) {
                jsonObject = allForecasts.getJSONObject(i);
                //Log.d("PronosticoListener", "Item: " + jsonObject.getString("dt_txt"));
                Pronostico pronostico = PronosticoFactory.fromJSONObject(jsonObject);
                getPronosticos().add(pronostico);
            }

            mostrarPronosticos();
            pronosticoAdapter.notifyDataSetChanged();

            if (refreshAnimator != null) {
                refreshAnimator.end();
            }
            //Hago un toast porque sino nadie se entera que se actualizo
            Toast.makeText(context, "Actualizado!", Toast.LENGTH_SHORT).show();


        } catch (JSONException e) {
            Log.e("PronosticoListener", e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestError(int codError, String errorMessage) {
        if (pronosticosPrevios != null)
            setPronosticos(pronosticosPrevios);
        //String error = codError + ": " + errorMessage;
        Log.d("PronosticoListenerR", errorMessage);
        if (refreshAnimator != null) {
            refreshAnimator.end();
        }
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
    }


    public List<Pronostico> getPronosticos() {
        return pronosticos;
    }

    public void setPronosticos(List<Pronostico> pronosticos) {
        this.pronosticos = pronosticos;
    }

    public void setPronosticosDelDia(List<PronosticoDelDia> pronosticosDelDia) {
        this.pronosticosDelDia = pronosticosDelDia;
    }

    private void mostrarPronosticos() {
        PronosticoDelDia pronosticoParaMostrar = new PronosticoDelDia();
        int diaActual = -1;
        int mesActual = -1;
        int anioActual = -1;
        double temperaturaDia = 0;
        double temperaturaNoche = 0;
        int imagenDia = 0;
        int imagenNoche = 0;
        int contadorParaPromediosDia = 0;
        int contadorParaPromediosNoche = 0;
        int contadorParaDiasDePronostico = 0;
        String[] nombresDePronosticos = {"Hoy", "Mañana"};
        Date date;
        pronosticosDelDia.clear();
        for (Pronostico pronostico: pronosticos) {
            int dia = pronostico.getDay();
            int hora = pronostico.getHour();
            if (dia != diaActual) {
                if (contadorParaPromediosNoche != 0) {
                    if (contadorParaDiasDePronostico == 0 || contadorParaDiasDePronostico == 1)
                        pronosticoParaMostrar.setNombreDia(nombresDePronosticos[contadorParaDiasDePronostico]);
                    else {
                        String dateString = String.format(Locale.getDefault(), "%d-%d-%d", anioActual, mesActual, diaActual);
                        try {
                            date = new SimpleDateFormat("yyyy-M-d").parse(dateString);//ParseException
                            String dayOfWeek = new SimpleDateFormat("EEEE", new Locale("es")).format(date);
                            pronosticoParaMostrar.setNombreDia(dayOfWeek.substring(0,1).toUpperCase() + dayOfWeek.substring(1) + ", " + diaActual + "/" + mesActual);
                        } catch (ParseException e) {
                            pronosticoParaMostrar.setNombreDia("ErrorParseo");
                        }

                    }
                    if (contadorParaPromediosDia == 0) {
                        pronosticoParaMostrar.setHayDataDelDia(false);
                    } else {
                        pronosticoParaMostrar.setTemperaturaDia(temperaturaDia / contadorParaPromediosDia);
                    }
                    pronosticoParaMostrar.setTemperaturaNoche(temperaturaNoche / contadorParaPromediosNoche);
                    pronosticoParaMostrar.setImagenDia(imagenDia);
                    pronosticoParaMostrar.setImagenNoche(imagenNoche);
                    pronosticosDelDia.add(pronosticoParaMostrar);
                    temperaturaDia = 0;
                    temperaturaNoche = 0;
                    contadorParaPromediosDia = 0;
                    contadorParaPromediosNoche = 0;
                    ++contadorParaDiasDePronostico;
                }
                pronosticoParaMostrar = new PronosticoDelDia();
                diaActual = pronostico.getDay();
                mesActual = pronostico.getMonth();
                anioActual = pronostico.getYear();
            }
            if ( hora >= 6 && hora < 20 ) {
                temperaturaDia += (pronostico.getTemperaturaMaxima() + pronostico.getTemperaturaMinima()) / 2;
                imagenDia = pronostico.getImagen();
                ++contadorParaPromediosDia;
            } else {
                temperaturaNoche += (pronostico.getTemperaturaMaxima() + pronostico.getTemperaturaMinima()) / 2;
                imagenNoche = pronostico.getImagen();
                ++contadorParaPromediosNoche;
            }
        }
        if (pronosticos.size() != 0 && pronosticos.size() < NUMERO_MAXIMO_DE_PRONOSTICOS_A_MOSTRAR) {
            String dateString = String.format(Locale.getDefault(), "%d-%d-%d", anioActual, mesActual, diaActual);
            try {
                date = new SimpleDateFormat("yyyy-M-d").parse(dateString);//ParseException
                String dayOfWeek = new SimpleDateFormat("EEEE", new Locale("es")).format(date);
                pronosticoParaMostrar.setNombreDia(dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1) + ", " + diaActual + "/" + mesActual);
            } catch (ParseException e) {
                pronosticoParaMostrar.setNombreDia("ErrorParseo");
            }
            pronosticoParaMostrar.setTemperaturaDia(temperaturaDia / contadorParaPromediosDia);
            pronosticoParaMostrar.setTemperaturaNoche(temperaturaNoche / contadorParaPromediosNoche);
            pronosticoParaMostrar.setImagenDia(imagenDia);
            pronosticoParaMostrar.setImagenNoche(imagenNoche);
            pronosticosDelDia.add(pronosticoParaMostrar);
        }
    }
}