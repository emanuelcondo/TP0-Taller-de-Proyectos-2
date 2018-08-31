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
import com.fiuba.tdp2.tp0.temperatura.dominio.Pronostico;

import java.util.ArrayList;

public class PronosticoAdapter extends RecyclerView.Adapter<PronosticoAdapter.ViewHolder> {

    private ArrayList<Pronostico> pronosticos;
    private Context contexto;

    public PronosticoAdapter(Context contexto, ArrayList<Pronostico> pronosticos) {
        this.contexto = contexto;
        this.pronosticos = pronosticos;
    }

    @NonNull
    public PronosticoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(contexto).inflate(R.layout.pronostico_item, parent, false));
    }

    public void onBindViewHolder(@NonNull PronosticoAdapter.ViewHolder holder, int posicion) {
        Pronostico pronostico = pronosticos.get(posicion);
        Glide.with(contexto).load(pronostico.getImagen()).into(holder.imagenClimaView);
        holder.bindTo(pronostico);
    }

    @Override
    public int getItemCount() {
        return pronosticos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView diaText;
        private TextView tempMaxText;
        private TextView tempMinText;
        private ImageView imagenClimaView;

        ViewHolder(View itemView) {
            super(itemView);

            diaText = itemView.findViewById(R.id.dia);
            tempMaxText = itemView.findViewById(R.id.tempMax);
            tempMinText = itemView.findViewById(R.id.tempMin);
            imagenClimaView = itemView.findViewById(R.id.imagenPronostico);
        }

        void bindTo(Pronostico pronostico){
            diaText.setText(pronostico.getDia());
            tempMaxText.setText(Integer.toString(pronostico.getTemperaturaMaxima()).concat("º"));
            tempMinText.setText(Integer.toString(pronostico.getTemperaturaMinima()).concat("º"));
        }
    }

}
