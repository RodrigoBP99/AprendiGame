package br.com.rodrigo.aprendigame.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

import br.com.rodrigo.aprendigame.Activity.CadastroActivity;
import br.com.rodrigo.aprendigame.Activity.EditarPerfilActivity;
import br.com.rodrigo.aprendigame.Activity.LoginActivity;
import br.com.rodrigo.aprendigame.DB.StudentDAO;
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
public class PerfilFragment extends Fragment {

    @BindView(R.id.textViewNameStudentPerfil)
    TextView textViewNameStudent;
    @BindView(R.id.textViewPointsPerfil)
    TextView textViewPoints;
    @BindView(R.id.textViewCourseStudentPerfil)
    TextView textViewCourse;
    @BindView(R.id.textViewActualLevelPerfil)
    TextView textViewLevel;
    @BindView(R.id.imageViewPerfil)
    ImageView imageViewPerfil;

    @BindView(R.id.textViewTitleToolbarMain)
    TextView textViewTittleToolbar;
    private Student student;
    private StudentDAO studentDAO;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        studentDAO = new StudentDAO(getContext());

        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getActivity());
        textViewTittleToolbar.setText(getString(R.string.perfil));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        textViewTittleToolbar.setText(R.string.app_name);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar_perfil_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.buttonLogOut:
                StudentDAO studentDAO = new StudentDAO(getContext());
                studentDAO.clearStudent();
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                return true;
            case R.id.buttonEditarPerfil:
                Intent editarPerfil = new Intent(getContext(), EditarPerfilActivity.class);
                startActivity(editarPerfil);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        student = studentDAO.checkIfDataExists();

        SetupRest.apiService.getStudent(student.getId()).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()){
                    student = response.body();
                    setStudentData(student);
                } else {
                    try {
                        String erroMessage = getErroMessage(response);
                        Toast.makeText(getActivity(), erroMessage, Toast.LENGTH_LONG).show();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                setStudentData(student);
            }
        });
    }

    private String getErroMessage(Response<Student> response) throws IOException {
        ResponseBody responseBody = response.errorBody();
        return responseBody.string();
    }

    private void setStudentData(Student student) {
        textViewNameStudent.setText(student.getName());

        try {
            textViewCourse.setText(student.getCourseUnit().getName());
        } catch (Exception e ){
            e.printStackTrace();
            textViewCourse.setText("---------");
        }

        textViewPoints.setText(String.valueOf(student.getPoints()));
        textViewLevel.setText(String.valueOf(student.getActualLevel()));
        try {
            Glide.with(getActivity()).load(student.getPhoto()).circleCrop().into(imageViewPerfil);
        } catch (Exception e){
            Log.e("ErroFoto: ", e.toString());
        }

    }

}
