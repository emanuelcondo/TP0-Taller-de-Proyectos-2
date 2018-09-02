package com.fiuba.tdp2.tp0.temperatura.vista;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fiuba.tdp2.tp0.temperatura.R;
import com.fiuba.tdp2.tp0.temperatura.dominio.PronosticoDelDia;

import java.util.ArrayList;
import java.util.Locale;

public class PronosticoDelDiaAdapter extends RecyclerView.Adapter<PronosticoDelDiaAdapter.ViewHolder> {

    private ArrayList<PronosticoDelDia> pronosticos;
    private Context contexto;

    public PronosticoDelDiaAdapter(Context contexto, ArrayList<PronosticoDelDia> pronosticos) {
        this.contexto = contexto;
        this.pronosticos = pronosticos;
    }

    @NonNull
    public PronosticoDelDiaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(contexto).inflate(R.layout.pronostico_item, parent, false));
    }

    public void onBindViewHolder(@NonNull PronosticoDelDiaAdapter.ViewHolder holder, int posicion) {
        PronosticoDelDia pronostico = pronosticos.get(posicion);
        Glide
                .with(contexto)
                .load(pronostico.getImagenDia())
                .into(holder.imagenClimaDiaView);
        Glide
                .with(contexto)
                .load(pronostico.getImagenNoche())
                .into(holder.imagenClimaNocheView);
        holder.bindTo(pronostico);
    }

    @Override
    public int getItemCount() {
        return pronosticos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView diaText;
        private TextView tempDiaText;
        private TextView tempNocheText;
        private ImageView imagenClimaDiaView;
        private ImageView imagenClimaNocheView;

        ViewHolder(View itemView) {
            super(itemView);

            diaText = itemView.findViewById(R.id.dia);
            tempDiaText = itemView.findViewById(R.id.tempDia);
            tempNocheText = itemView.findViewById(R.id.tempNoche);
            imagenClimaDiaView = itemView.findViewById(R.id.imagenDia);
            imagenClimaNocheView = itemView.findViewById(R.id.imagenNoche);
        }

        void bindTo(PronosticoDelDia pronostico){
            diaText.setText(pronostico.getNombreDia());

            if (pronostico.hayDataDelDia())
                tempDiaText.setText(String.format(Locale.getDefault(), "%2.1f" , pronostico.getTemperaturaDia()).concat("º"));
            else
                tempDiaText.setText("N/A");
            tempNocheText.setText(String.format(Locale.getDefault(), "%2.1f" , pronostico.getTemperaturaNoche()).concat("º"));
        }
    }

}
