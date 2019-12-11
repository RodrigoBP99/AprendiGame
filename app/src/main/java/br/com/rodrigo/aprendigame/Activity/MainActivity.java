package br.com.rodrigo.aprendigame.Activity;

import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.rodrigo.aprendigame.DB.StudentDAO;
import br.com.rodrigo.aprendigame.Fragments.CourseClassFragment;
import br.com.rodrigo.aprendigame.Fragments.CourseUnitFragment;
import br.com.rodrigo.aprendigame.Fragments.PerfilFragment;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbarMainActivity)
    Toolbar toolbar;
    @BindView(R.id.navViewMain)
    BottomNavigationView navView;
    @BindView(R.id.textViewTitleToolbarMain)
    TextView textViewTitleToolbar;

    public static Student student;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_course_unit:
                        trocarFragmento(new CourseUnitFragment(), "inicio");
                        return true;

                    case R.id.navigation_perfil:
                        trocarFragmento(new PerfilFragment(), "perfil");
                        return true;

                    case R.id.navigation_presença:
                        trocarFragmento(new CourseClassFragment(), "presencas");
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        trocarFragmento(new CourseUnitFragment(), "inicio");

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        textViewTitleToolbar.setText(R.string.app_name);
        try {
            SetupRest.apiService.getStudent(1L).enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    if (response.isSuccessful()){
                        student = response.body();
                    }
                    StudentDAO studentDAO = new StudentDAO(MainActivity.this);
                    student = studentDAO.selectUsuario("1");
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                    StudentDAO studentDAO = new StudentDAO(MainActivity.this);
                    student = studentDAO.selectUsuario("1");
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void trocarFragmento(Fragment fragment, String tag){
        getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutMainActivity, fragment, tag).commit();
    }


    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("inicio");
        Fragment fragment1 = getSupportFragmentManager().findFragmentByTag("leitorQR");

        if (fragment1 != null && fragment1.isVisible()){
            navView.setSelectedItemId(R.id.navigation_presença);
        } else{
            if (fragment != null && fragment.isVisible()) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogCustom);
                alerta.setTitle(getString(R.string.atencao)).setPositiveButton(R.string.sim, (dialog, which) ->
                        finish()).setNegativeButton(R.string.nao, null).setMessage(getString(R.string.deseja_sair_do_app)).show();
            } else {
                navView.setSelectedItemId(R.id.navigation_course_unit);
            }
        }
    }
}