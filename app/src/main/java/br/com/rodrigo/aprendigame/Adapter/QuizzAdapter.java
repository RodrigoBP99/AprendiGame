package br.com.rodrigo.aprendigame.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
    public void onBindViewHolder(@NonNull final QuizzViewHolder quizzViewHolder, final int posicao) {

        final Quizz quizz = quizzes.get(posicao);
        quizzViewHolder.textViewTitleQuizz.setText(quizz.getTitle());
        quizzViewHolder.textViewTeacherQuizz.setText(quizz.getTeacher().getName());
        quizzViewHolder.textViewCourseUnitQuizz.setText(quizz.getCourseUnit().getName());
        quizzViewHolder.textViewAmountQuestionsQuizz.setText(quizz.getAmountOfQuestions());

//        quizzViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, context.getString(R.string.abrindo) + " " + quizz.getTitle(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, QuizzActivity.class);
//                context.startActivity(intent);
//            }
//        });
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
        @BindView(R.id.textViewTeacherQuizz)
        TextView textViewTeacherQuizz;
        @BindView(R.id.textViewCourseUnitQuizz)
        TextView textViewCourseUnitQuizz;
        @BindView(R.id.textViewAmountQuestionsQuizz)
        TextView textViewAmountQuestionsQuizz;

        public QuizzViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
