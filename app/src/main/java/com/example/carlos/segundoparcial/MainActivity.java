package com.example.carlos.segundoparcial;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        token = sharedPreferences.getString("TOKEN","");
        if (token.equals("")){
            Log.d("Inicio","No token");
            Intent intent = new Intent(this, ActividadLogin.class);
            startActivityForResult(intent,1);
        }
        Log.d("Inicio","paso del token");
        System.out.println("ESTE ES EL TOKEN: "+token);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.main_drawer);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.openDrawer,R.string.closeDrawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.main_navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();

                if (id==R.id.menu_noticias_generales){
                    FramentoNoticiasGenerales fragmento = new FramentoNoticiasGenerales();
                    System.out.println("ESTE ES EL TOKEN: "+token);
                    fragmento.setToken(token);
                    setFragmento(fragmento);
                }
                if (id==R.id.menu_favoritos){
                    //iniciar favoritos
                }
                if (id==R.id.menu_ajustes){
                    setFragmento(new FragmentoAjustes());
                }
                if (id==R.id.menu_juegos){
                    setFragmento(new FragmentoJuegos());
                }
                DrawerLayout drawerLayout1 = findViewById(R.id.main_drawer);
                drawerLayout1.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    private void setFragmento(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_action){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.main_drawer);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1 && resultCode==1){
            token = data.getStringExtra("TOKEN");
            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            System.out.println();
            editor.putString("TOKEN",token);
            editor.commit();
            ViewModelUsuario viewModelUsuario = ViewModelProviders.of(this).get(ViewModelUsuario.class);
            viewModelUsuario.setUsuario(token);
        }
    }


}
