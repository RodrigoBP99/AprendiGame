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
import java.util.List;

import br.com.rodrigo.aprendigame.Adapter.QuestionariosAdapter;
import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionariosFragment extends Fragment {

    private QuestionariosAdapter adapter;
    private List<Quizz> questionarios = new ArrayList<>();

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

        adapter = new QuestionariosAdapter((ArrayList<Quizz>) questionarios, getContext());

        try{
            SetupRest.apiService.getListQuizz().enqueue(new Callback<List<Quizz>>() {
                @Override
                public void onResponse(Call<List<Quizz>> call, Response<List<Quizz>> response) {
                    questionarios = response.body();
                    adapter.updateQuizzes(questionarios);
                }

                @Override
                public void onFailure(Call<List<Quizz>> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
        recyclerViewQuestionarios.setAdapter(adapter);
    }
}
