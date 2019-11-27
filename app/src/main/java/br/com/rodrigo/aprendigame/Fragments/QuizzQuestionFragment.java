package br.com.rodrigo.aprendigame.Fragments;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizzQuestionFragment extends Fragment {

    private int respostaId;
    @BindView(R.id.textViewQuestionTitle)
    TextView textViewQuizzTitle;
    @BindView(R.id.radioButtonAnswerOne)
    RadioButton radioButtonOne;
    @BindView(R.id.radioButtonAnswerTwo)
    RadioButton radioButtonTwo;
    @BindView(R.id.radioButtonAnswerThree)
    RadioButton radioButtonThree;
    @BindView(R.id.radioButtonAnswerFour)
    RadioButton radioButtonFour;
    @BindView(R.id.radioGroupAnswers)
    RadioGroup radioGroup;

    private RadioButton radioButton;

    public QuizzQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quizz_question, container, false);
    }

    @OnClick(R.id.buttonGetAnswer) void proximaQuestao(){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = getActivity().findViewById(radioId);
        if (radioButton == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Tem certeza que deseja avançar sem marcar uma resposta?").setPositiveButton("Sim", (dialogInterface, i) ->
            {

            }).setNegativeButton("Não", null).show();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this,getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();

        textViewQuizzTitle.setText("Pergunta");
        radioButtonOne.setText("Primeira Resposta");
        radioButtonTwo.setText("Segunda Resposta");
        radioButtonThree.setText("Terceira Resposta");
        radioButtonFour.setText("Quarta Resposta");
    }
}
