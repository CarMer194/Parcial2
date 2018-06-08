package com.example.carlos.segundoparcial;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "tabla_usuario")
public class Usuario {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id_usuario")
    private String id;

    @ColumnInfo(name = "nombre_usuario")
    private String nombre;

    @ColumnInfo(name = "contraseña_usuario")
    private String contraseña;

    @ColumnInfo(name = "noticias_favortias_usuarios")
    private String noticiafavorita;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNoticiafavorita() {
        return noticiafavorita;
    }

    public void setNoticiafavorita(String noticiafavorita) {
        this.noticiafavorita = noticiafavorita;
    }
}
