package br.com.rodrigo.aprendigame.Activity;

import android.Manifest;

import android.content.pm.PackageManager;

import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.rodrigo.aprendigame.Fragments.LeitorQRFragment;
import br.com.rodrigo.aprendigame.Fragments.CourseUnitFragment;
import br.com.rodrigo.aprendigame.Fragments.PerfilFragment;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private int CameraPermission = 1;
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
                        trocarFragmento(new CourseUnitFragment(), "inicio");
                        return true;

                    case R.id.navigation_perfil:
                        trocarFragmento(new PerfilFragment(), "perfil");
                        return true;

                    case R.id.navigation_presença:
                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                            trocarFragmento(new LeitorQRFragment(), "leitorQR");
                        } else {
                            requestCameraPermission();
                        }

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
    }

    public void trocarFragmento(Fragment fragment, String tag){
        getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutMainActivity, fragment, tag).commit();
    }

    private void requestCameraPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)){
            new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogCustom)
                    .setTitle(getString(R.string.title_permissao_camera))
                    .setMessage(getString(R.string.message_permissao_camera))
                    .setPositiveButton(getString(R.string.ok), (dialog, which) ->
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CameraPermission))
                    .setNegativeButton(getString(R.string.cancelar), (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CameraPermission);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CameraPermission){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                trocarFragmento(new LeitorQRFragment(), "leitorQR");
            } else {
                Toast.makeText(MainActivity.this, getString(R.string.permissao_negada), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("inicio");
        if (fragment != null && fragment.isVisible()){
            AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogCustom);
            alerta.setTitle(getString(R.string.atencao)).setPositiveButton(R.string.sim, (dialog, which) ->
                    finish()).setNegativeButton(R.string.nao, null).setMessage(getString(R.string.deseja_sair_do_app)).show();
        } else {
            navView.setSelectedItemId(R.id.navigation_course_unit);
        }
    }
}