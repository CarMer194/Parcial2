package com.example.carlos.segundoparcial;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModelUsuario extends AndroidViewModel {

    private RepositorioDatos repositorioDatos;
    private LiveData<List<Noticias>> noticias;
    private List<Noticias> noticiasList;
    private LiveData<String> token;

    public ViewModelUsuario(@NonNull Application application) {
        super(application);
        repositorioDatos = new RepositorioDatos(application);
    }

    public void setTokenUsuario() {
    }

    public void iniciarToken(String usuario, String contraseña){
        if (this.token != null){
            return;
        }
        token = repositorioDatos.login(usuario,contraseña);
    }

    public LiveData<List<Noticias>> getNoticiasList(String token){
        return repositorioDatos.getNoticias(token);
    }

    public LiveData<String> getToken() {
        return token;
    }

    public void setUsuario(String tok){
        repositorioDatos.setUsuarioDao(tok);
    }

    public LiveData<List<Noticias>> getNoticiasJuego(String token,String juego) {
        return repositorioDatos.getNoticiasJuego(token,juego);
    }

    public LiveData<String[]> getCategorias(String token) {
        return repositorioDatos.getCategorias(token);
    }
}
