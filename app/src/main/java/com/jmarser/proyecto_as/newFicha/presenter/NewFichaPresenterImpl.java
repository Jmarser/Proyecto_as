package com.jmarser.proyecto_as.newFicha.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.model.Alumno;
import com.jmarser.proyecto_as.model.Ficha;
import com.jmarser.proyecto_as.mySharedPref.SharedPrefManager;
import com.jmarser.proyecto_as.newFicha.interactor.NewFichaInteractor;
import com.jmarser.proyecto_as.newFicha.view.NewFichaView;

public class NewFichaPresenterImpl implements NewFichaPresenter, NewFichaInteractor.OnPostNewFichaListener{

    private NewFichaView view;
    private NewFichaInteractor interactor;
    private Context context;



    public NewFichaPresenterImpl(NewFichaView view, NewFichaInteractor interactor, Context context) {
        this.view = view;
        this.interactor = interactor;
        this.context = context;
    }

    @Override
    public void success() {
        view.saveFichaOk(context.getResources().getString(R.string.SaveFichaOk));
    }

    @Override
    public void errorNewFicha(String mensaje) {
        view.showErrorFecha(mensaje);
    }

    @Override
    public void errorFichaExist(String mensaje) {
        view.errorFichaExist(mensaje);
    }

    @Override
    public void unknowError(String mensaje) {
        view.unknowError(mensaje);
    }

    @Override
    public void serverError(String mensaje) {
        view.serverError(mensaje);
    }

    @Override
    public void userWithoutAuthorization(String mensaje) {
        view.userWithoutAuthorization(mensaje);
    }

    @Override
    public void validarCampos(Spinner sp_horas, TextView fecha, TextInputLayout descripcion, TextInputLayout observaciones, CheckBox firmado, Alumno alumno) {
        if(sp_horas.getSelectedItemPosition() != 0){
            int horas = Integer.parseInt(sp_horas.getSelectedItem().toString());
            if(!fecha.getText().toString().equals(context.getResources().getString(R.string.fecha))){
                if(!descripcion.getEditText().getText().toString().isEmpty()){
                    String descrip = descripcion.getEditText().getText().toString();
                    if(firmado.isChecked()){
                        if(alumno != null) {
                            Ficha ficha = new Ficha();
                            ficha.setAlumnoId(alumno.getId());
                            ficha.setHoras(horas);
                            ficha.setFecha(fecha.getText().toString());
                            ficha.setDescripcion(descrip);
                            ficha.setObservaciones(observaciones.getEditText().getText().toString());
                            ficha.setFirmaAlumno(firmado.isChecked());
                            interactor.saveFicha(ficha, this);
                        }else{
                            view.showErrorAlumno("No hay alumno asignado a la ficha");
                        }
                    }else{
                        view.showErrorFirma("Falta firma de la ficha.");
                    }
                }else{
                    view.showErrorDescripcion("Falta descripción de las tareas realizadas.");
                }
            }else{
                view.showErrorFecha("Debe indicar la fecha.");
            }
        }else{
            view.showErrorHoras("Debe seleccionar las horas completadas.");
        }
    }
}
