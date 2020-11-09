package br.com.rodrigo.aprendigame.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Activity.LoginActivity;
import br.com.rodrigo.aprendigame.Adapter.CourseClassAdapter;
import br.com.rodrigo.aprendigame.DB.StudentDAO;
import br.com.rodrigo.aprendigame.Model.CourseClass;
import br.com.rodrigo.aprendigame.Model.CoursesUnit;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseClassFragment extends Fragment {

    private CourseClassAdapter courseClassAdapter;
    private List<CourseClass> courseClasses = new ArrayList<>();

    @BindView(R.id.recycleViewCourseUnits)
    RecyclerView recyclerView;
    @BindView(R.id.navViewMain)
    BottomNavigationView bottomNavigationView;

    public CourseClassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_unit, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        getCourseUnit();
    }

    private void getCourseUnit() {
        StudentDAO studentDAO = new StudentDAO(getContext());
        Student student = studentDAO.checkIfDataExists();
        SetupRest.apiService.getStudent(student.getId()).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()){
                    createCourseClassList(response);
                } else {
                    try {
                        String errormesage = getErroMessage(response);
                        Toast.makeText(getContext(), errormesage.toString(), Toast.LENGTH_LONG).show();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createCourseClassList(Response<Student> response) {
        courseClasses = response.body().getListClass();
        courseClassAdapter = new CourseClassAdapter((ArrayList<CourseClass>) courseClasses, getContext());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(courseClassAdapter);
    }

    private String getErroMessage(Response<Student> response) throws IOException {
        ResponseBody responseBody = response.errorBody();
        return responseBody.string();
    }
}
