package com.jmarser.proyecto_as.cuenta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmarser.proyecto_as.R;

public class CuentaFragment extends Fragment {


    public CuentaFragment() {
        // Required empty public constructor
    }

    public static CuentaFragment newInstance() {
        CuentaFragment fragment = new CuentaFragment();

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
        View view = inflater.inflate(R.layout.fragment_cuenta, container, false);

        return view;
    }
}