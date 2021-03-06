package br.com.rodrigo.aprendigame.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
public class NewQuizFragment extends Fragment {


    @BindView(R.id.editTextTituloNewQuizz)
    EditText editTextTitleNewQuizz;
    @BindView(R.id.recycleViewNewQuizQuestions)
    RecyclerView recyclerView;

    private String titleNewQuiz;
    private static List<Question> questions = new ArrayList<>();
    private ArrayList<Question> selectedQuestions = new ArrayList<>();
    private NewQuizzAdadpter newQuizzAdadpter;

    public NewQuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_quiz, container, false);
    }

    @Override
    public void onStop() {
        super.onStop();
        titleNewQuiz = editTextTitleNewQuizz.getText().toString();
        mCallback.titleNewQuiz(titleNewQuiz);
    }

    private TitleNewQuizPass mCallback;

    public interface TitleNewQuizPass {
        void titleNewQuiz(String titleNewQuiz);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try
        {
            mCallback = (TitleNewQuizPass) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+ " must implement OnImageClickListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getActivity());

       /* if (savedInstanceState != null) {
            editTextTitleNewQuizz.setText(savedInstanceState.getString("titleQuiz"));
        }*/
        getArgs();

        editTextTitleNewQuizz.setText(titleNewQuiz);
        questions = selectedQuestions;
        newQuizzAdadpter = new NewQuizzAdadpter(questions, getActivity(), new NewQuizzAdadpter.OnItemCheckListener() {
            @Override
            public void onItemCheck(Question question) {

            }

            @Override
            public void onItemUncheck(Question question) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(newQuizzAdadpter);
    }

    private void getArgs() {
        if (getArguments() != null) {
            ArrayList<Question> argQuestions = (ArrayList<Question>) getArguments().getSerializable(NewQuizzActivity.CHECKEDQUESTIONS);
            selectedQuestions.addAll(argQuestions);
            titleNewQuiz = getArguments().getString(NewQuizzActivity.TITLENEWQUIZ);
        }
    }

    @OnClick(R.id.buttonConfirmarNovoQuizz)
    void newQuiz() {
        titleNewQuiz = editTextTitleNewQuizz.getText().toString();
        if (!titleNewQuiz.isEmpty() && questions.size() > 0) {
            try {
                Quizz quizz = new Quizz();
                quizz.setTitle(titleNewQuiz);
                quizz.setQuestions(questions);
                quizz.setAmountOfQuestions(String.valueOf(questions.size()));
            } catch (Exception e) {
                Log.e("Error Quizz: ", e.getMessage());
            }
        } else {
            Toast.makeText(getContext(), "Por favor Preencha os dados necessarios!", Toast.LENGTH_LONG).show();
        }
    }

}
