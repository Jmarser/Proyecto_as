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
import android.widget.TextView;
import android.widget.Toast;

import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.adaptadores.AdaptadorAlumno;
import com.jmarser.proyecto_as.alumnos.interactor.AlumnosInteractorImpl;
import com.jmarser.proyecto_as.alumnos.presenter.AlumnosPresenter;
import com.jmarser.proyecto_as.alumnos.presenter.AlumnosPresenterImpl;
import com.jmarser.proyecto_as.fichasAlumno.view.FichasAlumnoFragment;
import com.jmarser.proyecto_as.model.Alumno;
import com.jmarser.proyecto_as.model.Profesor;
import com.jmarser.proyecto_as.model.Tutor;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;
import com.jmarser.proyecto_as.utils.Constantes;
import com.jmarser.proyecto_as.utils.NavigationFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class AlumnosFragment extends Fragment implements AlumnosView, AdaptadorAlumno.ItemClickListener {

    @BindView(R.id.rv_alumnos)
    RecyclerView rv_alumnos;
    @BindView(R.id.tv_empty_alumnos)
    TextView tv_empty_alumnos;

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
                adapter = new AdaptadorAlumno(profesor.getAlumnosProf(), getContext(), this);
            } else if (rol.equalsIgnoreCase(Constantes.ROL_TUTOR)) {
                adapter = new AdaptadorAlumno(tutor.getAlumnosTutor(), getContext(), this);
            }
            if(adapter != null) {
                if (adapter.getItemCount() != 0) {
                    rv_alumnos.setVisibility(View.VISIBLE);
                    rv_alumnos.setAdapter(adapter);
                    tv_empty_alumnos.setVisibility(View.INVISIBLE);
                }else{
                    rv_alumnos.setVisibility(View.INVISIBLE);
                    tv_empty_alumnos.setVisibility(View.VISIBLE);
                }
            }else{
                rv_alumnos.setVisibility(View.INVISIBLE);
                tv_empty_alumnos.setVisibility(View.VISIBLE);
            }
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

    @Override
    public void errorTutores(String mensaje) {
        Toasty.error(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void unknowError(String mensaje) {
        Toasty.error(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickListener(Alumno alumno) {
        NavigationFragment.replaceFragment(getActivity().getSupportFragmentManager(), FichasAlumnoFragment.newInstance(alumno), FichasAlumnoFragment.class.getName());
    }
}