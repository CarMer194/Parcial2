package com.example.carlos.segundoparcial;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActividadLogin extends AppCompatActivity {
    Button boton;
    EditText usuario, contra;
    ViewModelUsuario viewModelUsuario;
    AppCompatActivity appCompatActivity = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_iniciarsesion);
        viewModelUsuario= ViewModelProviders.of(this).get(ViewModelUsuario.class);
        usuario = findViewById(R.id.inicio_usuario);
        contra =findViewById(R.id.inicio_contraseña);
        boton = findViewById(R.id.inicio_boton);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModelUsuario.iniciarToken(usuario.getText().toString(), contra.getText().toString());
                viewModelUsuario.getToken().observe(appCompatActivity, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("TOKEN",s);
                        editor.commit();
                        Intent intent = new Intent();
                        intent.putExtra("TOKEN",s);
                        setResult(1,intent);
                        finish();
                    }
                });
            }
        });


    }
}