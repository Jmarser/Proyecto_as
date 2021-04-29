package com.jmarser.proyecto_as.cuenta.interactor;

import android.content.Context;

import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.api.WebService;
import com.jmarser.proyecto_as.api.WsApi;
import com.jmarser.proyecto_as.model.Login;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CuentaInteractorImpl implements CuentaInteractor{

    private Context context;

    public CuentaInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public void tryChangePassword(Login login, changePasswordListener listener) {

        Call<Login> call = WebService.getInstance().createWsApi(WsApi.class).modificarPass(SharedPrefManager.getInstance(context).getHeader(), login.getId(), login);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.code() == 200) {
                    listener.success(response.body());
                }else if(response.code() == 401){
                    listener.unknowError(context.getResources().getString(R.string.user_without_authorization));
                }else if(response.code() == 404){
                    listener.errorUnknowCuenta(context.getResources().getString(R.string.UserNotFound));
                }else{
                    listener.unknowError(context.getResources().getString(R.string.UnknowError));
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                listener.errorUnknowCuenta(context.getResources().getString(R.string.ErrorUnknowServer));
            }
        });
    }
}
