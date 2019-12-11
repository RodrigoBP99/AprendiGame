package br.com.rodrigo.aprendigame.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.Objects;

import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.editTextEditarPerfilCurso)
    EditText editTextCurso;

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
        textViewTitleToolbar.setText(getString(R.string.editar_perfil));
    }

    private void setUserInformation() {
        try {
            Student student = LoginActivity.student;

            editTextPerfilNome.setText(student.getName());
            editTextCurso.setText(student.getCourse());
            Glide.with(EditarPerfilActivity.this).load(student.getPhoto()).circleCrop().into(imageViewPerfil);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClick(R.id.imageViewEditarPerfilFoto) void trocarFoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (data != null) {
                String fotoSelecionada = String.valueOf(data.getData());
                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(fotoSelecionada));
                    Glide.with(EditarPerfilActivity.this).load(new BitmapDrawable(bitmap)).circleCrop().into(imageViewPerfil);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.voltar_icon);
        toolbar.setNavigationOnClickListener(v -> {
            alertCancelarEdicao();
        });
    }

    private void alertCancelarEdicao() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setMessage(getString(R.string.deseja_cancelar_edicao)).setPositiveButton(getString(R.string.sim),
                (dialogInterface, i) -> finish())
                .setNegativeButton(getString(R.string.nao), null).show();
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
                Toast.makeText(EditarPerfilActivity.this, getString(R.string.preencha_campos_vazios), Toast.LENGTH_LONG).show();
            } else {
                finish();
            }
            View view = getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        alertCancelarEdicao();
    }
}
