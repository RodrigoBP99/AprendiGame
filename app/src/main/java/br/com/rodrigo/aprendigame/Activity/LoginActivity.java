package br.com.rodrigo.aprendigame.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

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

    @BindView(R.id.progressBarLogin)
    ProgressBar progressBar;

    @BindView(R.id.textViewCadastroRedirect)
    TextView textViewCadastro;

    @BindView(R.id.editTextLoginUserMatricula)
    EditText editTextUserMatriculaLogin;

    public static String NUMERO = "numero";
    public static String STUDENT = "student";

    private String userName;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            getStudent();
            progressBarVisibility(View.GONE, View.VISIBLE, false);
        }

        usuarioDAO = new StudentDAO(getApplicationContext());
    }

    @OnClick(R.id.textViewCadastroRedirect)
    void cadastroRedirect(){
        Intent cadastroActivity = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(cadastroActivity);
    }

    @OnClick(R.id.buttonLogin) void login(){
        metodoFecharTeclado();
        userName = editTextUserMatriculaLogin.getText().toString().trim();

        progressBarVisibility(View.GONE, View.VISIBLE, false);

        if(userName.isEmpty() || userName.length() < 10){
            editTextUserMatriculaLogin.setError("Entre com um número válido");
            editTextUserMatriculaLogin.requestFocus();
            progressBarVisibility(View.VISIBLE, View.GONE, true);
        } else {
            Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
            intent.putExtra(NUMERO, userName);
            startActivity(intent);
            finish();
        }
    }

    private void progressBarVisibility(int gone, int visible, boolean b) {
        buttonLogin.setVisibility(gone);
        progressBar.setVisibility(visible);
        textViewCadastro.setClickable(b);
    }

    private void metodoFecharTeclado() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void getStudent() {
        SetupRest.apiService.getStudent(1L).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()){
                    Student student = response.body();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(STUDENT, student);
                    startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(getCurrentFocus(), "Erro ao logar. Servidor dormindo!", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Snackbar.make(getCurrentFocus(), "Usuario não existente!", Snackbar.LENGTH_SHORT).show();

                progressBarVisibility(View.VISIBLE, View.GONE, true);
            }
        });
    }

}