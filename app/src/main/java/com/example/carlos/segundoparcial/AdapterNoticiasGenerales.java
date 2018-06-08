package com.example.carlos.segundoparcial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterNoticiasGenerales extends RecyclerView.Adapter<AdapterNoticiasGenerales.ViewHolder> {
    Context context;
    List<Noticias> noticias;

    public AdapterNoticiasGenerales(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.noticia_card,parent,false);
        return new AdapterNoticiasGenerales.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Noticias noticia = noticias.get(position);

        //Se agregan los elementos de las noticias
    }

    @Override
    public int getItemCount() {
        if (noticias == null){
            return 0;
        }
        else {
            return noticias.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titulo,nota;
        ImageView imagen;
        ImageButton botonestrella;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.card_titulo);
            nota = itemView.findViewById(R.id.card_nota);
            imagen = itemView.findViewById(R.id.card_imagen_noticia);
            botonestrella=itemView.findViewById(R.id.card_boton_estrella);

        }
    }
}
