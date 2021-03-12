package com.jmarser.proyecto_as.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jmarser.proyecto_as.R;
import com.jmarser.proyecto_as.model.Alumno;
import com.jmarser.proyecto_as.utils.Constantes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdaptadorAlumno extends RecyclerView.Adapter<AdaptadorAlumno.AlumnoViewHolder> {

    private List<Alumno>  alumnos;
    private Context context;
    private int posicion;

    public AdaptadorAlumno(List<Alumno> alumnos, Context context) {
        this.alumnos = alumnos;
        this.context = context;
    }

    @NonNull
    @Override
    public AlumnoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlumnoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.alumnos_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoViewHolder holder, int position) {
        holder.bindData(alumnos.get(position));
    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }


    /*Clase interna*/
    class AlumnoViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_nombre_alumno)
        TextView tv_nombre_alumno;
        @BindView(R.id.tv_fichas)
        TextView tv_fichas;
        @BindView(R.id.pb_alumno)
        ProgressBar pb_alumno;
        @BindView(R.id.tv_info_total_horas)
        TextView tv_total_horas;

        public AlumnoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Alumno alumno){
            pb_alumno.setMax(Constantes.HORAS_PRACTICAS);
            int horas = 0;
            int fichas = alumno.getFichas().size();
            if(alumno.getFichas()!=null){
                for(int i = 0; i<alumno.getFichas().size(); i++){
                    horas += alumno.getFichas().get(i).getHoras();
                }
            }

            tv_nombre_alumno.setText(alumno.toString());
            tv_fichas.setText(String.valueOf(fichas));
            pb_alumno.setProgress(horas);
            tv_total_horas.setText(horas + "/"+ Constantes.HORAS_PRACTICAS);
        }
    }
}
