package com.fiuba.tdp2.tp0.temperatura.vista;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.fiuba.tdp2.tp0.temperatura.MainActivity;
import com.fiuba.tdp2.tp0.temperatura.R;
import com.fiuba.tdp2.tp0.temperatura.dominio.City;

import java.util.ArrayList;
import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> implements Filterable {

    private ArrayList<City> filteredCities;
    private ArrayList<City> originalCities;
    private Context context;
    private ItemFilter mFilter = new ItemFilter();

    public CitiesAdapter(Context context, ArrayList<City> cities) {
        this.context = context;
        this.filteredCities = cities;
        this.originalCities = cities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.city_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        City city = filteredCities.get(i);
        viewHolder.city_id = city.getId();
        viewHolder.cityNameText.setText(city.getName());
        viewHolder.cityNameText.setTextColor(MainActivity.colorLetras);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return filteredCities.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityNameText;
        int city_id;

        ViewHolder(View itemView) {
            super(itemView);

            cityNameText = itemView.findViewById(R.id.city_name);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    String nom = cityNameText.getText().toString();
                    SharedPreferences pref = context.getSharedPreferences("TemperaturaTDP2", 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("idActual", city_id);
                    editor.putString("ciudadActual", nom);
                    editor.commit();
                    editor.apply();
                    MainActivity.idCiudadActual = city_id;
                    MainActivity.nombreCiudadActual = nom;
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<City> list = originalCities;
            ArrayList<City> nlist = new ArrayList<>();

            String filterableString;

            for (City city: list) {
                filterableString = city.getName().toLowerCase();
                if (filterableString.contains(filterString)) {
                    nlist.add(city);
                }
            }
            results.values = nlist;
            results.count = nlist.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredCities = (ArrayList<City>) results.values;
            notifyDataSetChanged();
        }
    }

}
