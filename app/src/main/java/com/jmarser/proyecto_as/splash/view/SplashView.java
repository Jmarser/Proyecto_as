package com.jmarser.proyecto_as.splash.view;


public interface SplashView {

    void goToLogin();

    void goToView();

    void enabledUser(String mensaje);

    void unknowError(String mensaje);

    void errorUser(String mensaje);
}
