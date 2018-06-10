package com.example.carlos.segundoparcial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.List;

public class AdapterNoticiasGenerales extends RecyclerView.Adapter<AdapterNoticiasGenerales.ViewHolder> {
    Context context;
    List<Noticias> noticias;

    public AdapterNoticiasGenerales(Context context) {
        this.context = context;
    }

    public AdapterNoticiasGenerales(Context context, List<Noticias> noticias) {
        this.context = context;
        this.noticias = noticias;
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
        holder.titulo.setText(noticia.getTitulo());
        holder.nota.setText(noticia.getContenido());
        if (noticia.getImagen()!=null){
            new DownloadImageTask(holder.imagen).execute(noticia.getImagen());
        }

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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public void setList(List<Noticias> noticias){
        this.noticias = noticias;
        notifyDataSetChanged();
    }


}
