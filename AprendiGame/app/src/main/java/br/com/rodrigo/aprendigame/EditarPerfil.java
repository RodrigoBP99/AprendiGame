package br.com.rodrigo.aprendigame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import br.com.rodrigo.aprendigame.DB.UsuarioDAO;
import br.com.rodrigo.aprendigame.Model.Usuario;

public class EditarPerfil extends AppCompatActivity {

    private Toolbar toolbar;

    private Usuario usuario = new Usuario();
    private UsuarioDAO usuarioDAO = new UsuarioDAO(this);
    private ImageView imageViewPerfil;
    private EditText editTextPerfilNome;
    private EditText editTextPerfilIdade;
    private EditText editTextPerfilTurma;
    private EditText editTextPerfilInstituicao;

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
                finish();
            }
        });


        editTextPerfilNome = findViewById(R.id.editTextPerfilNome);
        editTextPerfilIdade = findViewById(R.id.editTextEditarPerfilIdade);
        editTextPerfilTurma = findViewById(R.id.editTextEditarPerfilTurma);
        editTextPerfilInstituicao = findViewById(R.id.editTextEditarPerfilInstituicao);
        imageViewPerfil = findViewById(R.id.imageViewEditarPerfilFoto);
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

                usuarioDAO.atualizar(usuario);

                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
