package com.example.cm;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.*;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Ponto> pontos;
    private OnNoteListener onNoteListener;
    Context context;


    public ArrayList<Ponto> getPonto() {
        return (ArrayList<Ponto>) this.pontos;
    }

    public RecyclerAdapter(List<Ponto> pontos, Context context, OnNoteListener onNoteListener) {
        this.pontos = pontos;
        this.context = context;
        this.onNoteListener = onNoteListener;
    }

    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.recycler_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(v, onNoteListener);
        return new ViewHolder(v, onNoteListener);
    }

    //carrega dados do WS
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {

        holder.tema.setText(pontos.get(position).getTema());
        holder.descricao.setText(pontos.get(position).getDescricao());
        holder.latitude.setText(String.valueOf(pontos.get(position).getLatitude()));
        holder.longitude.setText(String.valueOf(pontos.get(position).getLongitude()));
        //  String Imagem = pontos.get(position).getImagem();
        //  Picasso.with(context).load(VolleySingleton.URL + Imagem).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return pontos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView tema, descricao, longitude, latitude;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            //  img = itemView.findViewById(R.id.img);
            tema = itemView.findViewById(R.id.tema);
            descricao = itemView.findViewById(R.id.descricao);
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }

}
