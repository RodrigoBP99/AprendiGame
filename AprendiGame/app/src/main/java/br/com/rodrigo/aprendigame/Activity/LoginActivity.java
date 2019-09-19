package br.com.rodrigo.aprendigame.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.rodrigo.aprendigame.DB.UsuarioDAO;
import br.com.rodrigo.aprendigame.R;


public class LoginActivity extends AppCompatActivity {

    private UsuarioDAO usuarioDAO;
    private Button buttonLogin;
    private TextView textViewCadastro;
    private EditText editTextUserMatriculaLogin;
    private EditText editTextSenhaLogin;
    public final static String USERMATRICULA = "userName";

    private String userName;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioDAO = new UsuarioDAO(getApplicationContext());

        findViewsById();

        editTextSenhaLogin.setText("123456");
        editTextUserMatriculaLogin.setText("123456");

        clickLogin();

        clickCadastrarUsuario();
    }

    private void clickCadastrarUsuario() {
        textViewCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastroActivity = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(cadastroActivity);
            }
        });
    }

    private void clickLogin() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                userName = editTextUserMatriculaLogin.getText().toString().trim();
                senha = editTextSenhaLogin.getText().toString().trim();
                autenticaLogin(v, userName, senha);
            }
        });
    }

    private void findViewsById() {
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewCadastro = findViewById(R.id.textViewCadastro);
        editTextUserMatriculaLogin = findViewById(R.id.editTextLoginUserMatricula);
        editTextSenhaLogin = findViewById(R.id.editTextLoginSenha);
    }

    public void autenticaLogin(View v, String userName, String senha){
        if (usuarioDAO.autenticaUsuario(userName, senha)){
            Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
            mainActivity.putExtra(USERMATRICULA, editTextUserMatriculaLogin.getText().toString().trim());
            startActivity(mainActivity);
            finish();
        } else {
            Snackbar.make(v,"Senha ou User Name Incorreto!", Snackbar.LENGTH_SHORT).show();
        }
    }
}