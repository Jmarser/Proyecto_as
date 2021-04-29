package com.jmarser.proyecto_as.login.interactor;

import com.jmarser.proyecto_as.model.Login;

public interface LoginInteractor {

    interface onLoginFinishedListener{
        void errorUser(String mensaje);

        void success(Login login);

        void unknowError(String mensaje);

        void enabledUser(String mensaje);

        void serverError(String mensaje);

        void userWithoutAuthorization(String mensaje);
    }

    void tryToLogin(String email, String password, onLoginFinishedListener listener);
}
