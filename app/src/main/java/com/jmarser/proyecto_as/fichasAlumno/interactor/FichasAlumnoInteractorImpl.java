package com.jmarser.proyecto_as.fichasAlumno.interactor;

import android.content.Context;

import com.jmarser.proyecto_as.api.WebService;
import com.jmarser.proyecto_as.api.WsApi;
import com.jmarser.proyecto_as.model.Alumno;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FichasAlumnoInteractorImpl implements FichasAlumnoInteractor{

    private Context context;

    public FichasAlumnoInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getAlumno(OnGetAlumnoListener listener) {
        Alumno alumno = new Alumno();
        alumno.setEmail(SharedPrefManager.getInstance(context).getUsuario().getEmail());

        Call<Alumno> call = WebService.getInstance().createWsApi(WsApi.class).getAlumno(alumno);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if(response.code() == 200){
                    listener.success(response.body());
                }else if(response.code() == 404){
                    listener.errorAlumno(response.message());
                }else{
                    listener.unknowError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                listener.unknowError("Error desconocido del servidor.");
            }
        });
    }
}
