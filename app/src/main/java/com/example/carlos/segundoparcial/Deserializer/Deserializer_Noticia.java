package com.example.carlos.segundoparcial.Deserializer;

import com.example.carlos.segundoparcial.Noticias;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Deserializer_Noticia implements JsonDeserializer<Noticias>{
    @Override
    public Noticias deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Noticias noticias = new Noticias();
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.get("_id") != null){
            noticias.setId(jsonObject.get("_id").getAsString());
        }
        if (jsonObject.get("title")!= null){
            noticias.setTitulo(jsonObject.get("title").getAsString());
        }
        if (jsonObject.get("body")!= null){
            noticias.setContenido(jsonObject.get("body").getAsString());
        }
        if (jsonObject.get("coverImage")!=null){
        noticias.setImagen(jsonObject.get("coverImage").getAsString());
        }
        if (jsonObject.get("created_date")!=null){
            noticias.setFecha(jsonObject.get("created_date").getAsString());
        }
        if (jsonObject.get("description")!=null){
            noticias.setResumen(jsonObject.get("description").getAsString());
        }
        return noticias;
    }
}
