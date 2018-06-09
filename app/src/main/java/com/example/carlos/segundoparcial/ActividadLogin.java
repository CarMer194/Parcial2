package com.example.carlos.segundoparcial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class ActividadLogin extends AppCompatActivity {
    Button boton;
    EditText usuario, contraseña;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_iniciarsesion);



    }
}
