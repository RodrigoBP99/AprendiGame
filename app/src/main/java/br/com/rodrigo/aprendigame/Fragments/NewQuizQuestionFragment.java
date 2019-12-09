package br.com.rodrigo.aprendigame.Fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Activity.NewQuizzActivity;
import br.com.rodrigo.aprendigame.Adapter.NewQuizzAdadpter;
import br.com.rodrigo.aprendigame.Model.Question;
import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewQuizQuestionFragment extends Fragment {


    @BindView(R.id.textViewClassTitle)
    TextView textView;
    @BindView(R.id.recycleViewNewQuizz)
    RecyclerView recyclerView;

    private List<Question> questions = new ArrayList<>();

    public NewQuizQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_quiz_question, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getActivity());

        Quizz quizz = NewQuizzActivity.quizzes.get(NewQuizzActivity.quizId);

        textView.setText(quizz.getTitle());
        for (Question question : quizz.getQuestions()){
            questions.add(question);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        NewQuizzAdadpter newQuizzAdadpter = new NewQuizzAdadpter(questions, getActivity());

        recyclerView.setAdapter(newQuizzAdadpter);

    }
}
