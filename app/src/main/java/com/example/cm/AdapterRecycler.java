package com.example.cm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {

    LayoutInflater inflater;
    List<Ponto> pontos;

    public AdapterRecycler(Context ctx, List<Ponto> pontos) {
        this.inflater = LayoutInflater.from(ctx);
        this.pontos = pontos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycle_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tema.setText(pontos.get(position).getTema());
        Picasso.get().load(pontos.get(position).getImagem()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return pontos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tema;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tema = itemView.findViewById(R.id.tema);
            img = itemView.findViewById(R.id.img);

        }
    }

}
