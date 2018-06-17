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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    String token;
    List<String> juegos;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<ExpandedMenuModel> listDataHeader;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;
    AppCompatActivity appCompatActivity = this;
    boolean fragchange=false;



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

        expandableListView = findViewById(R.id.main_expandablelist);
        setlistaDeJuegos();

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
                /*int id= item.getItemId();

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
                }*/
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
                Log.d("ExpandableList","clic en: "+childPosition);

                setNoticiasGenerales();

                DrawerLayout drawerLayout1 = findViewById(R.id.main_drawer);
                drawerLayout1.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("GROUP CLICK ","ESTO:" + groupPosition);
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

    public void setNoticiasGenerales(){
        FragmentoJuegos fragmentoJuegos = new FragmentoJuegos();
        setFragmento(fragmentoJuegos);
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
            setlistaDeJuegos();
            fragchange=true;
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
        item1.setIconName("News");
        item1.setIconImg(R.drawable.ic_public_black_24dp);
        // Adding data header
        listDataHeader.add(item1);

        ExpandedMenuModel item2 = new ExpandedMenuModel();
        item2.setIconName("Games");
        item2.setIconImg(R.drawable.ic_games_black_24dp);
        listDataHeader.add(item2);

        ExpandedMenuModel item3 = new ExpandedMenuModel();
        item3.setIconName("Settings");
        item3.setIconImg(R.drawable.ic_settings_black_24dp);
        listDataHeader.add(item3);

        ExpandedMenuModel item4 = new ExpandedMenuModel();
        item4.setIconName("Favoritos");
        item4.setIconImg(R.drawable.ic_favorite_black_24dp);
        listDataHeader.add(item4);

        System.out.println("Aqui va para el onchanged");
        ViewModelUsuario viewModelUsuario = ViewModelProviders.of(this).get(ViewModelUsuario.class);
        viewModelUsuario.getCategorias(token).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                Log.d("Expandable Adapter", "por aqui pasa el onchange");
            juegos=strings;
            listDataChild.put(listDataHeader.get(1),juegos);

            expandableListAdapter = new ExpandableAdapter(appCompatActivity,listDataHeader, listDataChild,expandableListView);



            }
        });


    }


}
