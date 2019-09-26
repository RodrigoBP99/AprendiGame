package br.com.rodrigo.aprendigame.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private ArrayList<Questionario> questionarios = new ArrayList<>();

    public QuestionariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_questionarios, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar_main_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.buttonSeachQuestionario:
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerViewQuestionarios = getView().findViewById(R.id.recycleViewQuestionarios);
        recyclerViewQuestionarios.setLayoutManager(new LinearLayoutManager(getContext()));


        Questionario questionario1 = new Questionario("Segunda Guerra Mundial",
                "Marcelo Oliveira", "Historia", "--/10");
        questionarios.add(questionario1);

        adapter = new QuestionariosAdapter(questionarios, getContext());
        recyclerViewQuestionarios.setAdapter(adapter);
    }
}
