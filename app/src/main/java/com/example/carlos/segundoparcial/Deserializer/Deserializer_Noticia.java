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
        noticias.setId(jsonObject.get("_id").getAsString());
        noticias.setTitulo(jsonObject.get("title").getAsString());
        noticias.setContenido(jsonObject.get("body").getAsString());
        noticias.setImagen(jsonObject.get("coverImage").getAsString());
        noticias.setFecha(jsonObject.get("created_date").getAsString());
        noticias.setResumen(jsonObject.get("description").getAsString());
        return noticias;
    }
}
