package com.jmarser.proyecto_as.newFicha.view;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;
import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.model.Alumno;
import com.jmarser.proyecto_as.newFicha.interactor.NewFichaInteractorImpl;
import com.jmarser.proyecto_as.newFicha.presenter.NewFichaPresenter;
import com.jmarser.proyecto_as.newFicha.presenter.NewFichaPresenterImpl;
import com.jmarser.proyecto_as.utils.Constantes;
import java.util.Calendar;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class NewFichaFragment extends Fragment implements View.OnClickListener, NewFichaView {

    @BindView(R.id.sp_horas)
    Spinner sp_horas;
    @BindView(R.id.til_descripcion_nueva_ficha)
    TextInputLayout til_descripcion;
    @BindView(R.id.til_observaciones_nueva_ficha)
    TextInputLayout til_observaciones;
    @BindView(R.id.cb_newFicha_firma)
    CheckBox cb_newFicha;
    @BindView(R.id.btn_nueva_ficha)
    Button btn_newFicha;

    @BindView(R.id.tv_selectFecha)
    TextView tv_fecha;
    private DatePickerDialog.OnDateSetListener selectFecha;

    private Alumno alumno;
    private NewFichaPresenter presenter;

    public NewFichaFragment() {
        // Required empty public constructor
    }


    public static NewFichaFragment newInstance(Alumno alumno) {
        NewFichaFragment fragment = new NewFichaFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constantes.KEY_ALUMNO, alumno);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            alumno = getArguments().getParcelable(Constantes.KEY_ALUMNO);
        }
        presenter = new NewFichaPresenterImpl(this, new NewFichaInteractorImpl(getContext()), getContext());

        selectFecha = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv_fecha.setText(dayOfMonth+"/"+month+"/"+year);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_ficha, container, false);
        ButterKnife.bind(this, view);

        tv_fecha.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //rellenamos el spinner con el fichero de recursos
        ArrayAdapter<CharSequence> adaptador_spinner = ArrayAdapter.createFromResource(getContext(),
                R.array.horas_completadas,
                R.layout.style_spinner_horas);
        //asignamos el adaptador al spinner
        sp_horas.setAdapter(adaptador_spinner);

        btn_newFicha.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_nueva_ficha:
                presenter.validarCampos(sp_horas, tv_fecha, til_descripcion, til_observaciones, cb_newFicha, alumno);
                break;
            case R.id.tv_selectFecha:
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, selectFecha, anio, mes, dia);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
        }
    }

    private void limpiarCampos() {
        sp_horas.setSelection(0);
        til_descripcion.getEditText().setText("");
        til_observaciones.getEditText().setText("");
        cb_newFicha.setChecked(false);
    }

    @Override
    public void showErrorHoras(String mensaje) {
        Toasty.error(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
        sp_horas.requestFocus();
    }

    @Override
    public void showErrorFecha(String mensaje) {

    }

    @Override
    public void showErrorDescripcion(String mensaje) {
        til_descripcion.getEditText().setError(mensaje);
        til_descripcion.requestFocus();
    }

    @Override
    public void showErrorFirma(String mensaje) {
        Toasty.error(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
        cb_newFicha.requestFocus();
    }

    @Override
    public void showErrorAlumno(String mensaje) {
        Toasty.error(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void saveFichaOk(String mensaje) {
        Toasty.success(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
        limpiarCampos();
    }

    @Override
    public void errorFichaExist(String mensaje) {
        Toasty.error(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void unknowError(String mensaje) {
        Toasty.error(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void serverError(String mensaje) {
        Toasty.error(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void userWithoutAuthorization(String mensaje) {
        Toasty.error(getContext(), mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constantes.KEY_ALUMNO, alumno);
    }
}