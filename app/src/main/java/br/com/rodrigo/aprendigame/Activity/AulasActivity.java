package br.com.rodrigo.aprendigame.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Adapter.CoursesAdapter;
import br.com.rodrigo.aprendigame.Model.CoursesUnit;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AulasActivity extends AppCompatActivity {

    private CoursesAdapter aulaAdapter;
    private List<CoursesUnit> coursesUnitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aulas);

        setListAulaRecycle();

    }

    private void setListAulaRecycle() {
        RecyclerView recyclerViewAulas = findViewById(R.id.recycleViewAulas);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewAulas.addItemDecoration(itemDecoration);
        recyclerViewAulas.setLayoutManager(new LinearLayoutManager(this));

        aulaAdapter = new CoursesAdapter((ArrayList<CoursesUnit>) coursesUnitList, this);
        getAula();
        recyclerViewAulas.setAdapter(aulaAdapter);
    }

    private void getAula() {
        try {
            SetupRest.apiService.getListCourseUnit(1L).enqueue(new Callback<List<CoursesUnit>>() {
                @Override
                public void onResponse(Call<List<CoursesUnit>> call, Response<List<CoursesUnit>> response) {
                    coursesUnitList = response.body();
                    aulaAdapter.atualiza(coursesUnitList);
                }

                @Override
                public void onFailure(Call<List<CoursesUnit>> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }
}