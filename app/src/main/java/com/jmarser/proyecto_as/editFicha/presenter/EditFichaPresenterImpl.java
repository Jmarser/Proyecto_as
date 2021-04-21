package com.jmarser.proyecto_as.editFicha.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputLayout;
import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.editFicha.interactor.EditFichaInteractor;
import com.jmarser.proyecto_as.editFicha.view.EditFichaView;
import com.jmarser.proyecto_as.model.Ficha;

public class EditFichaPresenterImpl implements EditFichaPresenter, EditFichaInteractor.OnEditFichaInteractor {

    private Context context;
    private EditFichaView view;
    private EditFichaInteractor interactor;

    public EditFichaPresenterImpl(Context context, EditFichaView view, EditFichaInteractor interactor) {
        this.context = context;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void validarCampos(Spinner sp_horas, TextView newFecha, TextInputLayout descripcion, TextInputLayout observaciones, CheckBox firmado, Ficha ficha) {
        if(sp_horas.getSelectedItemPosition() != 0){
            int horas = Integer.parseInt(sp_horas.getSelectedItem().toString());
            if(!newFecha.getText().toString().isEmpty()){
                if(!descripcion.getEditText().getText().toString().isEmpty()){
                    String descrip = descripcion.getEditText().getText().toString();
                    if(firmado.isChecked()){
                        if(ficha != null){
                            Ficha newFicha = new Ficha();
                            newFicha.setId(ficha.getId());
                            newFicha.setAlumnoId(ficha.getAlumnoId());
                            newFicha.setHoras(horas);
                            newFicha.setFecha(newFecha.getText().toString());
                            newFicha.setDescripcion(descrip);
                            newFicha.setObservaciones(observaciones.getEditText().getText().toString());
                            newFicha.setFirmaAlumno(firmado.isChecked());
                            newFicha.setFirmaProf(false);
                            newFicha.setFirmaTutor(false);
                            if(ficha.isFirmaProf() | ficha.isFirmaTutor()){
                                AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                                        .setTitle(context.getResources().getString(R.string.ConfirmChange))//"Confirmar modificación"
                                        .setMessage("Al modificar la ficha se borraran las firmas de los tutores.")
                                        .setCancelable(false)
                                        .setPositiveButton(context.getResources().getString(android.R.string.yes), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                editFicha(newFicha);
                                            }
                                        })
                                        .setNegativeButton(context.getResources().getString(android.R.string.no), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                dialog.show();
                            }else {
                                editFicha(newFicha);
                            }
                        }else{
                            view.showErrorEdit("No hay ficha para editar");
                        }
                    }else{
                        view.showErrorFirma("Olvida firmar la ficha.");
                    }
                }else{
                    view.showErrorDescripcion("Describa los trabajos realizados.");
                }
            }else{
                view.showErrorFecha("Indique una fecha válida.");
            }
        }else{
            view.showErrorHoras("Debe indicar las horas trabajadas.");
        }
    }

    private void editFicha(Ficha newFicha){
        interactor.EditFicha(newFicha, this);
    }

    @Override
    public void success() {
        view.editFichaOk(context.getResources().getString(R.string.ModifiedCorrectly));
    }

    @Override
    public void errorEditFicha(String mensaje) {
        view.showErrorEdit(mensaje);
    }

    @Override
    public void errorFichaExist(String mensaje) {
        view.errorFichaExist(mensaje);
    }

    @Override
    public void unknowError(String mensaje) {
view.unknowError(mensaje);
    }

}
