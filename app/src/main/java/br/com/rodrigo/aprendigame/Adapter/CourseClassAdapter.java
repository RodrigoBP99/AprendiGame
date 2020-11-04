package br.com.rodrigo.aprendigame.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Activity.QuizzActivity;
import br.com.rodrigo.aprendigame.Model.CourseClass;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseClassAdapter extends RecyclerView.Adapter<CourseClassAdapter.CourseUnitViewHolder> {

    private ArrayList<CourseClass> courseClasses;
    private Context context;
    private String COURSECLASS = "courseUnit";

    public CourseClassAdapter(ArrayList<CourseClass> courseClasses, Context context) {
        this.courseClasses = courseClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseUnitViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_class_adapter, viewGroup, false);
        return new CourseUnitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseUnitViewHolder courseUnitViewHolder, int posicao) {
        final CourseClass courseClass = courseClasses.get(posicao);

        courseUnitViewHolder.textViewNameCourseClass.setText(courseClass.getName());
        courseUnitViewHolder.textViewCourseClassCode.setText("Curso:  " + courseClass.getCourseUnit().getName());
        courseUnitViewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, QuizzActivity.class);
            intent.putExtra(COURSECLASS, String.valueOf(courseClass.getId()));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        if(courseClasses != null){
            return courseClasses.size();
        } else {
            return 0;
        }
    }

    public void atualiza(List<CourseClass> courseClassList){
        this.courseClasses = (ArrayList<CourseClass>) courseClassList;
        notifyDataSetChanged();
    }

    public class CourseUnitViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewNameCourseClass)
        TextView textViewNameCourseClass;
        @BindView(R.id.textViewCourseClassCode)
        TextView textViewCourseClassCode;

        public CourseUnitViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
