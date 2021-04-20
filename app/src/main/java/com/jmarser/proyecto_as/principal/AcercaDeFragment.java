package com.jmarser.proyecto_as.principal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmarser.proyecto_as.R;

import butterknife.ButterKnife;

public class AcercaDeFragment extends Fragment {


    public AcercaDeFragment() {
        // Required empty public constructor
    }

    public static AcercaDeFragment newInstance() {
        AcercaDeFragment fragment = new AcercaDeFragment();

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
        View view = inflater.inflate(R.layout.fragment_acerca_de, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}