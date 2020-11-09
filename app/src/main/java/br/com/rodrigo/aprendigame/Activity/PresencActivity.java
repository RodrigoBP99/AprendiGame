package br.com.rodrigo.aprendigame.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Adapter.PresencCourseClassAdapter;
import br.com.rodrigo.aprendigame.Adapter.PresencaAdapter;
import br.com.rodrigo.aprendigame.DB.StudentDAO;
import br.com.rodrigo.aprendigame.Model.CourseClass;
import br.com.rodrigo.aprendigame.Model.Presenc;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresencActivity extends AppCompatActivity {

    private PresencaAdapter presencaAdapter;
    private List<Presenc> presencList = new ArrayList<>();
    private List<Presenc> filtredPresencList = new ArrayList<>();
    private CourseClass courseClass;
    private Student student;

    @BindView(R.id.recycleViewPresencActivity)
    RecyclerView recyclerViewPresenc;
    @BindView(R.id.textViewTitleToolbarMain)
    TextView textViewToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presenc);
        ButterKnife.bind(this);

        textViewToolbar.setText("Presenças");
    }

    @Override
    protected void onResume() {
        super.onResume();

        getCourseClass();
        StudentDAO studentDAO = new StudentDAO(this);
        getStudent(studentDAO);
    }

    private void createPresencList() {
        presencaAdapter = new PresencaAdapter((ArrayList<Presenc>) filtredPresencList, this);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewPresenc.addItemDecoration(itemDecoration);
        recyclerViewPresenc.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPresenc.setAdapter(presencaAdapter);
    }

    private void getStudent(StudentDAO studentDAO) {
        student = studentDAO.checkIfDataExists();
        SetupRest.apiService.getStudent(student.getId()).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()){
                    student = response.body();
                    filterPresencList();
                } else {
                    Toast.makeText(PresencActivity.this, "Parece que ocorreu um erro. Verfique se a turma está correta", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(PresencActivity.this, "Parece que ocorreu um erro. Verifique sua conexão", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void filterPresencList() {
        presencList = student.getPresences();

        for (Presenc presenc : presencList){
            if (presenc.getCourseClass().getId().equals(courseClass.getId())){
                filtredPresencList.add(presenc);
            }
        }
        createPresencList();
    }

    private void getCourseClass() {
        String courseClassId = getIntent().getStringExtra(PresencCourseClassAdapter.COURSECLASS);

        SetupRest.apiService.getCourseClass(Long.valueOf(courseClassId)).enqueue(new Callback<CourseClass>() {
            @Override
            public void onResponse(Call<CourseClass> call, Response<CourseClass> response) {
                if (response.isSuccessful()){
                    courseClass = response.body();
                } else {
                    Toast.makeText(PresencActivity.this, "Parece que ocorreu um erro. Verfique se a turma está correta", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CourseClass> call, Throwable t) {
                Toast.makeText(PresencActivity.this, "Parece que ocorreu um erro. Verifique sua conexão", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}