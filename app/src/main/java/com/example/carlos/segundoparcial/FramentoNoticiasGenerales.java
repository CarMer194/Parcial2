package com.example.carlos.segundoparcial;

import android.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FramentoNoticiasGenerales extends android.support.v4.app.Fragment {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    ViewModelUsuario viewModelUsuario;
    List<Noticias> noticiasList;
    String token="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.framento_recycler_noticias_generales,
                container,
                false);
        viewModelUsuario = ViewModelProviders.of(this).get(ViewModelUsuario.class);

        recyclerView = view.findViewById(R.id.recycler_noticias_generales);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 3 == 0 ? 2 : 1);
            }
        });
        final AdapterNoticiasGenerales adapter = new AdapterNoticiasGenerales(getContext(),noticiasList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        viewModelUsuario.getNoticiasList(token).observe(this, new Observer<List<Noticias>>() {
            @Override
            public void onChanged(@Nullable List<Noticias> noticias) {
                adapter.setList(noticias);
            }
        });
        System.out.println("por aqui paso del call");

        return view;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
