package com.jmarser.proyecto_as.ficha.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.model.Ficha;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FichaFragment extends Fragment {

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

    private Ficha ficha;

    public FichaFragment() {
        // Required empty public constructor
    }

    public static FichaFragment newInstance(Ficha ficha) {
        FichaFragment fragment = new FichaFragment();
        Bundle args = new Bundle();
        args.putParcelable("ficha", ficha);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ficha = getArguments().getParcelable("ficha");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ficha, container, false);
        ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {
        cb_firmaAlumno.setClickable(false);
        cb_firmaProfesor.setClickable(false);
        cb_firmaTutor.setClickable(false);
        tv_descripcion_ficha.setText(ficha.getDescripcion());
        tv_observaciones_ficha.setText(ficha.getObservaciones());
        tv_horas_ficha.setText(String.valueOf(ficha.getHoras()));
        cb_firmaAlumno.setChecked(ficha.isFirmaAlumno());
        cb_firmaProfesor.setChecked(ficha.isFirmaProf());
        cb_firmaTutor.setChecked(ficha.isFirmaTutor());
    }
}