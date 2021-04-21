package com.jmarser.proyecto_as.cuenta.presenter;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;
import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.cuenta.interactor.CuentaInteractor;
import com.jmarser.proyecto_as.cuenta.view.CuentaView;
import com.jmarser.proyecto_as.model.Login;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;

public class CuentaPresenterImpl implements CuentaPresenter, CuentaInteractor.changePasswordListener {

    private Context context;
    private CuentaView view;
    private CuentaInteractor interactor;

    public CuentaPresenterImpl(Context context, CuentaView view, CuentaInteractor interactor) {
        this.context = context;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void validarCampos(TextInputLayout passwordActual, TextInputLayout newPassword, TextInputLayout newPasswordRep) {
        //comprobamos que no haya campos vacíos
        if (!passwordActual.getEditText().getText().toString().isEmpty()) {
            if (!newPassword.getEditText().getText().toString().isEmpty()) {
                if (!newPasswordRep.getEditText().getText().toString().isEmpty()) {
                    //comprobamos la longitud de los password
                    if (passwordActual.getEditText().getText().length() >= 6) {
                        if (newPassword.getEditText().getText().length() >= 6) {
                            if (newPasswordRep.getEditText().getText().length() >= 6) {
                                //comprobamos que el password actual coincida con el de inicio de sesion
                                if (passwordActual.getEditText().getText().toString().equals(SharedPrefManager.getInstance(context).getUsuario().getPassword())) {
                                    //comprobamos que los nuevos password coinciden
                                    if (newPassword.getEditText().getText().toString().equals(newPasswordRep.getEditText().getText().toString())) {
                                        //Comprobamos que el nuevo password no sea el mismo que el antiguo
                                        if (!newPassword.getEditText().getText().toString().equals(SharedPrefManager.getInstance(context).getUsuario().getPassword())) {
                                            Login login = new Login();
                                            login.setId(SharedPrefManager.getInstance(context).getUsuario().getId());
                                            login.setEmail(SharedPrefManager.getInstance(context).getUsuario().getEmail());
                                            login.setPassword(newPassword.getEditText().getText().toString());
                                            login.setRol(SharedPrefManager.getInstance(context).getUsuario().getRol());
                                            interactor.tryChangePassword(login, this);
                                        } else {
                                            view.infoEqualsPass("El nuevo password es igual al actual.");
                                        }
                                    } else {
                                        view.errorEqualsPass(context.getResources().getString(R.string.PasswordNotMatch));
                                        newPassword.getEditText().setError(context.getResources().getString(R.string.PasswordNotMatch));
                                    }
                                } else {
                                    passwordActual.requestFocus();
                                    passwordActual.getEditText().setError(context.getResources().getString(R.string.IncorrectPassword));
                                }
                            } else {
                                newPasswordRep.requestFocus();
                                newPasswordRep.getEditText().setError("El password no cumple la longitud mínima.");
                            }
                        } else {
                            newPassword.requestFocus();
                            newPassword.getEditText().setError("El password no cumple la longitud mínima.");
                        }
                    } else {
                        passwordActual.requestFocus();
                        passwordActual.getEditText().setError(context.getResources().getString(R.string.IncorrectPassword));
                    }
                } else {
                    newPasswordRep.requestFocus();
                    newPasswordRep.getEditText().setError(context.getResources().getString(R.string.RequiredField));
                }
            } else {
                newPassword.requestFocus();
                newPassword.getEditText().setError(context.getResources().getString(R.string.RequiredField));
            }
        } else {
            passwordActual.requestFocus();
            passwordActual.getEditText().setError(context.getResources().getString(R.string.RequiredField));//campo obligatorio
        }
    }

    @Override
    public void success(Login login) {
        SharedPrefManager.getInstance(context).limpiarShared();//limpiamos los datos de la SharedPreferences.
        SharedPrefManager.getInstance(context).guardarUsuario(login);//Guardamos los nuevos datos en la SharedPreferences
        view.changePassOk(context.getResources().getString(R.string.PasswordChangeOk));
    }

    @Override
    public void errorUnknowCuenta(String mensaje) {
        view.UnknowCuenta(mensaje);
    }

    @Override
    public void unknowError(String mensaje) {
        view.unknowError(mensaje);
    }
}
