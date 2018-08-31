package com.fiuba.tdp2.tp0.temperatura;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;

import com.fiuba.tdp2.tp0.temperatura.dominio.Pronostico;
import com.fiuba.tdp2.tp0.temperatura.vista.PronosticoAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Pronostico> pronosticos;
    private PronosticoAdapter pronosticoAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pronosticos = new ArrayList<>();
        pronosticoAdapter = new PronosticoAdapter(this, pronosticos);
        recyclerView.setAdapter(pronosticoAdapter);
        mockearPronosticos();
    }

    //TODO: Borrar esto
    private void mockearPronosticos() {
        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
        Integer[] tempMaximas = {15, 22, 25, 11, 8};
        Integer[] tempMinimas = {11, 16, 18, 5, 1};
        TypedArray imagenesClima = getResources().obtainTypedArray(R.array.imagenes_clima);

        pronosticos.clear();

        for (int i = 0 ; i < dias.length ; ++i) {
            pronosticos.add(new Pronostico(dias[i], tempMaximas[i], tempMinimas[i], imagenesClima.getResourceId(0, 0)));
        }

        imagenesClima.recycle();

        pronosticoAdapter.notifyDataSetChanged();
    }

    public void refreshPronostico(View view) {
        mockearPronosticos();
    }

}
