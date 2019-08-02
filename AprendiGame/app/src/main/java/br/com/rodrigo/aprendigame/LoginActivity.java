package br.com.rodrigo.aprendigame;

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


public class LoginActivity extends AppCompatActivity {

    private UsuarioDAO usuarioDAO;
    private Button buttonLogin;
    private TextView textViewCadastro;
    private EditText editTextUserNameLogin;
    private EditText editTextSenhaLogin;
    public final static String USERNAME = "userName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioDAO = new UsuarioDAO(getApplicationContext());

        buttonLogin = findViewById(R.id.buttonLogin);
        textViewCadastro = findViewById(R.id.textViewCadastro);
        editTextUserNameLogin = findViewById(R.id.editTextLoginUserName);
        editTextSenhaLogin = findViewById(R.id.editTextLoginSenha);


        editTextSenhaLogin.setText("123456");
        editTextUserNameLogin.setText("teste");

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                if (usuarioDAO.autenticaUsuario(editTextUserNameLogin.getText().toString().trim(), editTextSenhaLogin.getText().toString().trim())){
                    Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                    mainActivity.putExtra(USERNAME, editTextUserNameLogin.getText().toString().trim());
                    startActivity(mainActivity);
                    finish();
                } else {
                    Snackbar.make(v,"Senha ou User Name Incorreto!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        textViewCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastroActivity = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(cadastroActivity);
            }
        });
    }
}