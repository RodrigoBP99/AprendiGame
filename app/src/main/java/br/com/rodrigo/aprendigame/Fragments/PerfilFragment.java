package br.com.rodrigo.aprendigame.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.rodrigo.aprendigame.Activity.EditarPerfilActivity;
import br.com.rodrigo.aprendigame.Activity.LoginActivity;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private TextView textViewNameStudent;
    private TextView textViewPoints;
    private TextView textViewCourse;
    private TextView textViewLevel;
    private ImageView imageViewPerfil;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar_perfil_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.buttonEditarPerfil:
                Intent editarPerfil = new Intent(getContext(), EditarPerfilActivity.class);
                startActivity(editarPerfil);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        findViewsById();

        //recuperar usuario
        try {
            Student student = (Student) getActivity().getIntent().getSerializableExtra(LoginActivity.STUDENT);

            Picasso.get().load(student.getPhoto()).into(imageViewPerfil);
            textViewNameStudent.setText(student.getName());
            textViewCourse.setText(student.getCourse());
            textViewPoints.setText(String.valueOf(student.getPoints()));
            textViewLevel.setText(String.valueOf(student.getActualLevel()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findViewsById() {
        textViewNameStudent = getActivity().findViewById(R.id.textViewNameStudentPerfil);
        textViewPoints = getActivity().findViewById(R.id.textViewPointsPerfil);
        textViewCourse = getActivity().findViewById(R.id.textViewCourseStudentPerfil);
        textViewLevel = getActivity().findViewById(R.id.textViewActualLevelPerfil);
        imageViewPerfil = getActivity().findViewById(R.id.imageViewPerfil);
    }
}
