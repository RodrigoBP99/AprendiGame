package br.com.rodrigo.aprendigame;

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

import br.com.rodrigo.aprendigame.DB.UsuarioDAO;
import br.com.rodrigo.aprendigame.Fragments.PerfilFragment;
import br.com.rodrigo.aprendigame.Model.Usuario;

public class EditarPerfil extends AppCompatActivity {

    private Toolbar toolbar;

    private Usuario usuario;
    private ImageView imageViewPerfil;
    private EditText editTextPerfilNome;
    private EditText editTextPerfilIdade;
    private EditText editTextPerfilTurma;
    private EditText editTextPerfilInstituicao;
    private EditText editTextPerfilEmail;
    private EditText editTextPerfilEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        toolbar = findViewById(R.id.toolbar_editarPerfil);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_voltar);
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


        UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());

        editTextPerfilNome = findViewById(R.id.editTextEditarPerfilNome);
        editTextPerfilIdade = findViewById(R.id.editTextEditarPerfilIdade);
        editTextPerfilTurma = findViewById(R.id.editTextEditarPerfilTurma);
        editTextPerfilInstituicao = findViewById(R.id.editTextEditarPerfilInstituicao);
        editTextPerfilEmail = findViewById(R.id.editTextEditarPerfilEmail);
        editTextPerfilEndereco = findViewById(R.id.editTextEditarPerfilEndereco);
        imageViewPerfil = findViewById(R.id.imageViewEditarPerfilFoto);


        String userName = getIntent().getStringExtra(PerfilFragment.USUARIO);

        try {
            usuario = usuarioDAO.selectedUsuario(userName);
            editTextPerfilNome.setText(usuario.getNome());
            editTextPerfilIdade.setText(usuario.getIdade());
            editTextPerfilTurma.setText(usuario.getTurma());
            editTextPerfilInstituicao.setText(usuario.getInstituicao());
            editTextPerfilEmail.setText(usuario.getEmail());
            editTextPerfilEndereco.setText(usuario.getEndereco());
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_editar_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.salvarPerfil:
                usuario.setNome(editTextPerfilNome.getText().toString());
                usuario.setIdade(editTextPerfilIdade.getText().toString());
                usuario.setTurma(editTextPerfilTurma.getText().toString());
                usuario.setInstituicao(editTextPerfilInstituicao.getText().toString());
                usuario.setEmail(editTextPerfilEmail.getText().toString());
                usuario.setEndereco(editTextPerfilEndereco.getText().toString());
                new UsuarioDAO(getApplicationContext()).atualizar(usuario);

                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
