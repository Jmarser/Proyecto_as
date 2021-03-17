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

public class AdaptadorFicha extends RecyclerView.Adapter<AdaptadorFicha.FichaViewHolder> {

    private final List<Ficha> fichas;
    private Context contexto;
    private final ItemClickListener itemClickListener;

    public AdaptadorFicha(List<Ficha> fichas, Context contexto, ItemClickListener itemClickListener) {
        this.fichas = fichas;
        this.contexto = contexto;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public FichaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FichaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ficha_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FichaViewHolder holder, int position) {
        holder.bindData(fichas.get(position));

        /*Damos funcionalidad al hacer click en el elmento del recyclerView*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClickListener(fichas.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return fichas.size();
    }

    /*clase interna*/
    static class FichaViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_descripcion;
        private final TextView tv_observaciones;
        private final CheckBox cb_firma_alumno;
        private final CheckBox cb_firma_profesor;
        private final CheckBox cb_firma_tutor;


        public FichaViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_descripcion = itemView.findViewById(R.id.tv_descripcion);
            tv_observaciones = itemView.findViewById(R.id.tv_observaciones);
            cb_firma_alumno = itemView.findViewById(R.id.cb_firma_alumno);
            cb_firma_profesor = itemView.findViewById(R.id.cb_firma_profesor);
            cb_firma_tutor = itemView.findViewById(R.id.cb_firma_tutor);

        }

        public void bindData(Ficha ficha) {
            tv_descripcion.setText(ficha.getDescripcion());
            tv_observaciones.setText(ficha.getObservaciones());
            cb_firma_alumno.setChecked(ficha.isFirmaAlumno());
            cb_firma_profesor.setChecked(ficha.isFirmaProf());
            cb_firma_tutor.setChecked(ficha.isFirmaTutor());
        }

    }

    /*Interface que nos permitirá darle funcionalidad al click de los elementos del recyclerView*/
    public interface ItemClickListener {

        void onItemClickListener(Ficha ficha);
    }
}
