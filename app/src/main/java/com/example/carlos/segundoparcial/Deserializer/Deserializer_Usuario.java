package com.example.carlos.segundoparcial.Deserializer;

import com.example.carlos.segundoparcial.Usuario;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Deserializer_Usuario  implements JsonDeserializer<Usuario>{


    @Override
    public Usuario deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Usuario usuario = new Usuario();
        JsonObject jsonObject = json.getAsJsonObject();
        usuario.setId(jsonObject.get("_id").getAsString());
        usuario.setNombre(jsonObject.get("user").getAsString());
        usuario.setContraseña(jsonObject.get("password").getAsString());
        JsonElement jsonElement = jsonObject.getAsJsonObject("favoriteNews");
        return usuario;
    }
}
