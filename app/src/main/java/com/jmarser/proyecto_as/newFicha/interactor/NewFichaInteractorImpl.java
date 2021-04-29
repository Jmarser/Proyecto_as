package com.jmarser.proyecto_as.newFicha.interactor;


import android.content.Context;

import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.api.WebService;
import com.jmarser.proyecto_as.api.WsApi;
import com.jmarser.proyecto_as.model.Ficha;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewFichaInteractorImpl implements NewFichaInteractor{

    private Context context;

    public NewFichaInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public void saveFicha(Ficha ficha, OnPostNewFichaListener listener) {
        Call<Ficha> call = WebService.getInstance().createWsApi(WsApi.class).saveFicha(SharedPrefManager.getInstance(context).getHeader(), ficha);
        call.enqueue(new Callback<Ficha>() {
            @Override
            public void onResponse(Call<Ficha> call, Response<Ficha> response) {
                if(response.code() == 201) {
                    listener.success();
                }else if(response.code() == 401){
                    listener.userWithoutAuthorization(context.getResources().getString(R.string.user_without_authorization));
                }else if(response.code() == 409){
                    listener.errorFichaExist(context.getResources().getString(R.string.FichaExist));
                }else if(response.code() == 400){
                    listener.errorNewFicha(context.getResources().getString(R.string.ErrorSaveFicha));
                }else{
                    listener.unknowError(context.getResources().getString(R.string.UnknowError));
                }
            }

            @Override
            public void onFailure(Call<Ficha> call, Throwable t) {
                listener.serverError(context.getResources().getString(R.string.ErrorUnknowServer));
            }
        });
    }
}
