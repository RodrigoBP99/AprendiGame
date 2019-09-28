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

public class QuestionariosAdapter extends RecyclerView.Adapter<QuestionariosAdapter.ViewHolderQuestionario>{

    private Context context;
    private ArrayList<Quizz> questionarios;

    public QuestionariosAdapter(ArrayList<Quizz> questionarios, Context context){
        this.questionarios = questionarios;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderQuestionario onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewQuestionario = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.questionarios_adapter, viewGroup, false);
        return new ViewHolderQuestionario(viewQuestionario);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderQuestionario viewHolder, final int posicao) {

        final Quizz questionario = questionarios.get(posicao);
        viewHolder.textViewTituloQuestionario.setText(questionario.getTitle());
        viewHolder.textViewAutorQuestionario.setText(questionario.getTeacher().getName());
        viewHolder.textViewMateriaQuestionario.setText(questionario.getCourseUnit().getName());
        viewHolder.textViewNotaQuestionario.setText(questionario.getAmountOfQuestions());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Abrindo " + questionario.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, QuestionarioActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (questionarios != null){
            return questionarios.size();
        } else {
            return 0;
        }
    }

    public void updateQuizzes(List<Quizz> quizzes){
        this.questionarios = (ArrayList<Quizz>) quizzes;
        notifyDataSetChanged();
    }

    public class ViewHolderQuestionario extends RecyclerView.ViewHolder{

        private TextView textViewTituloQuestionario;
        private TextView textViewAutorQuestionario;
        private TextView textViewMateriaQuestionario;
        private TextView textViewNotaQuestionario;

        public ViewHolderQuestionario(@NonNull View itemView) {
            super(itemView);

            textViewTituloQuestionario = itemView.findViewById(R.id.textViewTituloQuestionario);
            textViewAutorQuestionario = itemView.findViewById(R.id.textViewAutorQuestionario);
            textViewMateriaQuestionario = itemView.findViewById(R.id.textViewMateriaQuestionario);
            textViewNotaQuestionario = itemView.findViewById(R.id.textViewNotaQuestionario);
        }
    }
}
