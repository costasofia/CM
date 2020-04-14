package com.example.cm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.*;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Ponto> pontos;
    private OnNoteListener onNoteListener;
    Context context;

    public RecyclerAdapter(List<Ponto> pontos, Context context, OnNoteListener onNoteListener) {
        this.context = context;
        this.pontos = pontos;
        this.onNoteListener = onNoteListener;
    }

    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.recycler_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(v, onNoteListener);
        return new ViewHolder(v, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Ponto ponto = pontos.get(position);
        // holder.tema.setText(ponto.getTema());
        holder.tema.setText(pontos.get(position).getTema());
        String imagem = ponto.getImagem();
        Picasso.get().load(VolleySingleton.URL + imagem).into(holder.img);

    }


    @Override
    public int getItemCount() {
        return pontos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView tema;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tema = itemView.findViewById(R.id.tema);
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
