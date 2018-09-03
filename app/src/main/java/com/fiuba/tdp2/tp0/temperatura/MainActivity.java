package com.fiuba.tdp2.tp0.temperatura;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
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
import android.widget.Toast;

import com.fiuba.tdp2.tp0.temperatura.dominio.Pronostico;
import com.fiuba.tdp2.tp0.temperatura.dominio.PronosticoDelDia;
import com.fiuba.tdp2.tp0.temperatura.services.listeners.PronosticosListener;
import com.fiuba.tdp2.tp0.temperatura.services.web.RequestSender;
import com.fiuba.tdp2.tp0.temperatura.vista.PronosticoDelDiaAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshBtn = findViewById(R.id.floatingActionButton);
        refreshAnimator = ObjectAnimator.ofFloat(refreshBtn, "rotation", 0f, 360f);

        refreshBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                refreshAnimator.start();
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
        if (pronosticos.size() != 0) {
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
                Log.d("MainActivity", "Clic en ciudad harcodeada");
                mostrarCiudad(3436230); //Avellaneda harcodeada
                break;
            case R.id.harcodeada02:
                Log.d("MainActivity", "Clic en ciudad harcodeada");
                nombreCiudadActual = getResources().getString(R.string.ciudad_harcodeada02);
                mostrarCiudad(3429886);
                break;
            case R.id.harcodeada03:
                Log.d("MainActivity", "Clic en ciudad harcodeada");
                nombreCiudadActual = getResources().getString(R.string.ciudad_harcodeada03);
                mostrarCiudad(6359299);
                break;
            case R.id.harcodeada04:
                Log.d("MainActivity", "Clic en ciudad harcodeada");
                nombreCiudadActual = getResources().getString(R.string.ciudad_harcodeada04);
                mostrarCiudad(5128638);
                break;
            case R.id.harcodeada05:
                Log.d("MainActivity", "Clic en ciudad harcodeada");
                nombreCiudadActual = getResources().getString(R.string.ciudad_harcodeada05);
                mostrarCiudad(6453366);
                break;
            case R.id.harcodeada06:
                Log.d("MainActivity", "Clic en ciudad harcodeada");
                nombreCiudadActual = getResources().getString(R.string.ciudad_harcodeada06);
                mostrarCiudad(1853909);
                break;
            case R.id.harcodeada07:
                Log.d("MainActivity", "Clic en ciudad harcodeada");
                nombreCiudadActual = getResources().getString(R.string.ciudad_harcodeada07);
                mostrarCiudad(524901);
                break;
            case R.id.harcodeada08:
                Log.d("MainActivity", "Clic en ciudad harcodeada");
                nombreCiudadActual = getResources().getString(R.string.ciudad_harcodeada08);
                mostrarCiudad(3858677);
                break;
            case R.id.harcodeada09:
                Log.d("MainActivity", "Clic en ciudad harcodeada");
                nombreCiudadActual = getResources().getString(R.string.ciudad_harcodeada09);
                mostrarCiudad(3840092);
                break;
            case R.id.harcodeada10:
                Log.d("MainActivity", "Clic en ciudad harcodeada");
                nombreCiudadActual = getResources().getString(R.string.ciudad_harcodeada10);
                mostrarCiudad(3652462);
                break;
            case R.id.harcodeada11:
                Log.d("MainActivity", "Clic en ciudad harcodeada");
                nombreCiudadActual = getResources().getString(R.string.ciudad_harcodeada11);
                mostrarCiudad(1210997);
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
        String appid = getResources().getString(R.string.appid);

        String url = getString(R.string.url) + "?id=" + idCiudadActual + "&units=metric&APPID=" + appid;

        requestSender.doGet_expectSingleObject(pronosticoslistener, url);

        getSupportActionBar().setTitle(nombreCiudadActual);
    }

}
