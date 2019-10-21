package br.com.rodrigo.aprendigame.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.rodrigo.aprendigame.DB.StudentDAO;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private StudentDAO usuarioDAO;
    private Button buttonLogin;
    private TextView textViewCadastro;
    private EditText editTextUserMatriculaLogin;
    private EditText editTextSenhaLogin;
    public final static String STUDENT = "student";

    private String userName;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioDAO = new StudentDAO(getApplicationContext());

        findViewsById();

        editTextUserMatriculaLogin.setText("1");
        editTextSenhaLogin.setText("1");

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
                buttonLogin.setClickable(false);
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

        // autenticar login e recuperar usuario
        try {
            SetupRest.apiService.getStudent(1L).enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    if (response.isSuccessful()) {
                        Student student = response.body();
                        Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                        mainActivity.putExtra(STUDENT, student);
                        startActivity(mainActivity);
                        finish();
                    } else if(response.isSuccessful()){
                        buttonLogin.setClickable(true);
                    }
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Erro ao logar\nVerifique sua coneção com a internet!", Toast.LENGTH_LONG).show();
                    buttonLogin.setClickable(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}