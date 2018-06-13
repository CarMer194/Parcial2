package com.example.carlos.segundoparcial;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.carlos.segundoparcial.Deserializer.Deserializer_Noticia;
import com.example.carlos.segundoparcial.Deserializer.Deserializer_Token;
import com.example.carlos.segundoparcial.Deserializer.Deserializer_Usuario;
import com.example.carlos.segundoparcial.base_de_datos.BaseNoticias;
import com.example.carlos.segundoparcial.base_de_datos.NoticiasDao;
import com.example.carlos.segundoparcial.base_de_datos.UsuarioDao;
import com.example.carlos.segundoparcial.retrofits.ApiComu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositorioDatos {
    private NoticiasDao noticiasDao;
    private UsuarioDao usuarioDao;
    private LiveData<List<Noticias>> noticias;
    private ApiComu apiComu;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<Noticias> noticiasList;

    public RepositorioDatos(Application application) {
        BaseNoticias db = BaseNoticias.getDatabase(application);
        usuarioDao = db.userDao();
        noticiasDao = db.noticiasDao();
        iniciarApi();
    }

    private void iniciarApi(){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(Noticias.class, new Deserializer_Noticia())
                .registerTypeAdapter(Usuario.class, new Deserializer_Usuario())
                .registerTypeAdapter(Token.class, new Deserializer_Token())
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiComu.ENDPOINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiComu = retrofit.create(ApiComu.class);
    }

    public LiveData<String> login(String user, String password){
        final MutableLiveData<String> token = new MutableLiveData<>();
        retrofit2.Call<Token> call = apiComu.iniciarSesion(user,password);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(retrofit2.Call<Token> call, retrofit2.Response<Token> response) {
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

    public LiveData<List<Noticias>> getNoticias(String token) {
        Call<List<Noticias>> call = apiComu.getNoticias("Bearer "+token);
        final MutableLiveData<List<Noticias>> mutableLiveData = new MutableLiveData<>();
        System.out.println("ESTE ES EL TOKEN EN GET NOTICIAS: "+token);
        call.enqueue(new Callback<List<Noticias>>() {
            @Override
            public void onResponse(Call<List<Noticias>> call, retrofit2.Response<List<Noticias>> response) {
                if (response.isSuccessful()){
                    System.out.println("por aqui entro al succesful");
                    mutableLiveData.setValue(response.body());
                    noticias = mutableLiveData;
                }
                else{
                    System.out.println("NO RESPONDIO");
                    Log.d("Noticias", "nel");
                }
            }

            @Override
            public void onFailure(Call<List<Noticias>> call, Throwable t) {
                System.out.println("FALLO LA COMU");
                Log.d("Noticias","fail :(");
                System.out.println(t.getMessage());
            }
        });

        return mutableLiveData;
    }

    public void setToken(String token){

    }

    public void setUsuarioDao(String token){
        Call<Usuario> call = apiComu.getUsuario(token);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, retrofit2.Response<Usuario> response) {
                if (response.isSuccessful()){
                usuarioDao.insertar(response.body());}
                else {
                    Log.d("Usuario","fallo1");
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.d("Usuario","fallo2");
            }
        });

    }

}
