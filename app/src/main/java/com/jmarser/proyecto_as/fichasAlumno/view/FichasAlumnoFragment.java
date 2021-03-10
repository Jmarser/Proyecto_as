package com.jmarser.proyecto_as.fichasAlumno.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmarser.proyecto_as.R;

public class FichasAlumnoFragment extends Fragment {


    public FichasAlumnoFragment() {
        // Required empty public constructor
    }

    public static FichasAlumnoFragment newInstance(String param1, String param2) {
        FichasAlumnoFragment fragment = new FichasAlumnoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fichas_alumno, container, false);


        return view;
    }
}