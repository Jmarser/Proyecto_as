package com.jmarser.proyecto_as.alumnos.interactor;

import android.content.Context;

import com.jmarser.proyecto_as.api.WebService;
import com.jmarser.proyecto_as.api.WsApi;
import com.jmarser.proyecto_as.model.Profesor;
import com.jmarser.proyecto_as.model.Tutor;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnosInteractorImpl implements AlumnosInteractor{

    private Context context;

    public AlumnosInteractorImpl(Context context) {
        this.context = context;
    }


    @Override
    public void getProfesor(OnGetTutoresListener listener) {
        Profesor prof = new Profesor();
        prof.setEmail(SharedPrefManager.getInstance(context).getUsuario().getEmail());

        Call<Profesor> call = WebService.getInstance().createWsApi(WsApi.class).getProfesor(prof);
        call.enqueue(new Callback<Profesor>() {
            @Override
            public void onResponse(Call<Profesor> call, Response<Profesor> response) {
                if(response.code()==200){
                    listener.successProfesor(response.body());
                }else if(response.code()==404){
                    listener.errorTutores(response.message());
                }else{
                    listener.unkNowError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Profesor> call, Throwable t) {
                listener.unkNowError("Error desconocido del servidor.");
            }
        });
    }

    @Override
    public void getTutor(OnGetTutoresListener listener) {
        Tutor tutor = new Tutor();
        tutor.setEmail(SharedPrefManager.getInstance(context).getUsuario().getEmail());

        Call<Tutor> call = WebService.getInstance().createWsApi(WsApi.class).getTutor(tutor);
        call.enqueue(new Callback<Tutor>() {
            @Override
            public void onResponse(Call<Tutor> call, Response<Tutor> response) {
                if(response.code()==200){
                    listener.succcessTutor(response.body());
                }else if(response.code()==404){
                    listener.errorTutores(response.message());
                }else{
                    listener.unkNowError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Tutor> call, Throwable t) {
                listener.unkNowError("Error desconocido del servidor.");
            }
        });
    }
}