package com.jmarser.proyecto_as.login.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import com.google.android.material.textfield.TextInputLayout;
import com.jmarser.proyecto_as.login.interactor.LoginInteractor;
import com.jmarser.proyecto_as.login.view.LoginView;
import com.jmarser.proyecto_as.model.Login;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;


public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.onLoginFinishedListener {

    private LoginView view;
    private LoginInteractor interactor;
    private Context context;

    private String email;
    private String password;

    public LoginPresenterImpl(LoginView view, LoginInteractor interactor, Context context) {
        this.view = view;
        this.interactor = interactor;
        this.context = context;
    }

    @Override
    public void errorUser(String mensaje) {
        view.showErrorUser(mensaje);
    }

    @Override
    public void success(Login login) {
        /*guardamos los datos de login en sharedPreferences, con lo que si el usuario no hace logout
        * la proxima vez que inicie la aplicación se cargara desde la pantalla de splash y no tendrá
        * que volver a insertar los datos de login.*/
        SharedPrefManager.getInstance(context).guardarUsuario(login);
        view.goToView(login.getRol());

    }

    @Override
    public void unknowError(String mensaje) {
        view.unknowError(mensaje);
    }

    @Override
    public void enabledUser(String mensaje) {
        view.enabledUser(mensaje);
    }

    @Override
    public void validarCredenciales(TextInputLayout til_email, TextInputLayout til_password) {
        email = til_email.getEditText().getText().toString().trim();
        password = til_password.getEditText().getText().toString().trim();

        if (!TextUtils.isEmpty(email)) {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (!TextUtils.isEmpty(password)) {
                    interactor.tryToLogin(email, password, this);
                } else {
                    view.showErrorPassword("Indique un password");
                }
            } else {
                view.showErrorEmail("Email no válido.");
            }
        } else {
            view.showErrorEmail("Debe indicar un email.");
        }
    }

    @Override
    public void showErrorUser(String mensaje) {

    }
}
