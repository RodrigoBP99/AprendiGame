package br.com.rodrigo.aprendigame.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Adapter.CoursesUnitAdapter;
import br.com.rodrigo.aprendigame.Model.CoursesUnit;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseUnitActivity extends AppCompatActivity {

    private CoursesUnitAdapter courseUnitAdapter;
    private List<CoursesUnit> coursesUnitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_unit);

        setListAulaRecycle();

    }

    private void setListAulaRecycle() {
        RecyclerView recyclerViewAulas = findViewById(R.id.recycleViewAulas);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewAulas.addItemDecoration(itemDecoration);
        recyclerViewAulas.setLayoutManager(new LinearLayoutManager(this));

        courseUnitAdapter = new CoursesUnitAdapter((ArrayList<CoursesUnit>) coursesUnitList, this);
        getCourseUnit();
        recyclerViewAulas.setAdapter(courseUnitAdapter);
    }

    private void getCourseUnit() {
        try {
            SetupRest.apiService.getListCourseUnit(1L).enqueue(new Callback<List<CoursesUnit>>() {
                @Override
                public void onResponse(Call<List<CoursesUnit>> call, Response<List<CoursesUnit>> response) {
                    coursesUnitList = response.body();
                    courseUnitAdapter.atualiza(coursesUnitList);
                }

                @Override
                public void onFailure(Call<List<CoursesUnit>> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }
}