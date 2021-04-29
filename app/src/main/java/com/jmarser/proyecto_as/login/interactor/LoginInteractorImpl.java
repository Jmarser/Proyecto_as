package com.jmarser.proyecto_as.login.interactor;


import android.content.Context;

import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.api.WebService;
import com.jmarser.proyecto_as.api.WsApi;
import com.jmarser.proyecto_as.model.Login;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;
import com.jmarser.proyecto_as.utils.Constantes;
import com.jmarser.proyecto_as.utils.EncriptadorAES;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginInteractorImpl implements LoginInteractor {


    private Context context;

    public LoginInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public void tryToLogin(String email, String password, onLoginFinishedListener listener) {
        EncriptadorAES encriptador = new EncriptadorAES();

        Login login = new Login();
        login.setEmail(email);
        //login.setPassword(encriptador.encriptar(password, Constantes.CLAVE_ENCRIPTACION));
        login.setPassword(password);

        /*hacemos una llamada a nuestro webService genérico con patron singleton y en la misma linea
        * llamamos al método que necesitamos, en este caso login*/
        Call<Login> call = WebService.getInstance().createWsApi(WsApi.class).login(SharedPrefManager.getInstance(context).getHeader(), login);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.code() == 200) {//login correcto
                    listener.success(response.body());
                }else if(response.code() == 401){
                    listener.userWithoutAuthorization(context.getResources().getString(R.string.user_without_authorization));
                } else if (response.code() == 409) {//usuario dado de baja
                    listener.enabledUser(context.getResources().getString(R.string.EnabledUser));
                } else if (response.code() == 404){//error de login
                    listener.errorUser(context.getResources().getString(R.string.UserNotFound));
                }else{
                    listener.unknowError(context.getResources().getString(R.string.UnknowError));
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                listener.serverError(context.getResources().getString(R.string.ErrorUnknowServer));
            }
        });
    }
}
