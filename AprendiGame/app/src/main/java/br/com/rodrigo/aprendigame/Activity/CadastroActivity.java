package br.com.rodrigo.aprendigame.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import br.com.rodrigo.aprendigame.DB.UsuarioDAO;
import br.com.rodrigo.aprendigame.Model.Usuario;
import br.com.rodrigo.aprendigame.R;

public class CadastroActivity extends AppCompatActivity {

    private ImageButton buttonConfirmarCadastro;
    private ImageButton buttonCancelarCadastro;

    private Usuario usuario = new Usuario();

    private EditText editTextUserMatricula;
    private EditText editTextNome;
    private EditText editTextDataNascimento;
    private EditText editTextTurma;
    private EditText editTextInstituicao;
    private EditText editTextEmail;
    private EditText editTextEndereco;
    private EditText editTextSenha;
    private EditText editTextConfrimarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        findViewsById();

        cancelarCadastro();

        buttonConfirmarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastroUsuario();
            }
        });
    }

    private void cancelarCadastro() {
        buttonCancelarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroActivity.this);
                alerta.setTitle("Um momento, amigo(a)!").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("Não", null).setMessage("Tem certeza que deseja cancelar seu Cadastro?").show();
            }
        });
    }

    private void findViewsById() {
        editTextUserMatricula = findViewById(R.id.editTextUserMatricula);
        editTextNome = findViewById(R.id.editTextNome);
        editTextDataNascimento = findViewById(R.id.editTextDataNascimento);
        editTextTurma = findViewById(R.id.editTextTurma);
        editTextInstituicao = findViewById(R.id.editTextInstituicao);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextEndereco = findViewById(R.id.editTextEndereco);
        editTextSenha = findViewById(R.id.editTextSenha);
        editTextConfrimarSenha = findViewById(R.id.editTextConfirmaSenha);
        buttonCancelarCadastro = findViewById(R.id.buttonCancelarCadastro);
        buttonConfirmarCadastro = findViewById(R.id.buttonConfirmarCadastro);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroActivity.this);
        alerta.setTitle("Um momento, amigo(a)!").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("Não", null).setMessage("Tem certeza que deseja cancelar seu Cadastro?").show();
    }

    public void cadastroUsuario(){
        final String userMatricula = editTextUserMatricula.getText().toString().trim();
        final String nome = editTextNome.getText().toString().trim();
        final String dataNascimento = editTextDataNascimento.getText().toString().trim();
        final String turma = editTextTurma.getText().toString().trim();
        final String instituicao = editTextInstituicao.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String endereco = editTextEndereco.getText().toString().trim();
        final String senha = editTextSenha.getText().toString();
        final String confirmarSenha = editTextConfrimarSenha.getText().toString();

        UsuarioDAO bdUsuario = new UsuarioDAO(getApplicationContext());

        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        // Testa se os campos estão vazios
        if (userMatricula.isEmpty() || nome.isEmpty() || dataNascimento.isEmpty()
                || turma.isEmpty() || instituicao.isEmpty() || email.isEmpty()
                || endereco.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty())
        {
            Toast.makeText(CadastroActivity.this, "Todos os campos devem ser preenchidos!", Toast.LENGTH_LONG).show();
        } else {
            // checa se o nome de usuario já existe
            if (bdUsuario.checkUserMatricula(userMatricula)){
                Toast.makeText(CadastroActivity.this, "Matricula já Cadastrada", Toast.LENGTH_SHORT).show();
            } else {
                //verificação da senha
                if (senha.length() < 6) {
                    Toast.makeText(CadastroActivity.this, "A senha deve conter mais de 6 caracteres", Toast.LENGTH_LONG).show();
                } else {
                    if (confirmarSenha.equals(senha)) {

                        usuario.setId(userMatricula);
                        usuario.setNome(nome);
                        usuario.setIdade(dataNascimento);
                        usuario.setTurma(turma);
                        usuario.setInstituicao(instituicao);
                        usuario.setEmail(email);
                        usuario.setEndereco(endereco);
                        usuario.setSenha(senha);

                        try {
                            bdUsuario.salvarUsuario(usuario);
                            Toast.makeText(CadastroActivity.this, "Cadastro Realizado Com Sucesso!", Toast.LENGTH_SHORT).show();
                            finish();
                        }catch (Exception e){
                            Log.w("Cadastro: ", e.getMessage());
                        }

                    } else {
                        Toast.makeText(CadastroActivity.this, "Senha incorreta", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
