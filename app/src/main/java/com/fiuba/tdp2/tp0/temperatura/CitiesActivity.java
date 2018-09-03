package com.fiuba.tdp2.tp0.temperatura;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.fiuba.tdp2.tp0.temperatura.dominio.City;
import com.fiuba.tdp2.tp0.temperatura.vista.CitiesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CitiesActivity extends AppCompatActivity {
    private RecyclerView rv;
    private EditText et;
    private Toolbar toolbar;
    private ArrayList<City> cities;

    private CitiesAdapter citiesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        this.findViewById(R.id.rel).setBackgroundColor(MainActivity.colorFondo);

        toolbar = findViewById(R.id.citiestoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cities = this.loadCities();
        citiesArrayAdapter = new CitiesAdapter(getApplicationContext(), cities);

        rv = findViewById(R.id.recyclerViewCities);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(citiesArrayAdapter);

        Log.d("AD", Integer.toString(cities.size()));

        et = findViewById(R.id.myEditText);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                citiesArrayAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public ArrayList<City> loadCities() {
        String json;
        ArrayList<City> _cities = new ArrayList<>();

        try {
            InputStream is = getAssets().open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int id = obj.getInt("id");
                String name = obj.getString("name");
                _cities.add(new City(id, name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return _cities;
    }
}
