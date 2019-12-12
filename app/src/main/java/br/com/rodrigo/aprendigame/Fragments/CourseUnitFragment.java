package br.com.rodrigo.aprendigame.Fragments;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Activity.LoginActivity;
import br.com.rodrigo.aprendigame.Activity.MainActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseUnitFragment extends Fragment {

    private CoursesUnitAdapter coursesUnitAdapter;
    private List<CoursesUnit> coursesUnits;

    @BindView(R.id.recycleViewCourseUnits)
    RecyclerView recyclerView;
    @BindView(R.id.navViewMain)
    BottomNavigationView bottomNavigationView;
    private Student student;

    public CourseUnitFragment() {
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
        student = LoginActivity.student;

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        coursesUnitAdapter = new CoursesUnitAdapter((ArrayList<CoursesUnit>) coursesUnits, getContext());
        recyclerView.setAdapter(coursesUnitAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCourseUnit();
    }

    private void getCourseUnit() {
        try {
            SetupRest.apiService.getListCourseUnit(student.getId()).enqueue(new Callback<List<CoursesUnit>>() {
                @Override
                public void onResponse(Call<List<CoursesUnit>> call, Response<List<CoursesUnit>> response) {
                    if (response.isSuccessful()) {
                        coursesUnits = response.body();
                        coursesUnitAdapter.atualiza(coursesUnits);
                    }
                }

                @Override
                public void onFailure(Call<List<CoursesUnit>> call, Throwable t) {
                    Toast.makeText(getContext(), "Você está desconectado!", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
