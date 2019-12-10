package br.com.rodrigo.aprendigame.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import br.com.rodrigo.aprendigame.Activity.AuthenticationActivity;
import br.com.rodrigo.aprendigame.Activity.EditarPerfilActivity;
import br.com.rodrigo.aprendigame.Activity.LoginActivity;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;

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
        //recupera usuario
        try {
            Student student = (Student) getActivity().getIntent().getSerializableExtra(AuthenticationActivity.STUDENT);

            Glide.with(getActivity()).load(student.getPhoto()).circleCrop().into(imageViewPerfil);
            textViewNameStudent.setText(student.getName());
            textViewCourse.setText(student.getCourse());
            textViewPoints.setText(String.valueOf(student.getPoints()));
            textViewLevel.setText(String.valueOf(student.getActualLevel()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
