import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cm.Ponto;
import com.example.cm.R;
import com.example.cm.RecyclerActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {

    LayoutInflater inflater;
    List<Ponto> pontos;
    RecyclerActivity ra;

    public AdapterRecycler(Context ctx, List<Ponto> pontos) {
        this.inflater = LayoutInflater.from(ctx);
        this.pontos = pontos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tema.setText(pontos.get(position).getTema());
        holder.descricao.setText(pontos.get(position).getDescricao());
        //   holder.latitude.setText(pontos.get(position).getLatitude());
        //  holder.longitude.setText(pontos.get(position).getLongitude());


        Picasso.get().load(pontos.get(position).getImagem()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return pontos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tema, descricao;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                /*     Intent intent = new Intent(v.getContext(), PontoActivity.class);
                     Bundle params = new Bundle();
                     params.putString("Tema", Tema);
                     intent.putExtras(params);*/

                }
            });
            tema = itemView.findViewById(R.id.tema);
            img = itemView.findViewById(R.id.img);

        }
    }

}
