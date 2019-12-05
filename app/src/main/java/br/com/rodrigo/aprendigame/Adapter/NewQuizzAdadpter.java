package br.com.rodrigo.aprendigame.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alespero.expandablecardview.ExpandableCardView;

import java.util.List;

import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewQuizzAdadpter extends RecyclerView.Adapter<NewQuizzAdadpter.NewQuizzViewHolder> {

    private Context context;
    private List<Quizz> quizzes;

    public NewQuizzAdadpter(List<Quizz> quizzes,Context context) {
        this.quizzes = quizzes;
        this.context = context;
    }

    @NonNull
    @Override
    public NewQuizzViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_quizz_adapter, parent,false);
        return new NewQuizzViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewQuizzViewHolder holder, int position) {
        Quizz quizz = quizzes.get(position);

        holder.expandableCardView.setTitle(quizz.getTitle());
        holder.radioButtonOne.setText("1- " + quizz.getQuestions().get(0).getQuestionTitle());
        holder.radioButtonTwo.setText("2- " + quizz.getQuestions().get(1).getQuestionTitle());
        holder.radioButtonThree.setText("3- " + quizz.getQuestions().get(2).getQuestionTitle());
        holder.radioButtonFour.setText("4- " + quizz.getQuestions().get(3).getQuestionTitle());
        holder.radioButtonFive.setText("5- " + quizz.getQuestions().get(4).getQuestionTitle());
        holder.radioButtonSix.setText("6- " + quizz.getQuestions().get(5).getQuestionTitle());
        holder.radioButtonSeven.setText("7- " + quizz.getQuestions().get(6).getQuestionTitle());
        holder.radioButtonEight.setText("8- " + quizz.getQuestions().get(7).getQuestionTitle());
        holder.radioButtonNine.setText("9- " + quizz.getQuestions().get(8).getQuestionTitle());
        holder.radioButtonTen.setText("10- " + quizz.getQuestions().get(9).getQuestionTitle());
    }

    @Override
    public int getItemCount() {
        if (quizzes != null){
            return quizzes.size();
        } else {
            return 0;
        }
    }

    public class NewQuizzViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.expandedCarView)
        ExpandableCardView expandableCardView;
        @BindView(R.id.radioButtonQuestionOne)
        CheckBox radioButtonOne;
        @BindView(R.id.radioButtonQuestionTwo)
        CheckBox radioButtonTwo;
        @BindView(R.id.radioButtonQuestionThree)
        CheckBox radioButtonThree;
        @BindView(R.id.radioButtonQuestionFour)
        CheckBox radioButtonFour;
        @BindView(R.id.radioButtonQuestionFive)
        CheckBox radioButtonFive;
        @BindView(R.id.radioButtonQuestionSix)
        CheckBox radioButtonSix;
        @BindView(R.id.radioButtonQuestionSeven)
        CheckBox radioButtonSeven;
        @BindView(R.id.radioButtonQuestionEigth)
        CheckBox radioButtonEight;
        @BindView(R.id.radioButtonQuestionNine)
        CheckBox radioButtonNine;
        @BindView(R.id.radioButtonQuestionTen)
        CheckBox radioButtonTen;

        public NewQuizzViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
