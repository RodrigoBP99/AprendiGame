package br.com.rodrigo.aprendigame.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.rodrigo.aprendigame.DB.StudentDAO;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private StudentDAO usuarioDAO;

    @BindView(R.id.buttonLogin)
    Button buttonLogin;

    @BindView(R.id.textViewCadastroRedirect)
    TextView textViewCadastro;

    @BindView(R.id.editTextLoginUserMatricula)
    EditText editTextUserMatriculaLogin;

    @BindView(R.id.editTextLoginSenha)
    EditText editTextSenhaLogin;

    public final static String STUDENT = "student";

    private String userName;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        usuarioDAO = new StudentDAO(getApplicationContext());
    }

    @OnClick(R.id.textViewCadastroRedirect)
    void cadastroRedirect(){
        Intent cadastroActivity = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(cadastroActivity);
    }

    @OnClick(R.id.buttonLogin) void login(){
        //metodo fechar teclado
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        userName = editTextUserMatriculaLogin.getText().toString().trim();
        senha = editTextSenhaLogin.getText().toString().trim();
        buttonLogin.setClickable(false);
        autenticaLogin();
    }

    public void autenticaLogin(){
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
                    Toast.makeText(LoginActivity.this, getString(R.string.erro_logar), Toast.LENGTH_LONG).show();
                    buttonLogin.setClickable(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}