package com.example.medical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.MyViewHolder>{
    LayoutInflater inflater;
    List<Appointment> appointments;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,date,time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            time=itemView.findViewById(R.id.time);
            date=itemView.findViewById(R.id.date);
        }
    }

    public AppointmentsAdapter(Context ctx, List<Appointment> appointments){
        this.appointments=appointments;
        this.inflater = LayoutInflater.from(ctx);

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rendez_vous_recycler_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.date.setText("Date: "+appointments.get(position).getDate().toString());
        holder.title.setText("Titre: "+appointments.get(position).getTitle());
        holder.time.setText("Time: "+appointments.get(position).getTime().toString());
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }



}
