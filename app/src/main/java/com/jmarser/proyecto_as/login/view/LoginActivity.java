package com.jmarser.proyecto_as.login.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.jmarser.proyecto_as.principal.PrincipalActivity;
import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.login.interactor.LoginInteractorImpl;
import com.jmarser.proyecto_as.login.presenter.LoginPresenter;
import com.jmarser.proyecto_as.login.presenter.LoginPresenterImpl;
import com.jmarser.proyecto_as.utils.NavigationActivitis;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private LoginPresenter presenter;

    @BindView(R.id.til_email_login)
    TextInputLayout til_email;
    @BindView(R.id.til_password_login)
    TextInputLayout til_password;
    @BindView(R.id.btn_login)
    Button btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter = new LoginPresenterImpl(this, new LoginInteractorImpl(), this);

        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                presenter.validarCredenciales(til_email, til_password);
                break;
        }
    }

    @Override
    public void showErrorEmail(String mensaje) {
        til_email.getEditText().setError(mensaje);
        til_email.requestFocus();
    }

    @Override
    public void showErrorPassword(String mensaje) {
        til_password.getEditText().setError(mensaje);
        til_password.requestFocus();
    }

    @Override
    public void showErrorUser(String mensaje) {
        Toasty.error(this, mensaje, Toasty.LENGTH_SHORT).show();
        limpiarCampos();
    }

    @Override
    public void goToView(String rol) {
        Toasty.success(this, getResources().getString(R.string.login_ok), Toasty.LENGTH_SHORT).show();
        NavigationActivitis.pasarActividad(LoginActivity.this, PrincipalActivity.class);
    }

    @Override
    public void unknowError(String mensaje) {
        limpiarCampos();
        Toasty.error(this, mensaje, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void enabledUser(String mensaje) {

        limpiarCampos();
        /*dialogo que indica que el usuario esta dado de baja*/
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(getResources().getString(R.string.enableUser))
                .setMessage(getResources().getString(R.string.Enabled_user))
                .setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); //cerramos la aplicación
                    }
                }).show();
    }

    private void limpiarCampos() {
        til_email.getEditText().setText("");
        til_password.getEditText().setText("");
    }
}