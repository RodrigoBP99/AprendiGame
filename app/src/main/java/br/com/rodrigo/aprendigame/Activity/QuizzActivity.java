package br.com.rodrigo.aprendigame.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Adapter.QuizzAdapter;
import br.com.rodrigo.aprendigame.Model.CourseClass;
import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizzActivity extends AppCompatActivity {

    public static String LISTQUIZ = "quiz";
    private QuizzAdapter adapter;
    private static List<Quizz> quizzes;
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
                    if (quizzes != null) {
                        Intent intent = new Intent(QuizzActivity.this, NewQuizzActivity.class);
                        intent.putExtra(LISTQUIZ, (ArrayList<Quizz>) quizzes);
                        startActivity(intent);
                    }else {
                        Toast.makeText(QuizzActivity.this, "Parece que essa aula não contem nenhum Quizz", Toast.LENGTH_LONG).show();
                    }
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        textViewTitleToolbar.setText(getString(R.string.questionarios));
        Intent intent = getIntent();
        String courseClassId = intent.getStringExtra("courseUnit");

        getQuizzList(Long.valueOf(courseClassId));
    }

    private void getQuizzList(Long id) {
        SetupRest.apiService.getCourseClass(id).enqueue(new Callback<CourseClass>() {
            @Override
            public void onResponse(Call<CourseClass> call, Response<CourseClass> response) {
                if (response.isSuccessful()){
                    getCourseClassQuizzes(response);
                } else {
                    try {
                        String errormesage = getErroMessage(response);
                        Toast.makeText(QuizzActivity.this, errormesage.toString(), Toast.LENGTH_LONG).show();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CourseClass> call, Throwable t) {
                Toast.makeText(QuizzActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getCourseClassQuizzes(Response<CourseClass> response) {
        quizzes = response.body().getQuizzes();
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewQuizz.addItemDecoration(itemDecoration);
        recyclerViewQuizz.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuizzAdapter((ArrayList<Quizz>) quizzes, this);
        recyclerViewQuizz.setAdapter(adapter);
    }

    private String getErroMessage(Response<CourseClass> response) throws IOException {
        ResponseBody responseBody = response.errorBody();
        return responseBody.string();
    }
}
