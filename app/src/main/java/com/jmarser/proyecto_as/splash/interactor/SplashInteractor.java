package com.jmarser.proyecto_as.splash.interactor;

import android.content.Context;
import com.jmarser.proyecto_as.model.Login;

public interface SplashInteractor {

    interface onLoginFinishedListener{
        void errorUser(String mensaje);

        void success(Login login);

        void unknownError(String mensaje);

        void enabledUser(String mensaje);

        void serverError(String mensaje);

        void userWithoutAuthorization(String mensaje);
    }

    void tryToLogin(String email, String password, onLoginFinishedListener listener, Context context);
}
