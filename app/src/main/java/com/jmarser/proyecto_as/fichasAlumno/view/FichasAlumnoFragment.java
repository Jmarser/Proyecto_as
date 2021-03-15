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
import com.jmarser.proyecto_as.ficha.view.FichaFragment;
import com.jmarser.proyecto_as.fichasAlumno.interactor.FichasAlumnoInteractorImpl;
import com.jmarser.proyecto_as.fichasAlumno.presenter.FichasAlumnoPresenter;
import com.jmarser.proyecto_as.fichasAlumno.presenter.FichasAlumnoPresenterImpl;
import com.jmarser.proyecto_as.model.Alumno;
import com.jmarser.proyecto_as.model.Ficha;
import com.jmarser.proyecto_as.utils.Constantes;
import com.jmarser.proyecto_as.utils.NavigationFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FichasAlumnoFragment extends Fragment implements FichasAlumnoView, AdaptadorFicha.ItemClickListener {

    @BindView(R.id.tv_nombre_alumno_fichas)
    TextView tv_nombre_alumno;
    @BindView(R.id.pb_horas_alumno)
    ProgressBar pb_horas_alumno;
    @BindView(R.id.tv_info_horas_totales)
    TextView tv_horas_totales;
    @BindView(R.id.rv_fichas_alumno)
    RecyclerView rv_fichas_alumno;
    @BindView(R.id.tv_empty_fichas)
    TextView tv_empty_fichas;



    private FichasAlumnoPresenter presenter;
    private Alumno alumno = null;
    private AdaptadorFicha adaptadorFicha;

    public FichasAlumnoFragment() {
        // Required empty public constructor
    }

    public static FichasAlumnoFragment newInstance(Alumno alumno) {
        FichasAlumnoFragment fragment = new FichasAlumnoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("alumno", alumno);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            alumno = getArguments().getParcelable("alumno");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fichas_alumno, container, false);
        ButterKnife.bind(this, view);

        presenter = new FichasAlumnoPresenterImpl(this, new FichasAlumnoInteractorImpl(getContext()), getContext());
        if(alumno == null) {
            presenter.getAlumno();
        }else{
            initView();
            initRecycler();
        }
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
        if(alumno.getFichas().size() > 0) {
            for (int i = 0; i < alumno.getFichas().size(); i++) {
                horas += alumno.getFichas().get(i).getHoras();
            }
        }
        pb_horas_alumno.setProgress(horas);
        tv_horas_totales.setText(horas + "/" + Constantes.HORAS_PRACTICAS);
    }

    private void initRecycler() {
        adaptadorFicha = new AdaptadorFicha(alumno.getFichas(), getContext(), this);
        if(adaptadorFicha != null) {
            if(adaptadorFicha.getItemCount() > 0) {
                rv_fichas_alumno.setVisibility(View.VISIBLE);
                rv_fichas_alumno.setAdapter(adaptadorFicha);
                tv_empty_fichas.setVisibility(View.INVISIBLE);
            }else{
                rv_fichas_alumno.setVisibility(View.INVISIBLE);
                tv_empty_fichas.setVisibility(View.VISIBLE);
            }
        }else{
            rv_fichas_alumno.setVisibility(View.INVISIBLE);
            tv_empty_fichas.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClickListener(Ficha ficha) {
        NavigationFragment.replaceFragment(getActivity().getSupportFragmentManager(), FichaFragment.newInstance(ficha), FichaFragment.class.getName());
    }
}