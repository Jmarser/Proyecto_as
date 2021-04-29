package com.jmarser.proyecto_as.ficha.interactor;

import android.content.Context;

import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.api.WebService;
import com.jmarser.proyecto_as.api.WsApi;
import com.jmarser.proyecto_as.model.Ficha;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FichaFragmentInteractorImpl implements FichaFragmentInteractor{

    private Context context;

    public FichaFragmentInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public void tryUpdateFile(Ficha ficha, OnUpdateFileListener listener) {
        Call<Ficha> call = WebService.getInstance().createWsApi(WsApi.class).firmarFicha(SharedPrefManager.getInstance(context).getHeader(), ficha.getId(), ficha);
        call.enqueue(new Callback<Ficha>() {
            @Override
            public void onResponse(Call<Ficha> call, Response<Ficha> response) {
                if(response.code() == 200) {
                    listener.successUpdate(response.body());
                }else if(response.code() == 401){
                    listener.userWithoutAuthorization(context.getResources().getString(R.string.user_without_authorization));
                }else if(response.code() == 404){
                    listener.errorNotFound(context.getResources().getString(R.string.FichaNotFound));
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
