package br.com.rodrigo.aprendigame.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Adapter.QuizzAdapter;
import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizzActivity extends AppCompatActivity {
    private QuizzAdapter adapter;
    private List<Quizz> quizzes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        RecyclerView recyclerView = findViewById(R.id.recycleViewQuizz);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getQuizzList();
        adapter = new QuizzAdapter((ArrayList<Quizz>) quizzes, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    private void getQuizzList() {
        try {
            SetupRest.apiService.getListQuizz().enqueue(new Callback<List<Quizz>>() {
                @Override
                public void onResponse(Call<List<Quizz>> call, Response<List<Quizz>> response) {
                        quizzes = response.body();
                        adapter.updateQuizzes(quizzes);

                }

                @Override
                public void onFailure(Call<List<Quizz>> call, Throwable t) {
                    Log.e("QuizzCallbackErro: ", t.getMessage());
                }
            });
        } catch (Exception e){

        }
    }
}
