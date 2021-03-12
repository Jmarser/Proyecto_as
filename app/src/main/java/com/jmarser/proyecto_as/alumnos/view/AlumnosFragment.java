package com.jmarser.proyecto_as.alumnos.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.adaptadores.AdaptadorAlumno;
import com.jmarser.proyecto_as.alumnos.interactor.AlumnosInteractorImpl;
import com.jmarser.proyecto_as.alumnos.presenter.AlumnosPresenter;
import com.jmarser.proyecto_as.alumnos.presenter.AlumnosPresenterImpl;
import com.jmarser.proyecto_as.model.Profesor;
import com.jmarser.proyecto_as.model.Tutor;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;
import com.jmarser.proyecto_as.utils.Constantes;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlumnosFragment extends Fragment implements AlumnosView {

    @BindView(R.id.rv_alumnos)
    RecyclerView rv_alumnos;

    private AlumnosPresenter presenter;
    private AdaptadorAlumno adapter;
    private Profesor profesor = null;
    private Tutor tutor = null;
    String rol = SharedPrefManager.getInstance(getContext()).getUsuario().getRol();

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
        ButterKnife.bind(this, view);

        presenter = new AlumnosPresenterImpl(this, new AlumnosInteractorImpl(getContext()), getContext());

        initView();

        return view;
    }

    private void initView() {
        if (rol.equalsIgnoreCase(Constantes.ROL_PROFESOR)) {
            presenter.getProfesor();
        } else if (rol.equalsIgnoreCase(Constantes.ROL_TUTOR)) {
            presenter.getTutor();
        }
    }

    private void initRecycler(){
        if (rol.equalsIgnoreCase(Constantes.ROL_PROFESOR)) {
            adapter = new AdaptadorAlumno(profesor.getAlumnosProf(),getContext());
        } else if (rol.equalsIgnoreCase(Constantes.ROL_TUTOR)) {
            adapter = new AdaptadorAlumno(tutor.getAlumnosTutor(), getContext());
        }
        rv_alumnos.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rv_alumnos.setHasFixedSize(true);
        rv_alumnos.setLayoutManager(new LinearLayoutManager(getActivity()));

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
        if(profesor!= null){
            initRecycler();
        }
    }

    @Override
    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
        if(tutor!= null){
            initRecycler();
        }
    }
}