package com.example.carlos.segundoparcial;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FramentoNoticiasGenerales extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.framento_recycler_noticias_generales,
                container,
                false);


        return view;
    }
}
