package br.com.rodrigo.aprendigame.Activity;

import android.os.Bundle;

import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.crashlytics.android.Crashlytics;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.rodrigo.aprendigame.Fragments.PresencCourseClassFragment;
import br.com.rodrigo.aprendigame.Fragments.CourseClassFragment;
import br.com.rodrigo.aprendigame.Fragments.PerfilFragment;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbarMainActivity)
    Toolbar toolbar;
    @BindView(R.id.navViewMain)
    BottomNavigationView navView;
    @BindView(R.id.textViewTitleToolbarMain)
    TextView textViewTitleToolbar;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_course_unit:
                        trocarFragmento(new CourseClassFragment(), "inicio");
                        return true;

                    case R.id.navigation_perfil:
                        trocarFragmento(new PerfilFragment(), "perfil");
                        return true;

                    case R.id.navigation_presença:
                        trocarFragmento(new PresencCourseClassFragment(), "presencas");
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fabric.with(this, new Crashlytics());

        trocarFragmento(new CourseClassFragment(), "inicio");
        ButterKnife.bind(this);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        textViewTitleToolbar.setText(R.string.app_name);
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