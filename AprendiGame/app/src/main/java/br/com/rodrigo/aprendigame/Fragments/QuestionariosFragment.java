package br.com.rodrigo.aprendigame.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.rodrigo.aprendigame.Adapter.QuestionariosAdapter;
import br.com.rodrigo.aprendigame.Model.Questionario;
import br.com.rodrigo.aprendigame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionariosFragment extends Fragment {

    private QuestionariosAdapter adapter;
    ArrayList<Questionario> questionarios = new ArrayList<>();

    public QuestionariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questionarios, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerViewQuestionarios = getView().findViewById(R.id.recycleView_questionarios);
        recyclerViewQuestionarios.setLayoutManager(new LinearLayoutManager(getContext()));


        Questionario questionario1 = new Questionario("Segunda Guerra Mundial", "Marcelo Oliveira", "Historia", "--/10");

        questionarios.add(questionario1);

        Questionario questionario2 = new Questionario("Trigonometria", "Pedro Costa", "Matematica", "--/10");

        questionarios.add(questionario2);

        Questionario questionario3 = new Questionario("Conjugação Verbal", "Priscila Santos", "Portugues", "--/10");

        questionarios.add(questionario3);

        Questionario questionario4 = new Questionario("Futebol", "Robson Alves", "Educação Fisica", "--/10");

        questionarios.add(questionario4);

        Questionario questionario5 = new Questionario("Revolução Industrial", "Marcelo Oliveira", "Historia", "--/10");

        questionarios.add(questionario5);

        Questionario questionario6 = new Questionario("Equação do Segundo Gral", "Pedro Costa", "Matematica", "--/10");

        questionarios.add(questionario6);



        adapter = new QuestionariosAdapter(questionarios);
        recyclerViewQuestionarios.setAdapter(adapter);
    }
}
