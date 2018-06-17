package com.example.carlos.segundoparcial;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.carlos.segundoparcial.retrofits.ApiComu;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    String token, algo1,algo2;
    String[] juegos;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<ExpandedMenuModel> listDataHeader;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;
    AppCompatActivity appCompatActivity = this;
    boolean fragchange=false;
    ActualizarToken actualizarToken = new ActualizarToken();

    public class ActualizarToken extends Thread{
        ApiComu apiComu;
        @Override
        public synchronized void start() {
            super.start();
            while (true){

                retrofit2.Call<Token> call = apiComu.iniciarSesion(algo1,algo2);
                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(retrofit2.Call<Token> call, Response<Token> response) {
                        if (response.isSuccessful()){
                            if (response.body().getToken().equals(token)){
                                token=response.body().getToken();
                                Log.d("Actulizador","TOKEN actualizador: "+token);
                            }
                            else{
                                Log.d("Actualizador","Repetido");
                            }
                        }
                        else{
                            Log.d("Actualizador","fallo reponse");
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Token> call, Throwable t) {
                        Log.d("Actualizador","fallo onFailure");
                    }
                });
                try {
                    Thread.sleep(600000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        /*SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();*/

        token = sharedPreferences.getString("TOKEN","");
        if (token.equals("")){
            Log.d("Inicio","No token");
            Intent intent = new Intent(this, ActividadLogin.class);
            startActivityForResult(intent,1);
        }

        Log.d("Inicio","paso del token");
        System.out.println("ESTE ES EL TOKEN: "+token);

        expandableListView = findViewById(R.id.main_expandablelist);
        setlistaDeJuegos();

        expandableListAdapter = new ExpandableAdapter(appCompatActivity,listDataHeader, listDataChild,expandableListView);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        DrawerLayout drawerLayout = findViewById(R.id.main_drawer);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.openDrawer,R.string.closeDrawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        FramentoNoticiasGenerales noticiasGenerales = new FramentoNoticiasGenerales();

        NavigationView navigationView = findViewById(R.id.main_navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                DrawerLayout drawerLayout1 = findViewById(R.id.main_drawer);
                drawerLayout1.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        FramentoNoticiasGenerales fragmento = new FramentoNoticiasGenerales();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragmento);
        fragmentTransaction.commit();

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.d("CHILD CLICK","clic en: "+childPosition);
                Log.d("GROUP CLICK child", "clic en: "+childPosition);

                setNoticiasGenerales(childPosition+1);

                DrawerLayout drawerLayout1 = findViewById(R.id.main_drawer);
                drawerLayout1.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("GROUP CLICK ","ESTO:" + groupPosition);
                if (id==0){
                    FramentoNoticiasGenerales fragmento = new FramentoNoticiasGenerales();
                    System.out.println("ESTE ES EL TOKEN: "+token);
                    fragmento.setToken(token);
                    setFragmento(fragmento);
                    DrawerLayout drawerLayout1 = findViewById(R.id.main_drawer);
                    drawerLayout1.closeDrawer(GravityCompat.START);
                }
                if (id==1){
                    //iniciar favoritos
                }
                if (id==2){
                    setFragmento(new FragmentoAjustes());
                    DrawerLayout drawerLayout1 = findViewById(R.id.main_drawer);
                    drawerLayout1.closeDrawer(GravityCompat.START);
                }
                if (id==3){
                    setFragmento(new FragmentoJuegos());
                    DrawerLayout drawerLayout1 = findViewById(R.id.main_drawer);
                    drawerLayout1.closeDrawer(GravityCompat.START);
                }

                return false;
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

    public void setNoticiasGenerales(int i){
        FramentoNoticiasGenerales noticiasGenerales= new FramentoNoticiasGenerales();
        noticiasGenerales.setNoticiasg(i);
        noticiasGenerales.setToken(token);
        noticiasGenerales.setJuegos(juegos);
        setFragmento(noticiasGenerales);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1 && resultCode==1){
            token = data.getStringExtra("TOKEN");
            algo1=data.getStringExtra("Use");
            algo2=data.getStringExtra("CO");
            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            System.out.println();
            editor.putString("TOKEN",token);
            editor.commit();
            ViewModelUsuario viewModelUsuario = ViewModelProviders.of(this).get(ViewModelUsuario.class);
            viewModelUsuario.setUsuario(token);
            setlistaDeJuegos();
            fragchange=true;
            actualizarToken.start();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (fragchange){
            FramentoNoticiasGenerales fragmento = new FramentoNoticiasGenerales();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, fragmento);
            fragmentTransaction.commit();
        }


    }

    private void setlistaDeJuegos(){
        listDataHeader = new ArrayList<ExpandedMenuModel>();
        listDataChild = new HashMap<ExpandedMenuModel, List<String>>();

        ExpandedMenuModel item1 = new ExpandedMenuModel();
        item1.setIconName("Noticias Generales");
        item1.setIconImg(R.drawable.ic_public_black_24dp);
        listDataHeader.add(item1);

        ExpandedMenuModel item2 = new ExpandedMenuModel();
        item2.setIconName("Juegos");
        item2.setIconImg(R.drawable.ic_games_black_24dp);
        listDataHeader.add(item2);

        ExpandedMenuModel item3 = new ExpandedMenuModel();
        item3.setIconName("Ajustes");
        item3.setIconImg(R.drawable.ic_settings_black_24dp);
        listDataHeader.add(item3);

        ExpandedMenuModel item4 = new ExpandedMenuModel();
        item4.setIconName("Favoritos");
        item4.setIconImg(R.drawable.ic_favorite_black_24dp);
        listDataHeader.add(item4);

        System.out.println("Aqui va para el onchanged");
        ViewModelUsuario viewModelUsuario = ViewModelProviders.of(this).get(ViewModelUsuario.class);
        viewModelUsuario.getCategorias(token).observe(this, new Observer<String[]>() {
            @Override
            public void onChanged(@Nullable String[] strings) {
                juegos = strings != null ? strings.clone() : new String[0];
                List<String> heading2 = null;
                if (strings != null) {
                    heading2 = new ArrayList<>(Arrays.asList(strings));
                }

                listDataChild.put(listDataHeader.get(1), heading2);

                expandableListAdapter = new ExpandableAdapter(appCompatActivity,listDataHeader,listDataChild,expandableListView);

                expandableListView.setAdapter(expandableListAdapter);
            }
        });


    }


}
