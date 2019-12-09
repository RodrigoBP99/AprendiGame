package br.com.rodrigo.aprendigame.Fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import br.com.rodrigo.aprendigame.Activity.NewQuizzActivity;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getActivity());

    }

    @OnClick(R.id.buttonConfirmarNovoQuizz) void newQuiz(){
        String texto = editTextTitleNewQuizz.getText().toString();
        if (!texto.isEmpty()) {
            try {
                Quizz quizz = new Quizz();
                quizz.setTitle(texto);
                quizz.setQuestions(NewQuizzActivity.questions);
                quizz.setAmountOfQuestions(String.valueOf(NewQuizzActivity.questions.size()));
            } catch (Exception e){
                Log.e("Error Quizz: ", e.getMessage());
            }
        } else {
            Toast.makeText(getContext(), "Por favor Preencha os dados necessarios!", Toast.LENGTH_LONG).show();
        }
    }
}
