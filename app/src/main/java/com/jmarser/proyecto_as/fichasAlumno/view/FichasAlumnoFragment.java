package com.jmarser.proyecto_as.fichasAlumno.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.adaptadores.AdaptadorAlumno;
import com.jmarser.proyecto_as.adaptadores.AdaptadorFicha;
import com.jmarser.proyecto_as.fichasAlumno.interactor.FichasAlumnoInteractorImpl;
import com.jmarser.proyecto_as.fichasAlumno.presenter.FichasAlumnoPresenter;
import com.jmarser.proyecto_as.fichasAlumno.presenter.FichasAlumnoPresenterImpl;
import com.jmarser.proyecto_as.model.Alumno;
import com.jmarser.proyecto_as.model.Ficha;
import com.jmarser.proyecto_as.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FichasAlumnoFragment extends Fragment implements FichasAlumnoView {

    @BindView(R.id.tv_nombre_alumno_fichas)
    TextView tv_nombre_alumno;
    @BindView(R.id.pb_horas_alumno)
    ProgressBar pb_horas_alumno;
    @BindView(R.id.tv_info_horas_totales)
    TextView tv_horas_totales;
    @BindView(R.id.rv_fichas_alumno)
    RecyclerView rv_fichas_alumno;



    private FichasAlumnoPresenter presenter;
    private Alumno alumno = null;
    private AdaptadorFicha adaptadorFicha;

    public FichasAlumnoFragment() {
        // Required empty public constructor
    }

    public static FichasAlumnoFragment newInstance() {
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

        //rv_fichas_alumno = view.findViewById(R.id.rv_fichas_alumno);
        ButterKnife.bind(this, view);

        presenter = new FichasAlumnoPresenterImpl(this, new FichasAlumnoInteractorImpl(getContext()), getContext());
        presenter.getAlumno();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        rv_fichas_alumno.setHasFixedSize(true);
        rv_fichas_alumno.setLayoutManager(new LinearLayoutManager(getActivity()));

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
        if (alumno != null) {
            initView();
            initRecycler();
        }

    }

    private void initView() {
        pb_horas_alumno.setMax(Constantes.HORAS_PRACTICAS);
        tv_nombre_alumno.setText(alumno.toString());
        int horas = 0;
        for(int i = 0; i<alumno.getFichas().size(); i++){
            horas += alumno.getFichas().get(i).getHoras();
        }
        pb_horas_alumno.setProgress(horas);
        tv_horas_totales.setText(horas + "/" + Constantes.HORAS_PRACTICAS);
    }

    private void initRecycler() {
        adaptadorFicha = new AdaptadorFicha(alumno.getFichas(), getContext());
        rv_fichas_alumno.setAdapter(adaptadorFicha);
    }
}