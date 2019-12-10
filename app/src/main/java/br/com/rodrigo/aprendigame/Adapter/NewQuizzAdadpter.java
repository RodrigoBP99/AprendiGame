package br.com.rodrigo.aprendigame.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.rodrigo.aprendigame.Model.Question;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewQuizzAdadpter extends RecyclerView.Adapter<NewQuizzAdadpter.NewQuizzViewHolder> {

    private Context context;
    private List<Question> questions;

    public NewQuizzAdadpter(List<Question> questions,Context context, OnItemCheckListener onItemCheckListener) {
        this.questions = questions;
        this.context = context;
        this.onItemCheckListener = onItemCheckListener;
    }

    public interface OnItemCheckListener {
        void onItemCheck(Question question);
        void onItemUncheck(Question question);
    }

    @NonNull
    private OnItemCheckListener onItemCheckListener;

    @NonNull
    @Override
    public NewQuizzViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_quizz_adapter, parent,false);
        return new NewQuizzViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewQuizzViewHolder holder, int position) {
        Question question = questions.get(position);

        holder.checkBox.setText(question.getQuestionTitle());

        holder.setOnClickListener(view -> {
            holder.checkBox.setChecked(!holder.checkBox.isChecked());
            if (holder.checkBox.isChecked()){
                onItemCheckListener.onItemCheck(question);
            } else {
                onItemCheckListener.onItemUncheck(question);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (questions != null){
            return questions.size();
        } else {
            return 0;
        }
    }

    public void update(List<Question> questions){
        this.questions = questions;
        notifyDataSetChanged();
    }

    public class NewQuizzViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkboxQuizQuestion)
        CheckBox checkBox;
        public NewQuizzViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            checkBox.setClickable(false);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}
