package com.example.carlos.segundoparcial.retrofits;

import com.example.carlos.segundoparcial.Noticias;
import com.example.carlos.segundoparcial.Token;
import com.example.carlos.segundoparcial.Usuario;

import java.util.List;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiComu {

    String ENDPOINT = "http://gamenewsuca.herokuapp.com";

    @FormUrlEncoded
    @POST("/login")
    Call<Token> iniciarSesion(@Field("user") String string,@Field("password") String contra);


    @POST
    Single<ResponseBody> agregarUsuario(@Url String url, @Body Usuario usuario);

    @GET("/news")
    Call<List<Noticias>> getNoticias(@Header("Authorization") String token);

    @GET("/users/detail")
    Call<Usuario> getUsuario(@Header("Authorization") String token);

    /*@FormUrlEncoded
    @GET("/users/detail")*/

}
