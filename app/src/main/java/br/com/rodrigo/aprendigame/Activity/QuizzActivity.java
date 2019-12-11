package br.com.rodrigo.aprendigame.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Adapter.QuizzAdapter;
import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizzActivity extends AppCompatActivity {

    private QuizzAdapter adapter;
    private List<Quizz> quizzes;
    @BindView(R.id.recycleViewQuizz)
    RecyclerView recyclerViewQuizz;
    @BindView(R.id.textViewTitleToolbarMain)
    TextView textViewTitleToolbar;
    @BindView(R.id.toolbarQuizzActivity)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);
        ButterKnife.bind(this);

        toolbarMenu();

    }

    private void toolbarMenu() {
        toolbar.inflateMenu(R.menu.toolbar_quizz_activity);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.itemNewQuizz:
                    Intent intent = new Intent(QuizzActivity.this, NewQuizzActivity.class);
                    startActivity(intent);
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        textViewTitleToolbar.setText(getString(R.string.questionarios));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewQuizz.addItemDecoration(itemDecoration);
        recyclerViewQuizz.setLayoutManager(new LinearLayoutManager(this));
        getQuizzList();
        adapter = new QuizzAdapter((ArrayList<Quizz>) quizzes, this);
        recyclerViewQuizz.setAdapter(adapter);
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
            e.printStackTrace();
        }
    }
}
