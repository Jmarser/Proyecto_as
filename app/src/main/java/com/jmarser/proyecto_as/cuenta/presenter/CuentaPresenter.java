package com.jmarser.proyecto_as.cuenta.presenter;

import com.google.android.material.textfield.TextInputLayout;

public interface CuentaPresenter {

    void validarCampos(TextInputLayout passwordActual, TextInputLayout newPassword, TextInputLayout newPasswordRep);
}
