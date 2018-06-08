package com.example.carlos.segundoparcial.base_de_datos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.carlos.segundoparcial.Usuario;

@Dao
public interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertar(Usuario usuario);

    @Update
    void actualizar(Usuario usuario);

    @Delete
    void eliminar(Usuario usuario);

}
