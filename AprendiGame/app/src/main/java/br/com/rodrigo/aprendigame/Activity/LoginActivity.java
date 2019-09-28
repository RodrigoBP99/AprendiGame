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

import br.com.rodrigo.aprendigame.DB.StudentDAO;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;


public class LoginActivity extends AppCompatActivity {

    private StudentDAO usuarioDAO;
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

        usuarioDAO = new StudentDAO(getApplicationContext());

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

                //metodo fechar teclado
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                userName = editTextUserMatriculaLogin.getText().toString().trim();
                senha = editTextSenhaLogin.getText().toString().trim();
                autenticaLogin(Long.valueOf(userName));
            }
        });
    }

    private void findViewsById() {
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewCadastro = findViewById(R.id.textViewCadastroRedirect);
        editTextUserMatriculaLogin = findViewById(R.id.editTextLoginUserMatricula);
        editTextSenhaLogin = findViewById(R.id.editTextLoginSenha);
    }

    public void autenticaLogin(Long userName){
        Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
        mainActivity.putExtra(USERMATRICULA, userName);

        Student student = new Student();
        student.setId(Long.valueOf(userName));

        usuarioDAO.salvarUsuario(student);
        startActivity(mainActivity);

        finish();
    }
}