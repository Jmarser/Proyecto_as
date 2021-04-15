package com.jmarser.proyecto_as.fichasAlumno.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.adaptadores.AdaptadorFicha;
import com.jmarser.proyecto_as.ficha.view.FichaFragment;
import com.jmarser.proyecto_as.fichasAlumno.interactor.FichasAlumnoInteractorImpl;
import com.jmarser.proyecto_as.fichasAlumno.presenter.FichasAlumnoPresenter;
import com.jmarser.proyecto_as.fichasAlumno.presenter.FichasAlumnoPresenterImpl;
import com.jmarser.proyecto_as.model.Alumno;
import com.jmarser.proyecto_as.model.Ficha;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;
import com.jmarser.proyecto_as.newFicha.view.NewFichaFragment;
import com.jmarser.proyecto_as.utils.Constantes;
import com.jmarser.proyecto_as.utils.NavigationFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class FichasAlumnoFragment extends Fragment implements FichasAlumnoView, AdaptadorFicha.ItemClickListener, View.OnClickListener {

    @BindView(R.id.tv_nombre_alumno_fichas)
    TextView tv_nombre_alumno;
    @BindView(R.id.pb_horas_alumno)
    ProgressBar pb_horas_alumno;
    @BindView(R.id.tv_info_horas_totales)
    TextView tv_horas_totales;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.rv_fichas_alumno)
    RecyclerView rv_fichas_alumno;
    @BindView(R.id.tv_empty_fichas)
    TextView tv_empty_fichas;
    @BindView(R.id.fab_nueva_ficha)
    FloatingActionButton fab_nueva_ficha;

    private FichasAlumnoPresenter presenter;
    private Alumno alumno = null;
    private AdaptadorFicha adaptadorFicha;

    public FichasAlumnoFragment() {
        // Required empty public constructor
    }

    public static FichasAlumnoFragment newInstance(Alumno alumno) {
        FichasAlumnoFragment fragment = new FichasAlumnoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constantes.KEY_ALUMNO, alumno);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            alumno = getArguments().getParcelable(Constantes.KEY_ALUMNO);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fichas_alumno, container, false);
        ButterKnife.bind(this, view);

        presenter = new FichasAlumnoPresenterImpl(this, new FichasAlumnoInteractorImpl(getContext()), getContext());

        if (alumno == null) {
            /*En este caso se ha loga do un alumno, por lo que hay que obtener sus fichas de la base
             de datos, además debemos mostrar el boton para crear una nueva ficha*/
            presenter.getAlumno();
            fab_nueva_ficha.show();
            ocultarFloating();
        } else {
            /*Se ha logado un profesor/tutor por lo que no debemos solicitar datos a la base de datos
             * además no se debe de mostrar el boton para crear nuevas fichas*/
            initView();
            initRecycler();
            fab_nueva_ficha.hide();
        }

        fab_nueva_ficha.setOnClickListener(this);

        refrescarRecycler();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rv_fichas_alumno.setHasFixedSize(true);
        rv_fichas_alumno.setLayoutManager(new LinearLayoutManager(getActivity()));

        ocultarFloating();

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

    @Override
    public void errorAlumno(String mensaje) {
        Toasty.error(getContext(),mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void unknowError(String mensaje) {
        Toasty.error(getContext(),mensaje, Toasty.LENGTH_SHORT).show();
    }

    private void initView() {
        pb_horas_alumno.setMax(Constantes.HORAS_PRACTICAS);
        tv_nombre_alumno.setText(alumno.toString());
        int horas = 0;
        if (alumno.getFichas().size() > 0) {
            for (int i = 0; i < alumno.getFichas().size(); i++) {
                horas += alumno.getFichas().get(i).getHoras();
            }
        }
        pb_horas_alumno.setProgress(horas);
        tv_horas_totales.setText(String.valueOf(horas) + "/" + String.valueOf(Constantes.HORAS_PRACTICAS));
    }

    private void initRecycler() {
        adaptadorFicha = new AdaptadorFicha(alumno.getFichas(), getContext(), this);

        /*Para el caso de que el alumno no tenga fichas, controlamos que se muestre un mensaje indicandolo*/
        if (adaptadorFicha != null) {
            if (adaptadorFicha.getItemCount() > 0) {
                rv_fichas_alumno.setVisibility(View.VISIBLE);
                rv_fichas_alumno.setAdapter(adaptadorFicha);
                tv_empty_fichas.setVisibility(View.INVISIBLE);
            } else {
                rv_fichas_alumno.setVisibility(View.INVISIBLE);
                tv_empty_fichas.setVisibility(View.VISIBLE);
            }
        } else {
            rv_fichas_alumno.setVisibility(View.INVISIBLE);
            tv_empty_fichas.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClickListener(Ficha ficha) {
        NavigationFragment.replaceFragment(getActivity().getSupportFragmentManager(), FichaFragment.newInstance(ficha), FichaFragment.class.getName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_nueva_ficha:
                NavigationFragment.replaceFragment(getActivity().getSupportFragmentManager(), NewFichaFragment.newInstance(alumno), NewFichaFragment.class.getName());
                break;
        }
    }

    /*para que no estorbe el boton flotante al hacer scroll en el recyclerView lo ocultamos cuando
    nos desplacemos, se volvera a mostrar cuando regresemos al principio del recycler*/
    private void ocultarFloating() {
        rv_fichas_alumno.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fab_nueva_ficha.hide();
                } else if (dy < 0) {
                    if(!SharedPrefManager.getInstance(getContext()).getUsuario().getRol().equalsIgnoreCase(Constantes.ROL_ALUMNO)){
                        fab_nueva_ficha.hide();
                    }else {
                        fab_nueva_ficha.show();
                    }
                }
            }
        });
    }

    /*le damos al recycler la funcionalidad de al hacer pull recarge los elementos y muestre si hay nuevos*/
    private void refrescarRecycler(){
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*como ya tenemos el alumno podemos hacer una busqueda por el id, da igual quien se haya logado*/
                presenter.getAlumnoById(alumno.getId());
                adaptadorFicha.notifyDataSetChanged();//notificamos que ha habido un cambio al adaptador
                swipe.setRefreshing(false); //detenemos la barra de progreso circular del refresh
                if(!SharedPrefManager.getInstance(getContext()).getUsuario().getRol().equalsIgnoreCase(Constantes.ROL_ALUMNO)){
                    fab_nueva_ficha.hide();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constantes.KEY_ALUMNO, alumno);
    }
}