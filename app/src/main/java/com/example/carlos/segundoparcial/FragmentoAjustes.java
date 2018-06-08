package com.example.carlos.segundoparcial;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class FragmentoAjustes extends Fragment {
    ImageView imagen;
    Button boton;
    EditText usuario,contraseña,nuevacontra;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_ajustes,container,false);

        imagen = view.findViewById(R.id.ajustes_imagen);
        boton = view.findViewById(R.id.ajustes_boton);
        usuario = view.findViewById(R.id.ajustes_usuario);
        contraseña = view.findViewById(R.id.ajustes_contra);
        nuevacontra = view.findViewById(R.id.ajustes_nuevacontra);

        //boton.setOnClickListener();
        //Aqui va a ir transaccion a la nueva info


        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
