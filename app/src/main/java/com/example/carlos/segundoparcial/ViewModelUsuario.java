package com.example.carlos.segundoparcial;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.telecom.Call;
import android.util.Log;

import com.example.carlos.segundoparcial.base_de_datos.BaseNoticias;
import com.example.carlos.segundoparcial.base_de_datos.NoticiasDao;
import com.example.carlos.segundoparcial.base_de_datos.UsuarioDao;
import com.example.carlos.segundoparcial.retrofits.ApiComu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModelUsuario extends AndroidViewModel {

    private NoticiasDao noticiasDao;
    private UsuarioDao usuarioDao;
    private LiveData<List<Noticias>> noticias;
    private ApiComu apiComu;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<Noticias> noticiasList;


    public ViewModelUsuario(@NonNull Application application) {
        super(application);
        BaseNoticias db = BaseNoticias.getDatabase(application);
        usuarioDao = db.userDao();
        noticiasDao = db.noticiasDao();


    }

    public LiveData<String> login(String user, String password){
        final MutableLiveData<String> token = new MutableLiveData<>();
        retrofit2.Call<Token> call = apiComu.iniciarSesion(user,password);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(retrofit2.Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()){
                    token.setValue(response.body().getToken());
                }
                else{
                    Log.d("API","NEL");
                }
            }
            @Override
            public void onFailure(retrofit2.Call<Token> call, Throwable t) {
                Log.d("API","Error");
            }
        });
        return token;
    }

    private void iniciarApi(){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(Noticias.class, new NoticiaDeserializer)
    }
}
