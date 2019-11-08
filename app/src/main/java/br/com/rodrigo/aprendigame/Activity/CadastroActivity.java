package br.com.rodrigo.aprendigame.Activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import br.com.rodrigo.aprendigame.DB.StudentDAO;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CadastroActivity extends AppCompatActivity {

    private Student usuario = new Student();

    @BindView(R.id.editTextUserMatriculaCadastro)
    EditText editTextUserMatricula;
    @BindView(R.id.editTextNomeCadastro)
    EditText editTextNome;
    @BindView(R.id.editTextDataNascimentoCadastro)
    EditText editTextDataNascimento;
    @BindView(R.id.editTextTurmaCadastro)
    EditText editTextTurma;
    @BindView(R.id.editTextInstituicaoCadastro)
    EditText editTextInstituicao;
    @BindView(R.id.editTextEmailCadastro)
    EditText editTextEmail;
    @BindView(R.id.editTextEnderecoCadastro)
    EditText editTextEndereco;
    @BindView(R.id.editTextSenhaCadastro)
    EditText editTextSenha;
    @BindView(R.id.editTextConfirmaSenhaCadastro)
    EditText editTextConfrimarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonConfirmarCadastro) void confirmRegister(){
        cadastroUsuario();
    }

    @OnClick(R.id.buttonCancelarCadastro) void cancelRegister(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroActivity.this);
        alerta.setTitle(getString(R.string.titulo_alerta_cancelar_cadastro))
                .setPositiveButton(getString(R.string.sim), (dialog, which) ->
                        finish()).setNegativeButton(getString(R.string.nao), null)
                .setMessage(getString(R.string.messagem_cancelar_cadastro)).show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroActivity.this);
        alerta.setTitle(R.string.titulo_alerta_cancelar_cadastro)
                .setPositiveButton(R.string.sim, (dialog, which) ->
                finish()).setNegativeButton(R.string.nao, null)
                .setMessage(R.string.messagem_cancelar_cadastro).show();
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

        StudentDAO bdUsuario = new StudentDAO(getApplicationContext());

        //Metodo para fechar teclado
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
            Toast.makeText(CadastroActivity.this, getString(R.string.preencha_campos_vazios), Toast.LENGTH_LONG).show();
        } else {
            // checa se o nome de usuario já existe
            checkPasswordRegister(userMatricula, nome, dataNascimento, turma, instituicao, email, endereco, senha, confirmarSenha, bdUsuario);
        }
    }

    private void checkPasswordRegister(String userMatricula, String nome, String dataNascimento, String turma,
                                       String instituicao, String email, String endereco, String senha, String confirmarSenha,
                                       StudentDAO bdUsuario) {
        //verificação da senha
        if (senha.length() < 6) {
            Toast.makeText(CadastroActivity.this, getString(R.string.senha_deve_ter_mais_caracteres), Toast.LENGTH_LONG).show();
        } else {
            if (confirmarSenha.equals(senha)) {
                registerUser(userMatricula, nome, dataNascimento, turma, instituicao, email, endereco, senha, bdUsuario);
            } else {
                Toast.makeText(CadastroActivity.this, getString(R.string.confirma_senha_incorreta), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void registerUser(String userMatricula, String nome, String dataNascimento, String turma,
                              String instituicao, String email, String endereco, String senha, StudentDAO bdUsuario) {
        try {
            Toast.makeText(CadastroActivity.this, getString(R.string.sucesso_cadastro), Toast.LENGTH_SHORT).show();
            finish();
        }catch (Exception e){
            Log.w("Cadastro: ", e.getMessage());
        }
    }
}
