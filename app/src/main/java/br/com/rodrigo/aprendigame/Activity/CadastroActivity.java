package br.com.rodrigo.aprendigame.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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

import br.com.rodrigo.aprendigame.DB.StudentDAO;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CadastroActivity extends AppCompatActivity {

    private Student usuario = new Student();
    private String fotoSelecionada;

    @BindView(R.id.editTextUserMatriculaCadastro)
    EditText editTextUserMatricula;
    @BindView(R.id.editTextNomeCadastro)
    EditText editTextNome;
    @BindView(R.id.editTextCursoCadastro)
    EditText editTextTurma;
    @BindView(R.id.editTextSenhaCadastro)
    EditText editTextSenha;
    @BindView(R.id.editTextConfirmaSenhaCadastro)
    EditText editTextConfrimarSenha;
    @BindView(R.id.toolbarCadastro)
    Toolbar toolbar;
    @BindView(R.id.textViewTitleToolbarMain)
    TextView textViewToolbar;
    @BindView(R.id.imageViewStudentRegister)
    ImageView imageViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        ButterKnife.bind(this);

        toolbar.setElevation(0);
        textViewToolbar.setText("Cadastro");

    }

    @OnClick(R.id.imageViewStudentRegister) void carregarImagem(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            fotoSelecionada = String.valueOf(data.getData());
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(fotoSelecionada));
                Glide.with(CadastroActivity.this).load(new BitmapDrawable(bitmap)).circleCrop().into(imageViewRegister);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
        final String turma = editTextTurma.getText().toString().trim();
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
        if (userMatricula.isEmpty() || nome.isEmpty() || turma.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty())
        {
            Toast.makeText(CadastroActivity.this, getString(R.string.preencha_campos_vazios), Toast.LENGTH_LONG).show();
        } else {
            // checa se o nome de usuario já existe
            checkPasswordRegister(userMatricula, nome, turma, senha, confirmarSenha, bdUsuario);
        }
    }

    private void checkPasswordRegister(String userMatricula, String nome, String turma, String senha, String confirmarSenha,
                                       StudentDAO bdUsuario) {
        //verificação da senha
        if (senha.length() < 6) {
            Toast.makeText(CadastroActivity.this, getString(R.string.senha_deve_ter_mais_caracteres), Toast.LENGTH_LONG).show();
        } else {
            if (confirmarSenha.equals(senha)) {
                registerUser(userMatricula, nome, turma, senha, bdUsuario);
            } else {
                Toast.makeText(CadastroActivity.this, getString(R.string.confirma_senha_incorreta), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void registerUser(String userMatricula, String nome, String turma, String senha, StudentDAO bdUsuario) {
        try {
            Toast.makeText(CadastroActivity.this, getString(R.string.sucesso_cadastro), Toast.LENGTH_SHORT).show();
            finish();
        }catch (Exception e){
            Log.w("Cadastro: ", e.getMessage());
        }
    }
}
