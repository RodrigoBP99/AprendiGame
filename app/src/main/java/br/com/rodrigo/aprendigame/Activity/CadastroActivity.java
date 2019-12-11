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

        textViewToolbar.setText(getString(R.string.cadastro));
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
            if (data != null){
                fotoSelecionada = String.valueOf(data.getData());
                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(fotoSelecionada));
                    Glide.with(CadastroActivity.this).load(new BitmapDrawable(bitmap)).circleCrop().into(imageViewRegister);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnClick(R.id.buttonConfirmarCadastro) void confirmRegister(){
        String userMatricula = editTextUserMatricula.getText().toString().trim();
        String nome = editTextNome.getText().toString().trim();
        String turma = editTextTurma.getText().toString().trim();
        String senha = editTextSenha.getText().toString();
        String confirmarSenha = editTextConfrimarSenha.getText().toString();
        cadastroUsuario();
    }

    @OnClick(R.id.buttonCancelarCadastro) void cancelRegister(){
        alertaRegisterCancel();
    }

    @Override
    public void onBackPressed() {
        alertaRegisterCancel();
    }

    private void alertaRegisterCancel() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroActivity.this, R.style.AlertDialogCustom);
        alerta.setTitle(R.string.titulo_alerta_cancelar_cadastro)
                .setPositiveButton(R.string.sim, (dialog, which) ->
                finish()).setNegativeButton(R.string.nao, null)
                .setMessage(R.string.messagem_cancelar_cadastro).show();
    }

    public void cadastroUsuario(){
        //Metodo para fechar teclado
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
