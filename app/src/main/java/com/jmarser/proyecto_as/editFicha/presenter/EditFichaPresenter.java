package com.jmarser.proyecto_as.editFicha.presenter;

import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.jmarser.proyecto_as.model.Ficha;

public interface EditFichaPresenter {

    void validarCampos(Spinner sp_horas, TextView newFecha, TextInputLayout descripcion, TextInputLayout observaciones, CheckBox firmado, Ficha ficha);
}
