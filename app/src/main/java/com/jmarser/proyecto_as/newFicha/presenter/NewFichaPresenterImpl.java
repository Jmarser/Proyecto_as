package com.jmarser.proyecto_as.newFicha.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
        view.saveFichaOk("Ficha guardada correctamente.");
    }

    @Override
    public void errorNewFicha(String mensaje) {
        view.showErrorFecha(mensaje);
    }

    @Override
    public void unknowError(String mensaje) {
        view.unknowError(mensaje);
    }

    @Override
    public void validarCampos(Spinner sp_horas, String fecha, EditText descripcion, EditText observaciones, CheckBox firmado, Alumno alumno) {
        if(sp_horas.getSelectedItemPosition() != 0){
            int horas = Integer.parseInt(sp_horas.getSelectedItem().toString());
            if(!TextUtils.isEmpty(fecha)){
                if(!descripcion.getText().toString().isEmpty()){
                    String descrip = descripcion.getText().toString();
                    if(firmado.isChecked()){
                        if(alumno != null) {
                            Ficha ficha = new Ficha();
                            ficha.setAlumnoId(alumno.getId());
                            ficha.setHoras(horas);
                            ficha.setFecha(fecha);
                            ficha.setDescripcion(descrip);
                            ficha.setObservaciones(observaciones.getText().toString());
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
