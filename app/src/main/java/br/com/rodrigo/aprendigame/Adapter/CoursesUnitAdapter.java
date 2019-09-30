package br.com.rodrigo.aprendigame.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Activity.PresencaRealizadaActivity;
import br.com.rodrigo.aprendigame.Model.CoursesUnit;
import br.com.rodrigo.aprendigame.R;

public class CoursesUnitAdapter extends RecyclerView.Adapter<CoursesUnitAdapter.CourseUnitViewHolder> {

    private ArrayList<CoursesUnit> coursesUnits;
    private Context context;
    private String COURSEUNIT = "courseUnit";

    public CoursesUnitAdapter(ArrayList<CoursesUnit> coursesUnits, Context context) {
        this.coursesUnits = coursesUnits;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseUnitViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_unit_adapter, viewGroup, false);
        return new CourseUnitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseUnitViewHolder courseUnitViewHolder, int posicao) {
        final CoursesUnit coursesUnit = coursesUnits.get(posicao);

        courseUnitViewHolder.textViewNameCourseUnit.setText(coursesUnit.getName());
        courseUnitViewHolder.textViewTeachersCourseUnit.setText("");

        courseUnitViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PresencaRealizadaActivity.class);
                intent.putExtra(COURSEUNIT, String.valueOf(coursesUnit.getId()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(coursesUnits != null){
            return coursesUnits.size();
        } else {
            return 0;
        }
    }

    public void atualiza(List<CoursesUnit> coursesUnitList){
        this.coursesUnits = (ArrayList<CoursesUnit>) coursesUnitList;
        notifyDataSetChanged();
    }

    public class CourseUnitViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNameCourseUnit;
        private TextView textViewTeachersCourseUnit;

        public CourseUnitViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNameCourseUnit = itemView.findViewById(R.id.textViewNameCourseUnit);
            textViewTeachersCourseUnit = itemView.findViewById(R.id.textViewTeachersCourseUnit);
        }
    }
}