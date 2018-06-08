package com.example.carlos.segundoparcial.base_de_datos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.carlos.segundoparcial.Noticias;

import java.util.List;

@Dao
public interface NoticiasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Noticias... noticias);

    @Query("SELECT * FROM tabla_noticias")
    LiveData<List<Noticias>> getNoticias();

}
