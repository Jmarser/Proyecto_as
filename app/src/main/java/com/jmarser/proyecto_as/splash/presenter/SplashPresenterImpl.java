package com.jmarser.proyecto_as.splash.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.model.Login;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;
import com.jmarser.proyecto_as.splash.interactor.SplashInteractor;
import com.jmarser.proyecto_as.splash.view.SplashView;

public class SplashPresenterImpl implements SplashPresenter, SplashInteractor.onLoginFinishedListener {

    private SplashView view;
    private SplashInteractor interactor;
    private Context context;

    public SplashPresenterImpl(SplashView view, SplashInteractor interactor, Context context) {
        this.view = view;
        this.interactor = interactor;
        this.context = context;
    }

    @Override
    public void errorUser(String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
        SharedPrefManager.getInstance(context).limpiarShared();
        view.goToLogin();
    }

    @Override
    public void success(Login login) {
        SharedPrefManager.getInstance(context).guardarUsuario(login);
        view.goToView();
    }

    @Override
    public void unknownError(String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
        view.goToLogin();
    }

    @Override
    public void enabledUser(String mensaje) {
        view.enabledUser(mensaje);
    }

    @Override
    public void tryToLogin() {
        String email = SharedPrefManager.getInstance(context).getUsuario().getEmail();
        String password = SharedPrefManager.getInstance(context).getUsuario().getPassword();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            interactor.tryToLogin(email, password, this, context);
        }else{
            errorUser(context.getResources().getString(R.string.unknown_user));//Usuario desconocido.
        }
    }

}
