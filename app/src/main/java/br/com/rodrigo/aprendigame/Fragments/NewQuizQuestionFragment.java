package br.com.rodrigo.aprendigame.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import br.com.rodrigo.aprendigame.Activity.NewQuizzActivity;
import br.com.rodrigo.aprendigame.Adapter.NewQuizzAdadpter;
import br.com.rodrigo.aprendigame.Model.Question;
import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewQuizQuestionFragment extends Fragment {


    @BindView(R.id.textViewClassTitle)
    TextView textView;
    @BindView(R.id.recycleViewNewQuizz)
    RecyclerView recyclerView;
    @BindView(R.id.tabLayoutNewQuiz)
    TabLayout tabLayout;

    private Quizz quizz;
    private ArrayList<Question> selectedQuestions = new ArrayList<>();
    private ArrayList<Quizz> quizzes = new ArrayList<>();
    private ArrayList<Question> quizQuestions = new ArrayList<>();
    private NewQuizzAdadpter newQuizzAdadpter;

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

        quizzes = (ArrayList<Quizz>) getArguments().getSerializable(NewQuizzActivity.LISTQUIZ);
        int quizID = getArguments().getInt(NewQuizzActivity.QUIZID);
        quizz = quizzes.get(quizID);
        textView.setText(quizz.getTitle());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        quizQuestions = (ArrayList<Question>) quizz.getQuestions();

        newQuizzAdadpter = new NewQuizzAdadpter(quizQuestions, getActivity(), new NewQuizzAdadpter.OnItemCheckListener() {
            @Override
            public void onItemCheck(Question question) {
                selectedQuestions.add(question);
            }

            @Override
            public void onItemUncheck(Question question) {
                selectedQuestions.remove(question);
            }
        });
        recyclerView.setAdapter(newQuizzAdadpter);
    }

    private CheckedQuestionsPass mCallback;

    public interface CheckedQuestionsPass {
        void listQuestion(ArrayList<Question> questions);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try
        {
            mCallback = (CheckedQuestionsPass) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+ " must implement OnImageClickListener");
        }
    }

    @OnClick(R.id.buttonAddQuestions) void addQuestions(){
        if (selectedQuestions.size() != 0) {
            //adicionar questões selecionadas para uma lista e tirar aula da lista.
            mCallback.listQuestion(selectedQuestions);
            //remove questões selecionadas da aula
            quizQuestions.removeAll(selectedQuestions);
            newQuizzAdadpter.update(quizQuestions);
            if (quizQuestions.size() == 0){
                tabLayout.removeTabAt(tabLayout.getSelectedTabPosition());
                quizzes.remove(quizz.getId());
            }
        }
    }
}
