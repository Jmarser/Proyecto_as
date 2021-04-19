package com.jmarser.proyecto_as.editFicha.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.editFicha.interactor.EditFichaInteractorImpl;
import com.jmarser.proyecto_as.editFicha.presenter.EditFichaPresenter;
import com.jmarser.proyecto_as.editFicha.presenter.EditFichaPresenterImpl;
import com.jmarser.proyecto_as.model.Ficha;
import com.jmarser.proyecto_as.utils.Constantes;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;


public class EditFichaFragment extends Fragment implements View.OnClickListener, EditFichaView{

    @BindView(R.id.sp_horas_new)
    Spinner sp_horas_new;
    @BindView(R.id.selector_fecha_new)
    DatePicker selector_fecha_new;
    @BindView(R.id.til_editar_descripcion_ficha)
    TextInputLayout til_editar_descripcion_ficha;
    @BindView(R.id.til_editar_observaciones_ficha)
    TextInputLayout til_editar_observaciones_ficha;
    @BindView(R.id.cb_Ficha_firma)
    CheckBox cb_fichaFirma;
    @BindView(R.id.btn_editar_ficha)
    Button btn_editar_ficha;

    private Ficha ficha;
    private EditFichaPresenter presenter;

    public EditFichaFragment() {
        // Required empty public constructor
    }

    public static EditFichaFragment newInstance(Ficha ficha) {
        EditFichaFragment fragment = new EditFichaFragment();
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
        presenter = new EditFichaPresenterImpl(getContext(), this, new EditFichaInteractorImpl());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_ficha, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //rellenamos el spinner con el fichero de recursos
        ArrayAdapter<CharSequence> adaptador_spinner = ArrayAdapter.createFromResource(getContext(),
                R.array.horas_completadas,
                R.layout.style_spinner_horas);
        sp_horas_new.setAdapter(adaptador_spinner);

        btn_editar_ficha.setOnClickListener(this);
        initView();
    }

    private void initView(){
        til_editar_descripcion_ficha.getEditText().setText(ficha.getDescripcion());
        til_editar_observaciones_ficha.getEditText().setText(ficha.getObservaciones());
        cb_fichaFirma.setChecked(ficha.isFirmaAlumno());
        sp_horas_new.setSelection(ficha.getHoras());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_editar_ficha:
                //obtenemos la nueva fecha de la ficha
                String newFecha = selector_fecha_new.getDayOfMonth()+
                        "/"+selector_fecha_new.getMonth()+
                        "/"+selector_fecha_new.getYear();

                presenter.validarCampos(sp_horas_new, newFecha,til_editar_descripcion_ficha,
                        til_editar_observaciones_ficha,cb_fichaFirma,ficha);
                break;
        }
    }

    @Override
    public void showErrorHoras(String mensaje) {
        Toasty.error(getContext(),mensaje,Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorFecha(String mensaje) {
        Toasty.error(getContext(),mensaje,Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorDescripcion(String mensaje) {
        Toasty.error(getContext(),mensaje,Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorFirma(String mensaje) {
        Toasty.error(getContext(),mensaje,Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorEdit(String mensaje) {
        Toasty.error(getContext(),mensaje,Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void editFichaOk(String mensaje) {
        Toasty.success(getContext(), mensaje,Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void errorFichaExist(String mensaje) {
        Toasty.error(getContext(),mensaje,Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void unknowError(String mensaje) {
        Toasty.error(getContext(),mensaje,Toasty.LENGTH_SHORT).show();
    }
}