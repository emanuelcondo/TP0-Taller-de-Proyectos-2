package com.fiuba.tdp2.tp0.temperatura;

import android.content.res.TypedArray;
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
import com.fiuba.tdp2.tp0.temperatura.services.listeners.PronosticosListener;
import com.fiuba.tdp2.tp0.temperatura.services.web.RequestSender;
import com.fiuba.tdp2.tp0.temperatura.vista.PronosticoAdapter;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;

    private PronosticoAdapter pronosticoAdapter;

    //lista con todos los pronosticos para la ultima ciudad consultada
    private ArrayList<Pronostico> pronosticos;
    // si se consulta dos veces la misma ciudad no voy al servidor sino que levanto de aca
    private SparseArray<Pronostico> pronosticosCacheados;

    private PronosticosListener pronosticoslistener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pronosticos = new ArrayList<>();
        pronosticosCacheados = new SparseArray<>();
        pronosticoAdapter = new PronosticoAdapter(this, pronosticos);
        recyclerView.setAdapter(pronosticoAdapter);
        mockearPronosticos();

        pronosticoslistener = new PronosticosListener(this);
        pronosticoslistener.setPronosticos(pronosticos);
    }

    //TODO: Borrar esto
    private void mockearPronosticos() {
        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
        Integer[] tempMaximas = {15, 22, 25, 11, 8};
        Integer[] tempMinimas = {11, 16, 18, 5, 1};
        TypedArray imagenesClima = getResources().obtainTypedArray(R.array.imagenes_clima);

        pronosticos.clear(); //ojo no hacer new aca ni en el definitivo

        for (int i = 0 ; i < dias.length ; ++i) {
            pronosticos.add(new Pronostico(dias[i], tempMaximas[i], tempMinimas[i], imagenesClima.getResourceId(0, 0)));
        }

        imagenesClima.recycle();

        pronosticoAdapter.notifyDataSetChanged();
    }

    public void refreshPronostico(View view) {
        mockearPronosticos();
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
            case R.id.action_settings:
                Log.d("MainActivity", "Clic en configuracion");
                break;
            case R.id.harcodeada:
                Log.d("MainActivity", "Clic en ciudad harcodeada");
                mostrarCiudad(3436230); //Avellaneda harcodeada
                break;
            default:
                return super.onOptionsItemSelected(item);
        }


        return true;
    }

    private void mostrarCiudad(int idCiudad) {
        // TODO: descomentar cuando todo funcione
        // primero veo si ya fue cacheado
        //pronosticos = pronosticosCacheados.get(idCiudad);
        //if (pronosticos == null) {
            RequestSender requestSender = new RequestSender(this);
            String appid = getResources().getString(R.string.appid);

            String url = getString(R.string.url) + "?id=" + idCiudad + "&units=metric&APPID=" + appid;

            requestSender.doGet(pronosticoslistener, url);

            // pronosticosCacheados.put(idCiudad, pronosticos);
        //}

    }

}
