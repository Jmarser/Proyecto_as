package com.jmarser.proyecto_as.ficha.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmarser.proyecto_as.R;

public class FichaFragment extends Fragment {

    public FichaFragment() {
        // Required empty public constructor
    }

    public static FichaFragment newInstance(String param1, String param2) {
        FichaFragment fragment = new FichaFragment();

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
        View view = inflater.inflate(R.layout.fragment_ficha, container, false);
        return view;
    }
}