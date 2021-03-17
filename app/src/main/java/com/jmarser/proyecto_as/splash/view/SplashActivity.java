package com.jmarser.proyecto_as.splash.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import com.jmarser.proyecto_as.principal.PrincipalActivity;
import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.login.view.LoginActivity;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;
import com.jmarser.proyecto_as.splash.interactor.SplashInteractorImpl;
import com.jmarser.proyecto_as.splash.presenter.SplashPresenter;
import com.jmarser.proyecto_as.splash.presenter.SplashPresenterImpl;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements SplashView {

    private SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        //quitamos la barra de notificaciones del terminal para tener pantalla completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        presenter = new SplashPresenterImpl(this, new SplashInteractorImpl(), this);

        comprobarShared();
    }


    @Override
    public void goToLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        //para evitar que al darle hacia atras volvamos a esta pantalla, iniciamos una nueva lista de tareas y cerramos esta.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public void goToView() {
        Toast.makeText(this, getResources().getString(R.string.login_ok), Toast.LENGTH_SHORT).show();//"Login correcto"
        Intent intent = new Intent(SplashActivity.this, PrincipalActivity.class);
        /*para evitar que desde la actividad de destino podamos volver a esta actividad utilizamos los
        siguinetes flags*/
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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