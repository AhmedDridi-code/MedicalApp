package com.example.medical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    LayoutInflater inflater;
    List<Medicament> medicaments;

    public Adapter(Context ctx, List<Medicament> medicaments){
        this.medicaments=medicaments;
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.quantite.setText("Quantité: "+medicaments.get(position).getQuantite());
        holder.medicament.setText("Medicament: "+medicaments.get(position).getMedicaments());
        holder.date.setText("Date: "+medicaments.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return medicaments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView medicament,date,quantite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            medicament=itemView.findViewById(R.id.medicaments);
            quantite=itemView.findViewById(R.id.quantite);
            date=itemView.findViewById(R.id.date);
        }
    }

}
