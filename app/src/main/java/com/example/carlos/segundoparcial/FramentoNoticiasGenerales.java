package com.example.carlos.segundoparcial;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FramentoNoticiasGenerales extends android.support.v4.app.Fragment {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.framento_recycler_noticias_generales,
                container,
                false);

        AdapterNoticiasGenerales adapter = new AdapterNoticiasGenerales(getContext());
        recyclerView = view.findViewById(R.id.recycler_noticias_generales);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 3 == 0 ? 2 : 1);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        //FALTA mantener token

        return view;
    }
}
