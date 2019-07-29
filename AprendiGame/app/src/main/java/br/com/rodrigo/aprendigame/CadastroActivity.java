package br.com.rodrigo.aprendigame;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.rodrigo.aprendigame.DB.UsuarioDAO;
import br.com.rodrigo.aprendigame.Model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private Button buttonConfirmarCadastro;
    private Button buttonCancelarCadastro;

    private Usuario usuario = new Usuario();

    private EditText editTextUserName;
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

        editTextUserName = findViewById(R.id.editTextUserName);
        editTextNome = findViewById(R.id.editTextNome);
        editTextDataNascimento = findViewById(R.id.editTextDataNascimento);
        editTextTurma = findViewById(R.id.editTextTurma);
        editTextInstituicao = findViewById(R.id.editTextInstituicao);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextEndereco = findViewById(R.id.editTextEndereco);
        editTextSenha = findViewById(R.id.editTextSenha);
        editTextConfrimarSenha = findViewById(R.id.editTextConfirmaSenha);


        buttonCancelarCadastro = findViewById(R.id.buttonCancelarCadastro);

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

        buttonConfirmarCadastro = findViewById(R.id.buttonConfirmarCadastro);

        buttonConfirmarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editTextUserName.getText().toString().trim();
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

                if (username.isEmpty() || nome.isEmpty() || dataNascimento.isEmpty() || turma.isEmpty() || instituicao.isEmpty() || email.isEmpty() || endereco.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Todos os campos devem ser preenchidos!", Toast.LENGTH_LONG).show();
                } else {

                    if (bdUsuario.checkUser(username) == true){
                        Toast.makeText(CadastroActivity.this, "Usuario já existe", Toast.LENGTH_SHORT).show();
                    } else {
                        //Melhorar a verificação da senha
                        if (senha.length() < 6) {
                            Toast.makeText(CadastroActivity.this, "A senha deve conter mais de 6 caracteres", Toast.LENGTH_LONG).show();
                        } else {
                            if (confirmarSenha.equals(senha)) {

                                usuario.setUserName(username);
                                usuario.setNome(nome);
                                usuario.setIdade(dataNascimento);
                                usuario.setTurma(editTextTurma.getText().toString().trim());
                                usuario.setInstituicao(editTextInstituicao.getText().toString().trim());
                                usuario.setEmail(editTextEmail.getText().toString().trim());
                                usuario.setEndereco(editTextEndereco.getText().toString().trim());
                                usuario.setSenha(editTextSenha.getText().toString().trim());

                                bdUsuario.salvar(usuario);

                                Toast.makeText(CadastroActivity.this, "Cadastro Realizado Com Sucesso!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(CadastroActivity.this, "Senha incorreta", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        });
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
}
