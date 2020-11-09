package br.com.rodrigo.aprendigame.Fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Adapter.CourseClassAdapter;
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
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PresencCourseClassFragment extends Fragment {

    private int CameraPermission = 1;
    private PresencCourseClassAdapter courseClassAdapter;
    private List<CourseClass> courseClasses = new ArrayList<>();
    @BindView(R.id.recycleViewCourseClass)
    RecyclerView recyclerViewCourseClass;
    public PresencCourseClassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_class, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getCourseUnit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getActivity());
    }

    private void createCourseClassList() {
        courseClassAdapter = new PresencCourseClassAdapter((ArrayList<CourseClass>) courseClasses, getContext());

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerViewCourseClass.addItemDecoration(itemDecoration);
        recyclerViewCourseClass.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerViewCourseClass.setAdapter(courseClassAdapter);
    }

    private void getCourseUnit() {
        StudentDAO studentDAO = new StudentDAO(getContext());
        Student student = studentDAO.checkIfDataExists();
        SetupRest.apiService.getStudent(student.getId()).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()){
                    courseClasses = response.body().getListClass();
                    createCourseClassList();
                } else {
                    try {
                        String errormesage = getErroMessage(response);
                        Toast.makeText(getContext(), errormesage, Toast.LENGTH_LONG).show();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {

            }
        });
    }

    private String getErroMessage(Response<Student> response) throws IOException {
        ResponseBody responseBody = response.errorBody();
        return responseBody.string();
    }


    @OnClick(R.id.buttonLerPresenca) void lerPresenca(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutMainActivity, new LeitorQRFragment(), "leitorQR").commit();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CameraPermission);
        }
    }
}
