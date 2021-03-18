package com.jmarser.proyecto_as.newFicha.presenter;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.jmarser.proyecto_as.model.Alumno;

public interface NewFichaPresenter {

    void validarCampos(Spinner sp_horas, String fecha, TextInputLayout descripcion, TextInputLayout observaciones, CheckBox firmado, Alumno alumno);


}
