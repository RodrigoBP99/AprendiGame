package br.com.rodrigo.aprendigame.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.Activity.QuestionarioActivity;
import br.com.rodrigo.aprendigame.R;

public class QuizzAdapter extends RecyclerView.Adapter<QuizzAdapter.ViewHolderQuizz>{

    private Context context;
    private ArrayList<Quizz> quizzes;

    public QuizzAdapter(ArrayList<Quizz> questionarios, Context context){
        this.quizzes = questionarios;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderQuizz onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewQuizz = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.questionarios_adapter, viewGroup, false);
        return new ViewHolderQuizz(viewQuizz);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderQuizz viewHolder, final int posicao) {

        final Quizz quizz = quizzes.get(posicao);
        viewHolder.textViewTitleQuizz.setText(quizz.getTitle());
        viewHolder.textViewTeacherQuizz.setText(quizz.getTeacher().getName());
        viewHolder.textViewCourseUnitQuizz.setText(quizz.getCourseUnit().getName());
        viewHolder.textViewAmountQuestionsQuizz.setText(quizz.getAmountOfQuestions());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Abrindo " + quizz.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, QuestionarioActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (quizzes != null){
            return quizzes.size();
        } else {
            return 0;
        }
    }

    public void updateQuizzes(List<Quizz> quizzes){
        this.quizzes = (ArrayList<Quizz>) quizzes;
        notifyDataSetChanged();
    }

    public class ViewHolderQuizz extends RecyclerView.ViewHolder{

        private TextView textViewTitleQuizz;
        private TextView textViewTeacherQuizz;
        private TextView textViewCourseUnitQuizz;
        private TextView textViewAmountQuestionsQuizz;

        public ViewHolderQuizz(@NonNull View itemView) {
            super(itemView);

            textViewTitleQuizz = itemView.findViewById(R.id.textViewTitleQuizz);
            textViewTeacherQuizz = itemView.findViewById(R.id.textViewTeacherQuizz);
            textViewCourseUnitQuizz = itemView.findViewById(R.id.textViewCourseUnitQuizz);
            textViewAmountQuestionsQuizz = itemView.findViewById(R.id.textViewAmountQuestionsQuizz);
        }
    }
}
