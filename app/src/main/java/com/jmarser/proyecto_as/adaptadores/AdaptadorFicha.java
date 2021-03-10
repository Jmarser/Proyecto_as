package com.jmarser.proyecto_as.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.model.Ficha;

import java.util.List;

public class AdaptadorFicha extends RecyclerView.Adapter<AdaptadorFicha.FichaViewHolder>{

    private List<Ficha> fichas;
    private Context contexto;
    private int posicion;

    public AdaptadorFicha(List<Ficha> fichas, Context contexto) {
        this.fichas = fichas;
        this.contexto = contexto;
    }



    @NonNull
    @Override
    public FichaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FichaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ficha_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FichaViewHolder holder, int position) {
        holder.bindData(fichas.get(position));
    }

    @Override
    public int getItemCount() {
        return fichas.size();
    }


    /*clase interna*/
    class FichaViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_descripcion;
        private TextView tv_observaciones;
        private CheckBox cb_firma_alumno;
        private CheckBox cb_firma_profesor;
        private CheckBox cb_firma_tutor;

        public FichaViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_descripcion = itemView.findViewById(R.id.tv_descripcion);
            tv_observaciones = itemView.findViewById(R.id.tv_observaciones);
            cb_firma_alumno = itemView.findViewById(R.id.cb_firma_alumno);
            cb_firma_profesor = itemView.findViewById(R.id.cb_firma_profesor);
            cb_firma_tutor = itemView.findViewById(R.id.cb_firma_tutor);
        }

        public void bindData(Ficha ficha){
            tv_descripcion.setText(ficha.getDescripcion());
            tv_observaciones.setText(ficha.getObservaciones());
            cb_firma_alumno.setChecked(ficha.isFirmaAlumno());
            cb_firma_profesor.setChecked(ficha.isFirmaProf());
            cb_firma_tutor.setChecked(ficha.isFirmaTutor());
        }
    }
}
