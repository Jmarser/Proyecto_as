package com.jmarser.proyecto_as.login.presenter;

import com.google.android.material.textfield.TextInputLayout;

public interface LoginPresenter {

    void validarCredenciales(TextInputLayout til_email, TextInputLayout til_password);

    void showErrorUser(String mensaje);


}
