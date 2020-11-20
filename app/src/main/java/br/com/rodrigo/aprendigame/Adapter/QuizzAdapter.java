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
        holder.textViewAmountOfQuestions.setText(quizz.getAmountOfQuestions() + " Questões");
        holder.textViewQuizzValue.setText(quizz.getValue().toString());
        holder.textViewCodeQuizz.setText("Código: " + quizz.getCode());

        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
            builder.setTitle("Atenção").setMessage("Você deseja começar o questionario?").setPositiveButton("Sim", (dialogInterface, i) -> {
                Intent intent = new Intent(v.getContext(), QuizzQuestionActivity.class);
                intent.putExtra("questionario", String.valueOf(quizz.getId()));
                v.getContext().startActivity(intent);
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
        @BindView(R.id.textViewQuizzValue)
        TextView textViewQuizzValue;
        @BindView(R.id.textViewCodeQuizz)
        TextView textViewCodeQuizz;
        @BindView(R.id.textViewQuizzAmountOfQuestions)
        TextView textViewAmountOfQuestions;

        public QuizzViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
