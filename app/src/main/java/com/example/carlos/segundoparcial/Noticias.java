package com.example.carlos.segundoparcial;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "tabla_noticias")
public class Noticias {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id_noticia")
    private String id;

     @ColumnInfo(name = "titulo_noticia")
    private String titulo;

     @ColumnInfo(name = "imagen_noticia")
    private String imagen;

     @ColumnInfo(name = "fecha_noticia")
    private String fecha;

     @ColumnInfo(name = "resumen_noticia")
    private String resumen;

     @ColumnInfo(name = "contenido_noticia")
    private String contenido;

     @ColumnInfo(name = "juego_noticia")
    private String juego;


    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getJuego() {
        return juego;
    }

    public void setJuego(String juego) {
        this.juego = juego;
    }
}
