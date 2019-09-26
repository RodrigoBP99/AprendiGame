package br.com.rodrigo.aprendigame.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;

import br.com.rodrigo.aprendigame.DB.UsuarioDAO;
import br.com.rodrigo.aprendigame.Fragments.PerfilFragment;
import br.com.rodrigo.aprendigame.Model.Usuario;
import br.com.rodrigo.aprendigame.R;

public class EditarPerfilActivity extends AppCompatActivity {

    private Usuario usuario;
    private EditText editTextPerfilNome;
    private EditText editTextPerfilDataNascimento;
    private EditText editTextPerfilTurma;
    private EditText editTextPerfilInstituicao;
    private EditText editTextPerfilEmail;
    private EditText editTextPerfilEndereco;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        findViewsById();

        setToolbar();

        UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());

        String userName = getIntent().getStringExtra(PerfilFragment.USUARIO);

        setUserInformation(usuarioDAO, userName);
    }

    private void setUserInformation(UsuarioDAO usuarioDAO, String userName) {
        try {
            usuario = usuarioDAO.selectUsuario(userName);
            editTextPerfilNome.setText(usuario.getNome());
            editTextPerfilDataNascimento.setText(usuario.getIdade());
            editTextPerfilTurma.setText(usuario.getTurma());
            editTextPerfilInstituicao.setText(usuario.getInstituicao());
            editTextPerfilEmail.setText(usuario.getEmail());
            editTextPerfilEndereco.setText(usuario.getEndereco());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        toolbar.setNavigationIcon(R.drawable.voltar_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                finish();
            }
        });
    }

    private void findViewsById() {
        editTextPerfilNome = findViewById(R.id.editTextEditarPerfilNome);
        editTextPerfilDataNascimento = findViewById(R.id.editTextEditarPerfilDataNascimento);
        editTextPerfilTurma = findViewById(R.id.editTextEditarPerfilTurma);
        editTextPerfilInstituicao = findViewById(R.id.editTextEditarPerfilInstituicao);
        editTextPerfilEmail = findViewById(R.id.editTextEditarPerfilEmail);
        editTextPerfilEndereco = findViewById(R.id.editTextEditarPerfilEndereco);
        ImageView imageViewPerfil = findViewById(R.id.imageViewEditarPerfilFoto);
        toolbar = findViewById(R.id.toolbarEditarPerfil);
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
            final String dataNascimento = editTextPerfilDataNascimento.getText().toString();
            final String turma = editTextPerfilTurma.getText().toString();
            final String instituicao = editTextPerfilInstituicao.getText().toString();
            final String email = editTextPerfilEmail.getText().toString();
            final String endereco = editTextPerfilEndereco.getText().toString();

            if (nome.isEmpty() || dataNascimento.isEmpty()
                    || turma.isEmpty() || instituicao.isEmpty() || email.isEmpty()
                    || endereco.isEmpty())
            {
                Toast.makeText(EditarPerfilActivity.this, "Preencha os campos vazios", Toast.LENGTH_LONG).show();
            } else {
                usuario.setNome(nome);
                usuario.setIdade(dataNascimento);
                usuario.setTurma(turma);
                usuario.setInstituicao(instituicao);
                usuario.setEmail(email);
                usuario.setEndereco(endereco);
                new UsuarioDAO(getApplicationContext()).atualizaUsuario(usuario);

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
