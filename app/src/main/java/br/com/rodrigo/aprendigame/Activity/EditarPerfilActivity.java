package br.com.rodrigo.aprendigame.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.util.Objects;

import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPerfilActivity extends AppCompatActivity {

    @BindView(R.id.imageViewEditarPerfilFoto)
    ImageView imageViewPerfil;
    @BindView(R.id.editTextEditarPerfilNome)
    EditText editTextPerfilNome;
    @BindView(R.id.toolbarEditarPerfil)
    Toolbar toolbar;
    @BindView(R.id.textViewTitleToolbarMain)
    TextView textViewTitleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        ButterKnife.bind(this);

        setToolbar();
        setUserInformation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        textViewTitleToolbar.setText("Editar Perfil");
    }

    private void setUserInformation() {
        try {
            SetupRest.apiService.getStudent(1L).enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    if (response.isSuccessful()) {
                        Student student = response.body();
                        editTextPerfilNome.setText(student.getName());
                        Glide.with(EditarPerfilActivity.this).load(student.getPhoto()).circleCrop().into(imageViewPerfil);
                    }
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.voltar_icon);
        toolbar.setNavigationOnClickListener(v -> {
            View view = getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_editar_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.salvarPerfil) {
            final String nome = editTextPerfilNome.getText().toString();

            if (nome.isEmpty())
            {
                Toast.makeText(EditarPerfilActivity.this, "Preencha os campos vazios", Toast.LENGTH_LONG).show();
            } else {

                finish();
            }
            View view = getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
