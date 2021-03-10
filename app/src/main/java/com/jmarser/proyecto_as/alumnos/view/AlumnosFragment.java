package com.jmarser.proyecto_as.alumnos.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmarser.proyecto_as.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlumnosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlumnosFragment extends Fragment {

    public AlumnosFragment() {
        // Required empty public constructor
    }

    public static AlumnosFragment newInstance() {
        AlumnosFragment fragment = new AlumnosFragment();

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
        View view = inflater.inflate(R.layout.fragment_alumnos, container, false);

        return view;
    }
}