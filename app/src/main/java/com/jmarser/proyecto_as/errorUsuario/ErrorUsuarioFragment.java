package com.jmarser.proyecto_as.errorUsuario;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.splash.view.SplashActivity;
import com.jmarser.proyecto_as.utils.NavigationActivitis;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ErrorUsuarioFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.btn_error_user)
    Button btn_error_user;

    public ErrorUsuarioFragment() {
        // Required empty public constructor
    }

    public static ErrorUsuarioFragment newInstance() {
        ErrorUsuarioFragment fragment = new ErrorUsuarioFragment();

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
        View view = inflater.inflate(R.layout.fragment_error_usuario, container, false);
        ButterKnife.bind(this, view);

        btn_error_user.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        NavigationActivitis.pasarActividad(getActivity(),SplashActivity.class);
    }
}