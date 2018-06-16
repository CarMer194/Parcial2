package com.example.carlos.segundoparcial.base_de_datos;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.carlos.segundoparcial.Noticias;
import com.example.carlos.segundoparcial.Usuario;

@Database(entities = {Usuario.class, Noticias.class}, version = 2, exportSchema = false)
public abstract class BaseNoticias extends RoomDatabase {
    public abstract UsuarioDao userDao();
    public abstract NoticiasDao noticiasDao();

    private static BaseNoticias INSTANCE;

    public static BaseNoticias getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (BaseNoticias.class){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BaseNoticias.class,"base_de_noticias").build();
                }
            }
        }
        return INSTANCE;
    }

}
