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
    private int id;

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

}
