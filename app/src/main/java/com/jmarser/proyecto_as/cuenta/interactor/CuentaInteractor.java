package com.jmarser.proyecto_as.cuenta.interactor;

import com.jmarser.proyecto_as.model.Login;

public interface CuentaInteractor {

    interface changePasswordListener{
        void success(Login login);

        void errorUnknowCuenta(String mensaje);

        void unknowError(String mensaje);

        void serverError(String mensaje);

        void userWithoutAuthorization(String mensaje);
    }

    void tryChangePassword(Login login, changePasswordListener listener);
}
