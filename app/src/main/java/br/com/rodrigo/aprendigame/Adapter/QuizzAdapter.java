package br.com.rodrigo.aprendigame.Adapter;

import android.app.AlertDialog;
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

import br.com.rodrigo.aprendigame.Activity.QuizzQuestionActivity;
import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizzAdapter extends RecyclerView.Adapter<QuizzAdapter.QuizzViewHolder>{

    private Context context;
    private ArrayList<Quizz> quizzes;
    public final static String QUESTIONARIO = "questionario";

    public QuizzAdapter(ArrayList<Quizz> questionarios, Context context){
        this.quizzes = questionarios;
        this.context = context;
    }

    @NonNull
    @Override
    public QuizzViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewQuizz = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quizzes_adapter, viewGroup, false);
        return new QuizzViewHolder(viewQuizz);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuizzViewHolder holder, final int posicao) {

        Quizz quizz = quizzes.get(posicao);
        holder.textViewTitleQuizz.setText(quizz.getTitle());
        holder.textViewQuizzAnswers.setText(  "10/"+ quizz.getAmountOfQuestions());
//        holder.textViewQuizzCreateDate.setText();

        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
            builder.setTitle("Atenção").setMessage("Você deseja começar o questionario?").setPositiveButton("Sim", (dialogInterface, i) -> {
                Intent intent = new Intent(context, QuizzQuestionActivity.class);
                intent.putExtra(QUESTIONARIO, quizz);
                context.startActivity(intent);
            }).setNegativeButton("Não", null).create().show();
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

    public class QuizzViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.textViewTitleQuizz)
        TextView textViewTitleQuizz;
        @BindView(R.id.textViewAnswersQuizz)
        TextView textViewQuizzAnswers;
        @BindView(R.id.textViewDateQuizz)
        TextView textViewQuizzCreateDate;

        public QuizzViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
