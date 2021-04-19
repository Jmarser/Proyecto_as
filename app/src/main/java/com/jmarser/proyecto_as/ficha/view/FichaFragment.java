package com.jmarser.proyecto_as.ficha.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.editFicha.view.EditFichaFragment;
import com.jmarser.proyecto_as.ficha.interactor.FichaFragmentInteractorImpl;
import com.jmarser.proyecto_as.ficha.presenter.FichaFragmentPresenter;
import com.jmarser.proyecto_as.ficha.presenter.FichaFragmentPresenterImpl;
import com.jmarser.proyecto_as.model.Ficha;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;
import com.jmarser.proyecto_as.utils.Constantes;
import com.jmarser.proyecto_as.utils.NavigationFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class FichaFragment extends Fragment implements View.OnClickListener, FichaFragmentView {

    @BindView(R.id.tv_horas_ficha)
    TextView tv_horas_ficha;
    @BindView(R.id.tv_descripcion_ficha)
    TextView tv_descripcion_ficha;
    @BindView(R.id.tv_observaciones_ficha)
    TextView tv_observaciones_ficha;
    @BindView(R.id.cb_firmaAlumno)
    CheckBox cb_firmaAlumno;
    @BindView(R.id.cb_firmaProfesor)
    CheckBox cb_firmaProfesor;
    @BindView(R.id.cb_firmaTutor)
    CheckBox cb_firmaTutor;
    @BindView(R.id.btn_firmar_ficha)
    Button btn_firmar_ficha;
    @BindView(R.id.btn_editar)
    Button btn_editarFicha;

    private Ficha ficha;
    private FichaFragmentPresenter presenter;

    public FichaFragment() {
        // Required empty public constructor
    }

    public static FichaFragment newInstance(Ficha ficha) {
        FichaFragment fragment = new FichaFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constantes.KEY_FICHA, ficha);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ficha = getArguments().getParcelable(Constantes.KEY_FICHA);
        }

        presenter = new FichaFragmentPresenterImpl(getContext(),this, new FichaFragmentInteractorImpl());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ficha, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_firmar_ficha.setOnClickListener(this);
        btn_editarFicha.setOnClickListener(this);

        initView();
    }

    private void initView() {
        tv_descripcion_ficha.setText(ficha.getDescripcion());
        tv_observaciones_ficha.setText(ficha.getObservaciones());
        tv_horas_ficha.setText(String.valueOf(ficha.getHoras()));
        cb_firmaAlumno.setChecked(ficha.isFirmaAlumno());
        cb_firmaProfesor.setChecked(ficha.isFirmaProf());
        cb_firmaTutor.setChecked(ficha.isFirmaTutor());

        initButtons();
    }

    private void initView(Ficha aux){
        tv_descripcion_ficha.setText(aux.getDescripcion());
        tv_observaciones_ficha.setText(aux.getObservaciones());
        tv_horas_ficha.setText(String.valueOf(aux.getHoras()));
        cb_firmaAlumno.setChecked(aux.isFirmaAlumno());
        cb_firmaProfesor.setChecked(aux.isFirmaProf());
        cb_firmaTutor.setChecked(aux.isFirmaTutor());

        initButtons();
    }

    private void initButtons(){
        cb_firmaAlumno.setClickable(false);
        cb_firmaProfesor.setClickable(false);
        cb_firmaTutor.setClickable(false);
        if(SharedPrefManager.getInstance(getContext()).getUsuario().getRol().equalsIgnoreCase(Constantes.ROL_ALUMNO)){
            btn_firmar_ficha.setVisibility(View.GONE);
            btn_editarFicha.setVisibility(View.VISIBLE);
        }else{
            btn_firmar_ficha.setVisibility(View.VISIBLE);
            btn_editarFicha.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_firmar_ficha:
                if(SharedPrefManager.getInstance(getContext()).getUsuario().getRol().equalsIgnoreCase(Constantes.ROL_PROFESOR)){
                    presenter.checkTeacherSignature(ficha);
                }else if(SharedPrefManager.getInstance(getContext()).getUsuario().getRol().equalsIgnoreCase(Constantes.ROL_TUTOR)){
                    presenter.checkTutorSignature(ficha);
                }
                break;
            case R.id.btn_editar:
                NavigationFragment.replaceFragment(getActivity().getSupportFragmentManager(), EditFichaFragment.newInstance(ficha), EditFichaFragment.class.getName());
                break;
        }
    }

    @Override
    public void unknowError(String mensaje) {
        Toasty.error(getContext(),mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void errorFichaId(String mensaje) {
        Toasty.error(getContext(),mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void successUpdate(Ficha aux) {
        Toasty.success(getContext(), getResources().getString(R.string.firma_ok),Toasty.LENGTH_SHORT).show();
        initView(aux);
    }

    @Override
    public void fileSigned(String mensaje) {
        Toasty.info(getContext(),mensaje, Toasty.LENGTH_SHORT).show();
    }
}