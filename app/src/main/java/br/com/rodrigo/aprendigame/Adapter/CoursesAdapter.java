package br.com.rodrigo.aprendigame.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Activity.PresencaRealizadaActivity;
import br.com.rodrigo.aprendigame.Model.CoursesUnit;
import br.com.rodrigo.aprendigame.R;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.AulaViewHolder> {

    private ArrayList<CoursesUnit> coursesUnitArrayList;
    private Context context;
    private String AULA = "aula";

    public CoursesAdapter(ArrayList<CoursesUnit> coursesUnitArrayList, Context context) {
        this.coursesUnitArrayList = coursesUnitArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AulaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.aula_adapter, viewGroup, false);
        return new AulaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AulaViewHolder aulaViewHolder, int posicao) {
        final CoursesUnit coursesUnit = coursesUnitArrayList.get(posicao);

        aulaViewHolder.textViewNomeAula.setText(coursesUnit.getName());
        aulaViewHolder.textViewProfessorAula.setText("");
        aulaViewHolder.textViewAulaCurso.setText("");

        aulaViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PresencaRealizadaActivity.class);
                intent.putExtra(AULA, String.valueOf(coursesUnit.getId()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(coursesUnitArrayList != null){
            return coursesUnitArrayList.size();
        } else {
            return 0;
        }
    }

    public void atualiza(List<CoursesUnit> coursesUnitList){
        this.coursesUnitArrayList = (ArrayList<CoursesUnit>) coursesUnitList;
        notifyDataSetChanged();
    }

    public class AulaViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNomeAula;
        private TextView textViewProfessorAula;
        private TextView textViewAulaCurso;

        public AulaViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNomeAula = itemView.findViewById(R.id.textViewNomeAula);
            textViewProfessorAula = itemView.findViewById(R.id.textViewProfessorAula);
            textViewAulaCurso = itemView.findViewById(R.id.textViewNomeCursoAula);

        }
    }
}
