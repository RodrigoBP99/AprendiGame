package br.com.rodrigo.aprendigame.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Activity.CourseClassActivity;
import br.com.rodrigo.aprendigame.Model.CoursesUnit;
import br.com.rodrigo.aprendigame.Model.Teacher;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;

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

        courseUnitViewHolder.textViewTeachersCourseUnit.setText(getTeachersName(coursesUnit));
        courseUnitViewHolder.textViewCourseClassTotal.setText("Aulas: \n" + coursesUnit.getCourseClasses().size());
        courseUnitViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseClassActivity.class);
                intent.putExtra(COURSEUNIT, String.valueOf(coursesUnit.getId()));
                context.startActivity(intent);
            }
        });

    }

    private String getTeachersName(CoursesUnit coursesUnit) {
        String teachersName = "";
        for(Teacher teacher : coursesUnit.getTeacher()){
            if (coursesUnit.getTeacher().size() > 1) {
                teachersName += teacher.getName() + "; ";
            } else {
                teachersName = teacher.getName();
            }
        }
        return teachersName;
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
        @BindView(R.id.textViewNameCourseUnit)
        TextView textViewNameCourseUnit;
        @BindView(R.id.textViewTeachersCourseUnit)
        TextView textViewTeachersCourseUnit;
        @BindView(R.id.textViewTotalCourseClass)
        TextView textViewCourseClassTotal;

        public CourseUnitViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
