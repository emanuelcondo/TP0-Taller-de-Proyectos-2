package com.fiuba.tdp2.tp0.temperatura;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fiuba.tdp2.tp0.temperatura.dominio.Pronostico;
import com.fiuba.tdp2.tp0.temperatura.dominio.PronosticoDelDia;
import com.fiuba.tdp2.tp0.temperatura.services.listeners.PronosticosListener;
import com.fiuba.tdp2.tp0.temperatura.services.web.RequestSender;
import com.fiuba.tdp2.tp0.temperatura.vista.PronosticoDelDiaAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int NUMERO_MAXIMO_DE_PRONOSTICOS_A_MOSTRAR = 5;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private FloatingActionButton refreshBtn;

    private ObjectAnimator refreshAnimator;

    private PronosticoDelDiaAdapter pronosticoDelDiaAdapter;

    //lista con todos los pronosticos para la ultima ciudad consultada
    private ArrayList<Pronostico> pronosticos;
    //lista de los 5 pronosticos a mostrar
    private ArrayList<PronosticoDelDia> pronosticosParaMostrar;
    // si se consulta dos veces la misma ciudad no voy al servidor sino que levanto de aca
    private SparseArray<Pronostico> pronosticosCacheados;

    private PronosticosListener pronosticoslistener;

    public static int idCiudadActual = 3435910; //por default se muestra buenos aires
    public static String nombreCiudadActual = "Buenos Aires";

    public static int colorFondo;
    public static int colorItem;
    public static int colorLetras;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setColores();
        refreshBtn = findViewById(R.id.floatingActionButton);
        refreshAnimator = ObjectAnimator.ofFloat(refreshBtn, "rotation", 0f, 360f);

        refreshBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                refreshAnimator.start();
                // Despues si ves lo del callback cambia esto de lugar, pero no me dejes el boton roto mientras amigo
                refrescarCiudadActual();
            }
        });

        cargarSharedPrefs();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(nombreCiudadActual);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(nombreCiudadActual);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pronosticos = new ArrayList<>();
        pronosticosParaMostrar = new ArrayList<>();
        pronosticosCacheados = new SparseArray<>();
        pronosticoDelDiaAdapter = new PronosticoDelDiaAdapter(this, pronosticosParaMostrar);
        recyclerView.setAdapter(pronosticoDelDiaAdapter);

        pronosticoslistener = new PronosticosListener(this, pronosticoDelDiaAdapter);
        pronosticoslistener.setRefreshAnimator(refreshAnimator);
        pronosticoslistener.setPronosticos(pronosticos);
        pronosticoslistener.setPronosticosDelDia(pronosticosParaMostrar);
        refrescarCiudadActual();

        mostrarPronosticos();

    }

    private void cargarSharedPrefs() {
        SharedPreferences pref = this.getSharedPreferences("TemperaturaTDP2", 0);
        nombreCiudadActual = pref.getString("ciudadActual", nombreCiudadActual);
        idCiudadActual = pref.getInt("idActual", idCiudadActual);
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
        Date date;
        String[] nombresDePronosticos = {"Hoy", "Mañana"};
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
                            pronosticoParaMostrar.setNombreDia(dayOfWeek.substring(0,1).toUpperCase() + dayOfWeek.substring(1) + ", "  + diaActual + "/" + mesActual);
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
                    pronosticosParaMostrar.add(pronosticoParaMostrar);
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
            pronosticosParaMostrar.add(pronosticoParaMostrar);
        }
    }

    public void refreshPronostico(View view) {
        refrescarCiudadActual();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //drawer.closeDrawer(GravityCompat.START);

        Log.d("MainActivity", "Se hizo clic en la opcion " + id);


        switch (id) {
            case R.id.ciudades:
                Log.d("MainActivity", "Clic en ciudades");
                goToCities();
                break;
            case R.id.harcodeada:
                resetTemperaturas();
                break;
            case R.id.harcodeada02:
                setNubes();
                break;
            case R.id.harcodeada03:
                setCondClimaticas();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void goToCities() {
        Intent intent = new Intent(MainActivity.this, CitiesActivity.class);
        MainActivity.this.startActivity(intent);
    }

    private void mostrarCiudad(int idCiudad) {
        // TODO: descomentar cuando todo funcione
        // primero veo si ya fue cacheado
        //pronosticos = pronosticosCacheados.get(idCiudad);
        //if (pronosticos == null) {
           idCiudadActual = idCiudad;
           //TODO: pegarle el nombre de la ciudad actual con la clase estática de Cities

           refrescarCiudadActual();

            // pronosticosCacheados.put(idCiudad, pronosticos);
        //}


    }

    private void refrescarCiudadActual() {
        RequestSender requestSender = new RequestSender(this);
        String hostServer = getString(R.string.host);
        String baseUrl = getString(R.string.base_url);
        String url = hostServer+baseUrl+idCiudadActual;

        requestSender.doGet_expectSingleObject(pronosticoslistener, url);

        getSupportActionBar().setTitle(nombreCiudadActual);
    }

    private void setColores() {
        Calendar ahora = Calendar.getInstance();
        int hora = ahora.get(Calendar.HOUR_OF_DAY);

        if (hora >= 6 && hora < 20) {
            colorFondo = getResources().getColor(R.color.colorFondoDia);
            colorLetras = getResources().getColor(R.color.colorLetrasDia);
            colorItem = getResources().getColor(R.color.colorItemDia);
        }
        else {
            colorFondo = getResources().getColor(R.color.colorFondoNoche);
            colorLetras = getResources().getColor(R.color.colorLetrasNoche);
            colorItem = getResources().getColor(R.color.colorItemNoche);
        }
        this.findViewById(R.id.relative).setBackgroundColor(colorFondo);
    }

    /**
     * Pone todas las temperaturas a mostrar en 0
     */
    private void resetTemperaturas() {
        for (Iterator<PronosticoDelDia> i = pronosticosParaMostrar.iterator(); i.hasNext();) {
            PronosticoDelDia pronostico = i.next();
            pronostico.setTemperaturaDia(0);
            pronostico.setTemperaturaNoche(0);
        }
        this.pronosticoDelDiaAdapter.notifyDataSetChanged();
    }

    /**
     * Cambia los iconos por los diferentes grados de nubes
     */
    private void setNubes() {
        pronosticosParaMostrar.get(0).setImagenDia(800);
        pronosticosParaMostrar.get(0).setImagenNoche(800);
        pronosticosParaMostrar.get(1).setImagenDia(801);
        pronosticosParaMostrar.get(1).setImagenNoche(801);
        pronosticosParaMostrar.get(2).setImagenDia(802);
        pronosticosParaMostrar.get(2).setImagenNoche(802);
        pronosticosParaMostrar.get(3).setImagenDia(803);
        pronosticosParaMostrar.get(3).setImagenNoche(803);
        pronosticosParaMostrar.get(4).setImagenDia(803);
        pronosticosParaMostrar.get(4).setImagenNoche(803);
        this.pronosticoDelDiaAdapter.notifyDataSetChanged();
    }

    /**
     * Cambia los iconos por soleado/lluvia/rayos/nieve/neblina
     */
    private void setCondClimaticas() {
        pronosticosParaMostrar.get(0).setImagenDia(200);
        pronosticosParaMostrar.get(0).setImagenNoche(200);
        pronosticosParaMostrar.get(1).setImagenDia(300);
        pronosticosParaMostrar.get(1).setImagenNoche(300);
        pronosticosParaMostrar.get(2).setImagenDia(500);
        pronosticosParaMostrar.get(2).setImagenNoche(500);
        pronosticosParaMostrar.get(3).setImagenDia(600);
        pronosticosParaMostrar.get(3).setImagenNoche(600);
        pronosticosParaMostrar.get(4).setImagenDia(701);
        pronosticosParaMostrar.get(4).setImagenNoche(701);
        this.pronosticoDelDiaAdapter.notifyDataSetChanged();
    }

}
