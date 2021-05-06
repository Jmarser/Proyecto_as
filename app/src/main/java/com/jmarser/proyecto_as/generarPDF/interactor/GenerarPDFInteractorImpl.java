package com.jmarser.proyecto_as.generarPDF.interactor;

import android.content.Context;
import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.api.WebService;
import com.jmarser.proyecto_as.api.WsApi;
import com.jmarser.proyecto_as.model.Alumno;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerarPDFInteractorImpl implements GenerarPDFInteractor {

    private Context context;

    public GenerarPDFInteractorImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getAlumno(GenerarPDFInteractor.OnGetAlumnoListener listener) {
        Alumno alumno = new Alumno();
        alumno.setEmail(SharedPrefManager.getInstance(context).getUsuario().getEmail());

        Call<Alumno> call = WebService.getInstance().createWsApi(WsApi.class).getAlumno(SharedPrefManager.getInstance(context).getHeader(), alumno);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if (response.code() == 200) {
                    listener.success(response.body());
                } else if (response.code() == 404) {
                    listener.errorAlumno(context.getResources().getString(R.string.UserNotFound));
                } else if (response.code() == 401) {
                    listener.userWithoutAuthorization(context.getResources().getString(R.string.user_without_authorization));
                } else {
                    listener.unknowError(context.getResources().getString(R.string.UnknowError));
                }
            }

            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                listener.serverError(context.getResources().getString(R.string.ErrorUnknowServer));
            }
        });
    }
}
