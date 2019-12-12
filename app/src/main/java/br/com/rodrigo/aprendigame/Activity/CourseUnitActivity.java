package br.com.rodrigo.aprendigame.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Adapter.CoursesUnitAdapter;
import br.com.rodrigo.aprendigame.Model.CoursesUnit;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseUnitActivity extends AppCompatActivity {

    @BindView(R.id.recycleViewCourseUnitActivity) RecyclerView recyclerViewCourseUnits;
    private CoursesUnitAdapter courseUnitAdapter;
    private List<CoursesUnit> coursesUnitList;
    @BindView(R.id.textViewTitleToolbarMain)
    TextView textViewTitleToolbar;
    private Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_unit);
        ButterKnife.bind(this);
        student = LoginActivity.student;
        setListAulaRecycle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        textViewTitleToolbar.setText(getString(R.string.diciplinas));
    }

    private void setListAulaRecycle() {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewCourseUnits.addItemDecoration(itemDecoration);
        recyclerViewCourseUnits.setLayoutManager(new LinearLayoutManager(this));

        courseUnitAdapter = new CoursesUnitAdapter((ArrayList<CoursesUnit>) coursesUnitList, this);
        getCourseUnit();
        recyclerViewCourseUnits.setAdapter(courseUnitAdapter);
    }

    private void getCourseUnit() {
        try {
            SetupRest.apiService.getListCourseUnit(student.getId()).enqueue(new Callback<List<CoursesUnit>>() {
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
            e.printStackTrace();
        }
    }
}