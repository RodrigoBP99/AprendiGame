package br.com.rodrigo.aprendigame.Activity;

import android.Manifest;

import android.content.DialogInterface;

import android.content.pm.PackageManager;

import android.os.Bundle;
import android.view.MenuItem;

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
import br.com.rodrigo.aprendigame.Fragments.RankingFragment;
import br.com.rodrigo.aprendigame.R;

public class MainActivity extends AppCompatActivity {

    private int CameraPermission = 1;
    private Toolbar toolbar;
    private BottomNavigationView navView;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_ranking:
                    trocarFragmento(new RankingFragment(), "inicio");
                    return true;

                case R.id.navigation_questionarios:
                    trocarFragmento(new CourseUnitFragment(), "questionario");
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
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trocarFragmento(new RankingFragment(), "inicio");

        navView = findViewById(R.id.navViewMain);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar = findViewById(R.id.toolbarMainActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }


    public void trocarFragmento(Fragment fragment, String tag){
        getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutMainActivity, fragment, tag).commit();
    }

    private void requestCameraPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Permissão Nescessaria")
                    .setMessage("Essa permissão é essencial!")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CameraPermission);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
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
                Toast.makeText(MainActivity.this, "Permissão Negada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("inicio");
        if (fragment != null && fragment.isVisible()){
            AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
            alerta.setTitle("Atenção!").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setNegativeButton("Não", null).setMessage("Deseja Sair do Aplicativo?").show();
        } else {
            navView.setSelectedItemId(R.id.navigation_ranking);
        }
    }
}