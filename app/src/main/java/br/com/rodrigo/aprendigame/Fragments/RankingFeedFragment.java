package br.com.rodrigo.aprendigame.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.rodrigo.aprendigame.Adapter.QuizzAdapter;
import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFeedFragment extends Fragment {

    private QuizzAdapter adapter;
    ArrayList<Quizz> questionarios = new ArrayList<>();

    public RankingFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ranking_feed, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerViewQuestionarios = getView().findViewById(R.id.recycleViewRankingFeed);
        recyclerViewQuestionarios.setLayoutManager(new LinearLayoutManager(getContext()));


        adapter = new QuizzAdapter(questionarios, getContext());
        recyclerViewQuestionarios.setAdapter(adapter);
    }
}
