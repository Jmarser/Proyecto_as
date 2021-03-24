package com.jmarser.proyecto_as.splash.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.jmarser.proyecto_as.principal.PrincipalActivity;
import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.login.view.LoginActivity;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;
import com.jmarser.proyecto_as.splash.interactor.SplashInteractorImpl;
import com.jmarser.proyecto_as.splash.presenter.SplashPresenter;
import com.jmarser.proyecto_as.splash.presenter.SplashPresenterImpl;
import com.jmarser.proyecto_as.utils.NavigationActivitis;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class SplashActivity extends AppCompatActivity implements SplashView {

    private SplashPresenter presenter;

    @BindView(R.id.pb_cargando)
    ProgressBar pb_cargando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        //quitamos la barra de notificaciones del terminal para tener pantalla completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        presenter = new SplashPresenterImpl(this, new SplashInteractorImpl(), this);

        pb_cargando.setVisibility(View.VISIBLE);
        comprobarShared();
    }

    @Override
    public void goToLogin() {
        pb_cargando.setVisibility(View.INVISIBLE);
        NavigationActivitis.pasarActividad(SplashActivity.this, LoginActivity.class);
    }

    @Override
    public void goToView() {
        pb_cargando.setVisibility(View.INVISIBLE);
        Toasty.success(this, getResources().getString(R.string.login_ok), Toasty.LENGTH_SHORT).show();
        NavigationActivitis.pasarActividad(SplashActivity.this, PrincipalActivity.class);
    }

    @Override
    public void enabledUser(String mensaje) {
        pb_cargando.setVisibility(View.INVISIBLE);
        /*dialogo que indica que el usuario esta dado de baja*/
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(getResources().getString(R.string.enableUser))
                .setMessage(getResources().getString(R.string.Enabled_user))
                .setNeutralButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPrefManager.getInstance(SplashActivity.this).limpiarShared();
                        finish(); //cerramos la aplicación
                    }
                }).show();
    }

    @Override
    public void unknowError(String mensaje) {
        Toasty.error(this, mensaje, Toasty.LENGTH_SHORT).show();
        goToLogin();
    }

    @Override
    public void errorUser(String mensaje) {
        Toasty.error(this, mensaje, Toasty.LENGTH_SHORT).show();
        goToLogin();
    }

    /*Comprobamos que haya un usuario guardado en la sharedpreferences e intentamos hacer login, de no
    * haberlo vamos a la actividad de login.*/
    private void comprobarShared() {
        if (SharedPrefManager.getInstance(this).isLogin()) {
            presenter.tryToLogin();
        } else {
            goToLogin();
        }
    }
}