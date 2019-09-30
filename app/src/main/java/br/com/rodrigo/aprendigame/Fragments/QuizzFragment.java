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

import br.com.rodrigo.aprendigame.Adapter.QuizzAdapter;
import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizzFragment extends Fragment {

    private QuizzAdapter adapter;
    private List<Quizz> quizzes = new ArrayList<>();

    public QuizzFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_quizz, container, false);
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
            case R.id.buttonSearchQuizz:
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerViewQuizzes = getView().findViewById(R.id.recycleViewQuizzes);
        recyclerViewQuizzes.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new QuizzAdapter((ArrayList<Quizz>) quizzes, getContext());

        try{
            SetupRest.apiService.getListQuizz().enqueue(new Callback<List<Quizz>>() {
                @Override
                public void onResponse(Call<List<Quizz>> call, Response<List<Quizz>> response) {
                    quizzes = response.body();
                    adapter.updateQuizzes(quizzes);
                }

                @Override
                public void onFailure(Call<List<Quizz>> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
        recyclerViewQuizzes.setAdapter(adapter);
    }
}
